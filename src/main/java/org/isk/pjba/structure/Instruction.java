package org.isk.pjba.structure;

import org.isk.pjba.visitor.Visitable;
import org.isk.pjba.visitor.Visitor;

public class Instruction implements Visitable {
  final private int opcode;
  final private int stack;
  final private int locals;
  final private int length;

  public Instruction(final int opcode, final int stack, final int locals, final int length) {
    this.opcode = opcode;
    this.stack = stack;
    this.locals = locals;
    this.length = length;
  }

  public int getOpcode() {
    return this.opcode;
  }

  public int getStack() {
    return this.stack;
  }

  public int getLocals() {
    return this.locals;
  }

  public int getLength() {
    return this.length;
  }

  @Override
  public void accept(final Visitor visitor) {
    visitor.visitOpcode(this.opcode);
  }
}