package org.isk.pjba.structure.attribute;

import org.isk.pjba.structure.Exception;
import org.isk.pjba.structure.Instruction;
import org.isk.pjba.structure.attribute.constraint.CodeAttribute;
import org.isk.pjba.structure.attribute.constraint.MethodAttribute;
import org.isk.pjba.util.PjbaList;
import org.isk.pjba.visitor.Visitor;

public class Code extends Attribute implements MethodAttribute {
  public final static String ATTRIBUTE_NAME = "Code";

  private int maxStack;
  private int maxLocals;
  private int codeLength;

  private PjbaList<Instruction> instructions = new PjbaList<>();
  private final PjbaList<Exception> exceptions = new PjbaList<>();
  private final PjbaList<CodeAttribute> attributes = new PjbaList<>();

  private int currentStack;

  public Code(final int attributeNameIndex) {
    super(attributeNameIndex);
  }

  public void setMaxStack(final int maxStack) {
    this.maxStack = maxStack;
  }

  public void setMaxLocals(final int maxLocals) {
    this.maxLocals = maxLocals;
  }

  public void setCodeLength(final int codeLength) {
    this.codeLength = codeLength;
  }

  public void setInstructions(final PjbaList<Instruction> instructions) {
    this.instructions = instructions;
  }

  public void setParameterCount(final int parameterCount) {
    this.addLocals(parameterCount);
  }

  private void addStack(final int stack) {
    this.currentStack += stack;

    if (this.maxStack < this.currentStack) {
      this.maxStack = this.currentStack;
    }
  }

  private void addLocals(final int locals) {
    if (this.maxLocals < locals) {
      this.maxLocals = locals;
    }
  }

  private void addLength(final int length) {
    this.codeLength += length;
  }

  public void addInstruction(final Instruction instruction) {
    this.addStack(instruction.getStack());
    this.addLocals(instruction.getLocals());
    this.addLength(instruction.getLength());
    this.instructions.add(instruction);
  }

  @Override
  public void accept(final Visitor visitor) {
    super.accept(visitor);

    // 2 + 2 + 4 + code.length + 2 + 8 * exceptionTable.length + 2
    visitor.visitAttributeLength(12 + this.codeLength + 8 * this.exceptions.size());
    visitor.visitCodeMaxStack(this.maxStack);
    visitor.visitCodeMaxLocals(this.maxLocals);
    visitor.visitCodeLength(this.codeLength);
    this.instructions.accept(visitor);
    visitor.visitCodeExceptionsSize(this.exceptions.size());
    this.exceptions.accept(visitor);
    visitor.visitCodeAttributesSize(this.attributes.size());
    this.attributes.accept(visitor);
  }
}
