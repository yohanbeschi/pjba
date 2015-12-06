package org.isk.pjba.instruction;

import org.isk.pjba.structure.Instruction;
import org.isk.pjba.visitor.Visitor;

public class ByteArgInstruction extends Instruction {

  final private int arg;

  public ByteArgInstruction(final int opcode, final int stack, final int locals, final int arg) {
    super(opcode, stack, locals, 2);
    this.arg = arg;
  }

  @Override
  public void accept(final Visitor visitor) {
    super.accept(visitor);
    visitor.visitInstructionByte(this.arg);
  }
}