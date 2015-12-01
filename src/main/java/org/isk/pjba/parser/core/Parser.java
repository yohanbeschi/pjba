package org.isk.pjba.parser.core;

import org.isk.pjba.parser.core.exception.ParserException;
import org.isk.pjba.tokenizer.core.reader.TokenReader;
import org.isk.pjba.tokenizer.core.token.EndOfStreamToken;
import org.isk.pjba.tokenizer.core.token.Token;

// Parser - Ability to create an AST from a list of tokens
public class Parser<T extends TokenReader, P extends ProductionStack<T, P, B>, B extends AstBuilder, R> {
  private final P productionStack;
  private final T reader;
  private final B builder;

  public Parser(final T tokenReader, final P productionstack, final B builder) {
    this.builder = builder;
    this.reader = tokenReader;
    this.productionStack = productionstack;
  }

  public R parse() {
    while (!this.productionStack.isEmpty()) {
      final Symbol<T, P, B> symbol = this.productionStack.pop();
      final Token token = symbol.produce(this.reader, this.productionStack, this.builder);

      if (token == EndOfStreamToken.EOS) {
        break;
      }
    }

    if (!this.productionStack.isEmpty()) {
      throw new ParserException("EOS encontered and the production stack is not empty!");
    }

    this.builder.endStream();
    return this.builder.getBuiltObject();
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static <T extends TokenReader, P extends ProductionStack<T, P, B>, B extends AstBuilder, R> R parse(
      final T tokenReader, final P productionStack, final B builder) {
    final Parser parser = new Parser(tokenReader, productionStack, builder);
    return (R) parser.parse();
  }
}
