package org.isk.pjba.parser.core;

import org.isk.pjba.tokenizer.core.reader.TokenReader;
import org.isk.pjba.tokenizer.core.token.EndOfStreamToken;
import org.isk.pjba.tokenizer.core.token.Token;

public abstract class SkipWSSymbol<T extends TokenReader, P extends ProductionStack<T, P, B>, B extends AstBuilder>
    extends Symbol<T, P, B> {

  protected SkipWSSymbol(final Production<T, P, B> p) {
    super(p);
  }

  @Override
  public Token produce(final T reader, final P productionStack, final B builder) {
    for (;;) {
      final Token token = reader.peek();

      if (token == null || token == EndOfStreamToken.EOS) {
        return EndOfStreamToken.EOS;
      } else if (token.type().isWhitespace()) {
        reader.next(); // Discard Whitespace
      } else {
        this.p.produce(reader, productionStack, builder);
        return null;
      }
    }
  }
}
