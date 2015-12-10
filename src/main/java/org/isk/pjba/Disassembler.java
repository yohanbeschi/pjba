package org.isk.pjba;

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;

import org.isk.pjba.instruction.meta.ByteArgMetaInstruction;
import org.isk.pjba.instruction.meta.MetaInstruction;
import org.isk.pjba.instruction.meta.MetaInstructions;
import org.isk.pjba.instruction.meta.NoArgMetaInstruction;
import org.isk.pjba.instruction.meta.ShortArgMetaInstruction;
import org.isk.pjba.structure.ClassFile;
import org.isk.pjba.structure.Constant;
import org.isk.pjba.structure.Constant.ConstantPoolEntry;
import org.isk.pjba.structure.Instruction;
import org.isk.pjba.structure.Method;
import org.isk.pjba.structure.attribute.Code;
import org.isk.pjba.structure.attribute.constraint.MethodAttribute;
import org.isk.pjba.util.BytecodeUtils;
import org.isk.pjba.util.PjbaList;

public class Disassembler {
  final private DataInput dataInput;
  final private ClassFile classFile;

  private Disassembler(final DataInput dataInput) {
    super();
    this.dataInput = dataInput;
    this.classFile = new ClassFile();
  }

  public static ClassFile disassemble(final DataInput dataInput) {
    final Disassembler disassembler = new Disassembler(dataInput);
    return disassembler.disassemble();
  }

  private ClassFile disassemble() {
    final long magicNumber = this.readInt();
    if (magicNumber != 0xCAFEBABE) {
      throw new RuntimeException("Invalid Class File Format");
    }

    this.readClass();

    this.checkEOF();

    return this.classFile;
  }

  private void checkEOF() {
    try {
      this.dataInput.readByte();
      throw new RuntimeException("Class " + this.classFile.getClassName() + " has not been completely read");
    } catch (final EOFException e) {
      // Expected
    } catch (final IOException e) {
      // Unexpected...
    }
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Read Class
  // -------------------------------------------------------------------------------------------------------------------

  private void readClass() {
    final int version = this.readInt();
    this.classFile.setVersion(version);

    final int constantPoolCount = this.readUnsignedShort();
    final PjbaList<ConstantPoolEntry> constants = this.readConstants(constantPoolCount);
    this.classFile.setConstantPool(constants);

    final int accessFlags = this.readShort();
    this.classFile.setAccessFlags(accessFlags);

    final int thisClass = this.readUnsignedShort();
    this.classFile.setThisClass(thisClass);

    final Constant.Class constantClass = (Constant.Class) this.classFile.getConstant(thisClass);
    final Constant.UTF8 constantUTF8 = (Constant.UTF8) this.classFile.getConstant(constantClass.nameIndex);
    final String fullyQualifiedClassName = constantUTF8.value;
    this.classFile.setFullyQualifiedClassName(fullyQualifiedClassName);

    final int superClass = this.readUnsignedShort();
    this.classFile.setSuperClass(superClass);

    final int interfacesCount = this.readUnsignedShort();
    if (interfacesCount != 0) {
      throw new RuntimeException("Unable to read interfaces yet in class " + this.classFile.getClassName());
    }

    final int fieldsCount = this.readUnsignedShort();
    if (fieldsCount != 0) {
      throw new RuntimeException("Unable to read fields yet");
    }

    final int methodsCount = this.readUnsignedShort();
    final PjbaList<Method> methods = this.readMethods(methodsCount);
    this.classFile.setMethods(methods);

    final int attributesCount = this.readUnsignedShort();
    if (attributesCount != 0) {
      throw new RuntimeException("Unable to read class attributes yet in class " + this.classFile.getClassName());
    }
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Read Methods
  // -------------------------------------------------------------------------------------------------------------------

  private PjbaList<Method> readMethods(final int methodsCount) {
    final PjbaList<Method> list = new PjbaList<>();

    for (int i = 0; i < methodsCount; i++) {
      list.add(this.readMethod());
    }

    return list;
  }

  private Method readMethod() {
    final Method method = new Method();

    final int accessFlags = this.readShort();
    method.setAccessFlags(accessFlags);

    final int nameIndex = this.readUnsignedShort();
    method.setNameIndex(nameIndex);

    final int descriptorIndex = this.readUnsignedShort();
    method.setDescriptorIndex(descriptorIndex);

    final int attributesCount = this.readUnsignedShort();
    final PjbaList<MethodAttribute> attributes = this.readMethodAttributes(attributesCount);
    method.setAttributes(attributes);

    return method;
  }

  private PjbaList<MethodAttribute> readMethodAttributes(final int attributesCount) {
    final PjbaList<MethodAttribute> attributes = new PjbaList<>();

    for (int i = 0; i < attributesCount; i++) {
      final int attributeNameIndex = this.readUnsignedShort();
      final ConstantPoolEntry entry = this.classFile.getConstant(attributeNameIndex);
      final String attributeName = ((Constant.UTF8) entry).value;

      if (Code.ATTRIBUTE_NAME.equals(attributeName)) {
        final Code code = this.readCode(attributeNameIndex);
        attributes.add(code);
      } else {
        throw new RuntimeException(
            "Unknown attribute: " + attributeName + " in class " + this.classFile.getClassName());
      }
    }

    return attributes;
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Read Code
  // -------------------------------------------------------------------------------------------------------------------

  private Code readCode(final int attributeNameIndex) {
    final Code code = new Code(attributeNameIndex);

    // Attribute length
    this.readInt(); // Dynamically computed

    final int maxStack = this.readUnsignedShort();
    code.setMaxStack(maxStack);

    final int maxLocals = this.readUnsignedShort();
    code.setMaxLocals(maxLocals);

    final long codeLength = this.readUnsignedInt();
    code.setCodeLength((int) codeLength);

    final PjbaList<Instruction> instructions = this.readInstructions(codeLength);
    code.setInstructions(instructions);

    final int exceptionsCount = this.readUnsignedShort();
    if (exceptionsCount != 0) {
      throw new RuntimeException("Unable to read exceptions yet in class " + this.classFile.getClassName());
    }

    final int attributesCount = this.readUnsignedShort();
    if (attributesCount != 0) {
      throw new RuntimeException("Unable to read code attributes yet in class " + this.classFile.getClassName());
    }

    return code;
  }

  private PjbaList<Instruction> readInstructions(final long codeSize) {
    final PjbaList<Instruction> instructions = new PjbaList<>();
    int bytesProceed = 0;

    while (bytesProceed < codeSize) {
      final int opcode = this.readUnsignedByte();
      bytesProceed++;

      final MetaInstruction metaInstruction = MetaInstructions.getMetaInstruction(opcode);
      Instruction instruction = null;

      if (metaInstruction instanceof NoArgMetaInstruction) {
        instruction = ((NoArgMetaInstruction) metaInstruction).buildInstruction();
      } else if (metaInstruction instanceof ByteArgMetaInstruction) {
        bytesProceed++;
        final byte b = this.readByte();
        instruction = ((ByteArgMetaInstruction) metaInstruction).buildInstruction(b);
      } else if (metaInstruction instanceof ShortArgMetaInstruction) {
        bytesProceed += 2;
        final short s = this.readShort();
        instruction = ((ShortArgMetaInstruction) metaInstruction).buildInstruction(s);
      }

      instructions.add(instruction);
    }

    return instructions;
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Read Constants
  // -------------------------------------------------------------------------------------------------------------------

  private PjbaList<ConstantPoolEntry> readConstants(final int constantPoolCount) {
    final PjbaList<ConstantPoolEntry> list = new PjbaList<>();
    list.add(null);

    int realCount = 2; // For easy comparison with constantPoolCount
    for (int i = 1; i < constantPoolCount; i++, realCount++) {
      final int tag = this.readByte();
      switch (tag) {
      case 1: // UTF8
        list.add(this.readConstantUTF8());
        break;
      case 3: // Integer
        list.add(this.readConstantInteger());
        break;
      case 4: // Float
        list.add(this.readConstantFloat());
        break;
      case 5: // Long
        realCount++;
        list.add(this.readConstantLong());
        list.add(null);
        break;
      case 6: // Double
        realCount++;
        list.add(this.readConstantDouble());
        list.add(null);
        break;
      case 7: // Class
        list.add(this.readConstantClass());
        break;
      case 8: // String
        list.add(this.readConstantString());
        break;
      default:
        throw new RuntimeException("Unknown tag: " + tag + " in class " + this.classFile.getClassName());
      }

      if (realCount == constantPoolCount) {
        break;
      }
    }

    return list;
  }

  private ConstantPoolEntry readConstantUTF8() {
    final String value = this.readUTF();
    return new Constant.UTF8(value);
  }

  private ConstantPoolEntry readConstantInteger() {
    final int value = this.readInt();
    return new Constant.Integer(value);
  }

  private ConstantPoolEntry readConstantFloat() {
    final float value = this.readFloat();
    return new Constant.Float(value);
  }

  private ConstantPoolEntry readConstantLong() {
    final long value = this.readLong();
    return new Constant.Long(value);
  }

  private ConstantPoolEntry readConstantDouble() {
    final double value = this.readDouble();
    return new Constant.Double(value);
  }

  private ConstantPoolEntry readConstantClass() {
    final int value = this.readShort();
    return new Constant.Class(value);
  }

  private ConstantPoolEntry readConstantString() {
    final int value = this.readShort();
    return new Constant.String(value);
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Unsigning methods
  // -------------------------------------------------------------------------------------------------------------------
  public int readUnsignedByte() {
    try {
      return this.dataInput.readUnsignedByte();
    } catch (final IOException e) {
      throw new RuntimeException("Unable to read data input in class " + this.classFile.getClassName(), e);
    }
  }

  public int readUnsignedShort() {
    try {
      return this.dataInput.readUnsignedShort();
    } catch (final IOException e) {
      throw new RuntimeException("Unable to read data input in class " + this.classFile.getClassName(), e);
    }
  }

  public long readUnsignedInt() {
    final int i = this.readInt();
    return BytecodeUtils.unsign(i);
  }

  // --------------------------------------------------------------------------------------------------------------------
  // DataInput methods wrappers
  // --------------------------------------------------------------------------------------------------------------------
  private byte readByte() {
    try {
      return this.dataInput.readByte();
    } catch (final IOException e) {
      throw new RuntimeException("Unable to read data input in class " + this.classFile.getClassName(), e);
    }
  }

  private short readShort() {
    try {
      return this.dataInput.readShort();
    } catch (final IOException e) {
      throw new RuntimeException("Unable to read data input in class " + this.classFile.getClassName(), e);
    }
  }

  private int readInt() {
    try {
      return this.dataInput.readInt();
    } catch (final IOException e) {
      throw new RuntimeException("Unable to read data input in class " + this.classFile.getClassName(), e);
    }
  }

  private long readLong() {
    try {
      return this.dataInput.readLong();
    } catch (final IOException e) {
      throw new RuntimeException("Unable to read data input in class " + this.classFile.getClassName(), e);
    }
  }

  private float readFloat() {
    try {
      return this.dataInput.readFloat();
    } catch (final IOException e) {
      throw new RuntimeException("Unable to read data input in class " + this.classFile.getClassName(), e);
    }
  }

  private double readDouble() {
    try {
      return this.dataInput.readDouble();
    } catch (final IOException e) {
      throw new RuntimeException("Unable to read data input in class " + this.classFile.getClassName(), e);
    }
  }

  private String readUTF() {
    try {
      return this.dataInput.readUTF();
    } catch (final IOException e) {
      throw new RuntimeException("Unable to read data input in class " + this.classFile.getClassName(), e);
    }
  }
}
