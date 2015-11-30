package org.isk.pjba.tokenizer.core.reader;

import org.isk.pjba.tokenizer.core.error.CoreErrorType;
import org.isk.pjba.tokenizer.core.token.EndOfStreamToken;
import org.isk.pjba.tokenizer.core.token.Token;

/**
 * A <code>TokenReader</code> offers the ability to read, peek and unread a {@link Token}.
 */
public interface TokenReader {

  /**
   * Returns the next token.
   *
   * @return the next token or {@link EndOfStreamToken#EOS} if there is nothing else to be read.
   */
  Token next();

  /**
   * <p>
   * Returns the next token without consuming it.
   * <p>
   * This token will be returned the next time {@link #next()} or {@link #peek()} will be called.
   *
   * @return the next token or {@link EndOfStreamToken#EOS} if there is nothing else to be read.
   */
  Token peek();

  /**
   * <p>
   * Adds a token to the reader to be returned the next time {@link #next()} or {@link #peek()} will be called.
   * <p>
   * Depending on the implementation, the parameter MAY be ignored, and the last consumed token will be added to the
   * reader.
   *
   * @param token
   *          is the token to unread.
   */
  void unread(Token token);

  /**
   * <p>
   * Consumes every tokens until a multiple lines whitespace token is reached.
   * <p>
   * An error of type {@link CoreErrorType#INVALID_TOKEN} is added to each tokens.
   * <p>
   * Note: if the implementing class doesn't keep track of each read token, using this method is useless.
   */
  default void invalidateLine() {
    for (;;) {
      final Token token = this.next();

      if (token == EndOfStreamToken.EOS) {
        break;
      } else if (token.type().isMultiLinesWhitespace()) {
        this.unread(token);
        break;
      } else {
        token.addError(CoreErrorType.INVALID_TOKEN);
      }
    }
  }

  /**
   * Consumes every tokens until a multiple lines whitespace token is reached.
   */
  default void ignoreLine() {
    for (;;) {
      final Token token = this.next();

      if (token == EndOfStreamToken.EOS) {
        break;
      } else if (token.type().isMultiLinesWhitespace()) {
        this.unread(token);
        break;
      }
    }
  }
}
