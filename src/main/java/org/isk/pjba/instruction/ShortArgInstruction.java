package org.isk.pjba.instruction;

import org.isk.pjba.structure.Instruction;
import org.isk.pjba.visitor.Visitor;

public class ShortArgInstruction extends Instruction {

  final private int arg;

  public ShortArgInstruction(final int opcode, final int stack, final int locals, final int arg) {
    super(opcode, stack, locals, 3);
    this.arg = arg;
  }

  @Override
  public void accept(final Visitor visitor) {
    super.accept(visitor);
    visitor.visitInstructionShort(this.arg);
  }
}
