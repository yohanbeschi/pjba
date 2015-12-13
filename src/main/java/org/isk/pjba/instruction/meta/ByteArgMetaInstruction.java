package org.isk.pjba.instruction.meta;

import org.isk.pjba.instruction.factory.ByteArgInstructionFactory;
import org.isk.pjba.structure.Instruction;

public class ByteArgMetaInstruction extends MetaInstruction {
  final private static byte BYTE_ZERO = 0;

  private final ByteArgInstructionFactory instructionBuilder;

  public ByteArgMetaInstruction(final String mnemonic, final ArgsType argsType,
      final ByteArgInstructionFactory instructionBuilder) {
    this(mnemonic, mnemonic, argsType, instructionBuilder);
  }

  public ByteArgMetaInstruction(final String mnemonic, final String pjbMnemonic, final ArgsType argsType,
      final ByteArgInstructionFactory instructionBuilder) {
    super(mnemonic, pjbMnemonic, argsType);

    this.instructionBuilder = instructionBuilder;
    this.opcode = instructionBuilder.buildInstruction(BYTE_ZERO).getOpcode();
  }

  public Instruction buildInstruction(final int b) {
    return this.instructionBuilder.buildInstruction(b);
  }
}
