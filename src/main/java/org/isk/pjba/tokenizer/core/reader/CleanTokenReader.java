package org.isk.pjba.tokenizer.core.reader;

import java.util.ArrayList;
import java.util.List;

import org.isk.pjba.tokenizer.core.Tokenizer;
import org.isk.pjba.tokenizer.core.token.CoreTokenType;
import org.isk.pjba.tokenizer.core.token.EndOfStreamToken;
import org.isk.pjba.tokenizer.core.token.Token;
import org.isk.pjba.tokenizer.core.token.TokenType;
import org.isk.pjba.unicode.CodePoints.Reader;

/**
 * <p>
 * A <code>CleanTokenReader</code> offers the ability to read, peek and unread a {@link Token}.
 * <p>
 * When initialized a <code>CleanTokenReader</code> build a list of all tokens that a {@link Tokenizer} can find.
 */
public class CleanTokenReader implements TokenReader {
  private final List<Token> tokens = new ArrayList<>();
  private final BufferedTokenReader tokenReader;
  private int index = 0;

  /**
   * Instantiates a new <code>BufferedTokenReader</code> with a {@link Tokenizer}.
   *
   * @param tokenizer
   *          is the tokenizer that will be used build the list tokens.
   */
  public CleanTokenReader(final Tokenizer tokenizer) {
    super();
    this.tokenReader = new BufferedTokenReader(tokenizer);
    this.tokenize();
  }

  /**
   * Processes all the data in the {@link Reader} used by the {@link Tokenizer}, to create a list of tokens.
   */
  private void tokenize() {
    for (;;) {
      final Token token = this.tokenReader.next();
      if (token == EndOfStreamToken.EOS) {
        break;
      } else {
        final TokenType type = token.type();

        if (type.isWhitespace()) {
          this.squashWhiteSpaces(token);
        }

        this.tokens.add(token);
      }
    }
  }

  /**
   * Squashes successive whitespace tokens into one.
   *
   * @param token
   *          is the last token read.
   */
  private void squashWhiteSpaces(final Token token) {
    for (;;) {
      final Token next = this.tokenReader.next();

      final TokenType type = next.type();
      if (!type.isWhitespace()) {
        this.tokenReader.unread(next);
        break;
      } else if (token.type().isMultiLinesWhitespace() || next.type().isMultiLinesWhitespace()) {
        token.addAfter(CoreTokenType.NEW_LINE, next);
      } else {
        token.addAfter(CoreTokenType.WHITESPACE, next);
      }
    }
  }

  /**
   * Reset the counter to 0. {@link #next()} and {@link #peek()} will read the first token.
   */
  public void restart() {
    this.index = 0;
  }

  /**
   * Returns all processed tokens.
   *
   * @return all processed tokens.
   */
  public List<Token> tokens() {
    return this.tokens;
  }

  /*
   * {@inheritDoc}
   */
  @Override
  public Token next() {
    if (this.index < this.tokens.size()) {
      return this.tokens.get(this.index++);
    } else {
      return EndOfStreamToken.EOS;
    }
  }

  /*
   * {@inheritDoc}
   */
  @Override
  public Token peek() {
    if (this.index < this.tokens.size()) {
      return this.tokens.get(this.index);
    } else {
      return EndOfStreamToken.EOS;
    }
  }

  /*
   * {@inheritDoc}
   */
  @Override
  public void unread(final Token token) {
    if (this.index > 0) {
      this.index--;
    }
  }

  /**
   * Skips all tokens until finding the token type in parameter (without consuming it) or the EOS.
   *
   * @param tokenType
   *          is the token type to find.
   */
  public void skipToBefore(final TokenType tokenType) {
    while (this.peek() != EndOfStreamToken.EOS && this.peek().type() != tokenType) {
      this.next();
    }
  }

  /**
   * Skips all tokens until finding the token type in parameter (consuming it) or the EOS
   *
   * @param tokenType
   *          is the token type to find.
   */
  public void skipTo(final TokenType tokenType) {
    while (this.peek() != EndOfStreamToken.EOS && this.next().type() != tokenType) {
    }
  }
}
