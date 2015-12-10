package org.isk.pjba.instruction.factory;

import org.isk.pjba.structure.Instruction;

public interface ByteArgInstructionFactory {
  Instruction buildInstruction(int b);
}
