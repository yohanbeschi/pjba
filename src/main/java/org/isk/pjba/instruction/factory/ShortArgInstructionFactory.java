package org.isk.pjba.instruction.factory;

import org.isk.pjba.structure.Instruction;

public interface ShortArgInstructionFactory {
  Instruction buildInstruction(int s);
}
