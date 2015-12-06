package org.isk.pjba;

import java.io.DataOutput;
import java.io.IOException;

import org.isk.pjba.structure.ClassFile;
import org.isk.pjba.visitor.Visitor;

public class Assembler implements Visitor {

  final private ClassFile classFile;
  final private DataOutput dataOutput;

  private Assembler(final ClassFile classFile, final DataOutput dataOutput) {
    this.classFile = classFile;
    this.dataOutput = dataOutput;
    this.assemble();
  }

  public static void assemble(final ClassFile classFile, final DataOutput dataOutput) {
    new Assembler(classFile, dataOutput);
  }

  public void assemble() {
    this.classFile.accept(this);
  }

  @Override
  public void visitMagicNumber(final int magicNumber) {
    this.writeInt(magicNumber);
  }

  @Override
  public void visitVersion(final int version) {
    this.writeInt(version);
  }

  @Override
  public void visitConstantPoolSize(final int size) {
    this.writeShort(size);
  }

  @Override
  public void visitClassAccessFlags(final int accessFlags) {
    this.writeShort(accessFlags);
  }

  @Override
  public void visitThisClass(final int thisClass) {
    this.writeShort(thisClass);
  }

  @Override
  public void visitSuperClass(final int superClass) {
    this.writeShort(superClass);
  }

  @Override
  public void visitInterfacesSize(final int size) {
    this.writeShort(size);
  }

  @Override
  public void visitFieldsSize(final int size) {
    this.writeShort(size);
  }

  @Override
  public void visitMethodsSize(final int size) {
    this.writeShort(size);
  }

  @Override
  public void visitClassAttributeSize(final int size) {
    this.writeShort(size);
  }

  @Override
  public void visitConstantTag(final int tag) {
    this.writeByte(tag);
  }

  @Override
  public void visitConstantUTF8(final String value) {
    this.writeUTF(value); // length included
  }

  @Override
  public void visitConstantInteger(final int value) {
    this.writeInt(value);
  }

  @Override
  public void visitConstantFloat(final float value) {
    this.writeFloat(value);
  }

  @Override
  public void visitConstantLong(final long value) {
    this.writeLong(value);
  }

  @Override
  public void visitConstantDouble(final double value) {
    this.writeDouble(value);
  }

  @Override
  public void visitConstantClass(final int utf8Index) {
    this.writeShort(utf8Index);
  }

  @Override
  public void visitConstantString(final int utf8Index) {
    this.writeShort(utf8Index);
  }

  @Override
  public void visitMethodAccessFlags(final int accessFlags) {
    this.writeShort(accessFlags);
  }

  @Override
  public void visitMethodNameIndex(final int nameIndex) {
    this.writeShort(nameIndex);
  }

  @Override
  public void visitMethodDescriptorIndex(final int descriptorIndex) {
    this.writeShort(descriptorIndex);
  }

  @Override
  public void visitMethodAttributesSize(final int size) {
    this.writeShort(size);
  }

  @Override
  public void visitAttributeNameIndex(final int nameIndex) {
    this.writeShort(nameIndex);
  }

  @Override
  public void visitAttributeLength(final int length) {
    this.writeInt(length);
  }

  @Override
  public void visitCodeMaxStack(final int maxStack) {
    this.writeShort(maxStack);
  }

  @Override
  public void visitCodeMaxLocals(final int maxLocals) {
    this.writeShort(maxLocals);
  }

  @Override
  public void visitCodeLength(final int codeLength) {
    this.writeInt(codeLength);
  }

  @Override
  public void visitCodeExceptionsSize(final int size) {
    this.writeShort(size);
  }

  @Override
  public void visitCodeAttributesSize(final int size) {
    this.writeShort(size);
  }

  @Override
  public void visitOpcode(final int opcode) {
    this.writeByte(opcode);
  }

  @Override
  public void visitInstructionByte(final int arg) {
    this.writeByte(arg);
  }

  @Override
  public void visitInstructionShort(final int arg) {
    this.writeShort(arg);
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Write to the DataOutputStream
  // -------------------------------------------------------------------------------------------------------------------

  private void writeByte(final int b) {
    try {
      this.dataOutput.writeByte(b);
    } catch (final IOException e) {
      throw new RuntimeException("Unable to write class", e);
    }
  }

  private void writeShort(final int s) {
    try {
      this.dataOutput.writeShort(s);
    } catch (final IOException e) {
      throw new RuntimeException("Unable to write class", e);
    }
  }

  private void writeInt(final int i) {
    try {
      this.dataOutput.writeInt(i);
    } catch (final IOException e) {
      throw new RuntimeException("Unable to write class", e);
    }
  }

  private void writeFloat(final float f) {
    try {
      this.dataOutput.writeFloat(f);
    } catch (final IOException e) {
      throw new RuntimeException("Unable to write class", e);
    }
  }

  private void writeLong(final long l) {
    try {
      this.dataOutput.writeLong(l);
    } catch (final IOException e) {
      throw new RuntimeException("Unable to write class", e);
    }
  }

  private void writeDouble(final double d) {
    try {
      this.dataOutput.writeDouble(d);
    } catch (final IOException e) {
      throw new RuntimeException("Unable to write class", e);
    }
  }

  private void writeUTF(final String s) {
    try {
      this.dataOutput.writeUTF(s);
    } catch (final IOException e) {
      throw new RuntimeException("Unable to write class", e);
    }
  }

}
