package org.isk.pjba.dumper;

import org.isk.pjba.instruction.meta.MetaInstruction;
import org.isk.pjba.instruction.meta.MetaInstruction.ArgsType;
import org.isk.pjba.instruction.meta.MetaInstructions;
import org.isk.pjba.structure.ClassFile;
import org.isk.pjba.structure.Constant;
import org.isk.pjba.util.BytecodeUtils;
import org.isk.pjba.visitor.Visitor;

public class PjbDumper implements Visitor {

  final private ClassFile classFile;
  final private StringBuilder pjb;
  private int methodCount;

  private PjbDumper(final ClassFile classFile) {
    super();
    this.classFile = classFile;
    this.pjb = new StringBuilder();
  }

  public static String dump(final ClassFile classFile) {
    final PjbDumper dumper = new PjbDumper(classFile);
    return dumper.dump();
  }

  private String dump() {
    this.classFile.accept(this);

    this.pjb.append("  .methodend\n");
    this.pjb.append(".classend");
    return this.pjb.toString();
  }

  @Override
  public void visitMagicNumber(final int magicNumber) {
    this.pjb.append(".class ");
  }

  @Override
  public void visitVersion(final int version) {
    // Do nothing
  }

  @Override
  public void visitConstantPoolSize(final int size) {
    // Do nothing
  }

  @Override
  public void visitClassAccessFlags(final int accessFlags) {
    this.pjb.append(StringValues.getClassModifiers(accessFlags));
  }

  @Override
  public void visitThisClass(final int thisClass) {
    final Constant.Class constantClass = (Constant.Class) this.classFile.getConstant(thisClass);
    final Constant.UTF8 constantUtf8 = (Constant.UTF8) this.classFile.getConstant(constantClass.nameIndex);
    this.pjb.append(constantUtf8.value).append("\n");
  }

  @Override
  public void visitSuperClass(final int superClass) {
    // Do nothing
  }

  @Override
  public void visitInterfacesSize(final int length) {
    // Do nothing
  }

  @Override
  public void visitFieldsSize(final int size) {
    // Do nothing
  }

  @Override
  public void visitMethodsSize(final int size) {
    // Do nothing
  }

  @Override
  public void visitClassAttributeSize(final int size) {
    // Do nothing
  }

  @Override
  public void visitConstantTag(final int tag) {
    // Do nothing
  }

  @Override
  public void visitConstantUTF8(final String value) {
    // Do nothing
  }

  @Override
  public void visitConstantInteger(final int integer) {
    // Do nothing
  }

  @Override
  public void visitConstantFloat(final float floatValue) {
    // Do nothing
  }

  @Override
  public void visitConstantLong(final long longValue) {
    // Do nothing
  }

  @Override
  public void visitConstantDouble(final double doubleValue) {
    // Do nothing
  }

  @Override
  public void visitConstantClass(final int utf8Index) {
    // Do nothing
  }

  @Override
  public void visitConstantString(final int utf8Index) {
    // Do nothing
  }

  @Override
  public void visitMethodAccessFlags(final int accessFlags) {
    if (this.methodCount > 0) {
      this.pjb.append("  .methodend\n\n");
    }

    this.pjb.append("  .method ").append(StringValues.getMethodModifiers(accessFlags));

    this.methodCount++;
  }

  @Override
  public void visitMethodNameIndex(final int nameIndex) {
    final Constant.UTF8 constantUtf8 = (Constant.UTF8) this.classFile.getConstant(nameIndex);
    this.pjb.append(constantUtf8.value);
  }

  @Override
  public void visitMethodDescriptorIndex(final int descriptorIndex) {
    final Constant.UTF8 constantUtf8 = (Constant.UTF8) this.classFile.getConstant(descriptorIndex);
    this.pjb.append(constantUtf8.value).append("\n");
  }

  @Override
  public void visitMethodAttributesSize(final int size) {
    // Do nothing
  }

  @Override
  public void visitAttributeNameIndex(final int nameIndex) {
    // Do nothing
  }

  @Override
  public void visitAttributeLength(final int length) {
    // Do nothing
  }

  @Override
  public void visitCodeMaxStack(final int maxStack) {
    // Do nothing
  }

  @Override
  public void visitCodeMaxLocals(final int maxLocals) {
    // Do nothing
  }

  @Override
  public void visitCodeLength(final int codeLength) {
    // Do nothing
  }

  @Override
  public void visitCodeExceptionsSize(final int size) {
    // Do nothing
  }

  @Override
  public void visitCodeAttributesSize(final int size) {
    // Do nothing
  }

  private MetaInstruction metaInstruction;

  @Override
  public void visitOpcode(final int opcode) {
    this.metaInstruction = MetaInstructions.getMetaInstruction(opcode);
    this.pjb.append("    ").append(this.metaInstruction.getPjbMnemonic());

    if (this.metaInstruction.getArgsType() == ArgsType.NONE) {
      this.pjb.append("\n");
    }
  }

  @Override
  public void visitInstructionByte(final int value) {
    Object printableValue = null;

    final ArgsType type = this.metaInstruction.getArgsType();
    switch (type) {
    case BYTE_VALUE:
      printableValue = value;
      break;
    case IFS_CONSTANT:
      printableValue = StringValues.getPrintableConstant(BytecodeUtils.unsign((byte) value), this.classFile);
      break;
    default:
      throw new RuntimeException("Incorrect type: " + type + " for the value: " + value);
    }

    this.pjb.append(" ").append(printableValue).append("\n");
  }

  @Override
  public void visitInstructionShort(final int value) {
    Object printableValue = null;

    final ArgsType type = this.metaInstruction.getArgsType();
    switch (type) {
    case SHORT_VALUE:
      printableValue = value;
      break;
    case W_IFS_CONSTANT:
    case LD_CONSTANT:
      printableValue = StringValues.getPrintableConstant(BytecodeUtils.unsign((short) value), this.classFile);
      break;
    default:
      throw new RuntimeException("Incorrect type: " + type + " for the value: " + value);
    }

    this.pjb.append(" ").append(printableValue).append("\n");
  }
}
