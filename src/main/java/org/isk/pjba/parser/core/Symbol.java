package org.isk.pjba.parser.core;

import org.isk.pjba.tokenizer.core.reader.TokenReader;
import org.isk.pjba.tokenizer.core.token.EndOfStreamToken;
import org.isk.pjba.tokenizer.core.token.Token;

public abstract class Symbol<T extends TokenReader, P extends ProductionStack<T, P, B>, B extends AstBuilder> {
  protected final Production<T, P, B> p;

  protected Symbol(final Production<T, P, B> p) {
    this.p = p;
  }

  public Token produce(final T reader, final P productionStack, final B builder) {
    final Token token = reader.peek();
    if (token == null || token == EndOfStreamToken.EOS) {
      return EndOfStreamToken.EOS;
    } else {
      this.p.produce(reader, productionStack, builder);
      return null;
    }
  }
}
