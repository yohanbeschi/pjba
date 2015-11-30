package org.isk.pjba.tokenizer.core.reader;

import java.util.ArrayDeque;
import java.util.Deque;

import org.isk.pjba.tokenizer.core.Tokenizer;
import org.isk.pjba.tokenizer.core.token.EndOfStreamToken;
import org.isk.pjba.tokenizer.core.token.Token;

/**
 * <p>
 * A <code>BufferedTokenReader</code> offers the ability to read, peek and unread a {@link Token}.
 * <p>
 * Unread tokens are cached in a {@link Deque} with an initial size of 16.
 */
public class BufferedTokenReader implements TokenReader {
  private final Tokenizer tokenizer;
  private final Deque<Token> buffer = new ArrayDeque<>(16);

  /**
   * Instantiates a new <code>BufferedTokenReader</code> with a {@link Tokenizer}.
   *
   * @param tokenizer
   *          is the tokenizer that will be used to find the next tokens.
   */
  public BufferedTokenReader(final Tokenizer tokenizer) {
    this.tokenizer = tokenizer;
  }

  /*
   * {@inheritDoc}
   */
  @Override
  public Token next() {
    if (!this.buffer.isEmpty()) {
      return this.buffer.pop();
    } else {
      return this.tokenizer.getNextToken();
    }
  }

  /*
   * {@inheritDoc}
   */
  @Override
  public Token peek() {
    final Token token = this.next();
    this.unread(token);
    return token;
  }

  /*
   * {@inheritDoc}
   */
  @Override
  public void unread(final Token token) {
    if (token != null && token != EndOfStreamToken.EOS) {
      this.buffer.push(token);
    }
  }
}
