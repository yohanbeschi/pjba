package org.isk.pjba.instruction;

import org.isk.pjba.structure.Instruction;

public class NoArgInstruction extends Instruction {
  public NoArgInstruction(final int opcode, final int stack, final int locals) {
    super(opcode, stack, locals, 1);
  }
}
