package org.isk.pjba.tokenizer.core;

import org.isk.pjba.tokenizer.core.token.Token;
import org.isk.pjba.tokenizer.core.token.TokenType;
import org.isk.pjba.unicode.CodePoints.Reader;

public class FakeTokenizer extends Tokenizer {
  public static final TokenType IDENTIFIER = new TokenType("IDENTIFIER", false, false, false);

  public FakeTokenizer(final Reader reader) {
    super(reader);
  }

  @Override
  public Token tokenizeNextElement() {
    final int startColumn = this.sourceMapper.currentColumn;
    final int startLine = this.sourceMapper.currentLine;
    final int character = this.reader.read();

    if (isInvisibleChar(character)) { // WHITESPACE or // NEW_LINE
      return this.tokenizeInsivibleChars(startLine, startColumn, character);
    } else if (Tokenizer.isValidJavaIdentifierStartChar(character)) { // Identifier (core and user) or keyword
      return this.tokenizeJavaIdentifier(startLine, startColumn, character);
    } else {
      this.sourceMapper.addCodePoint(character);
      return this.undefinedToken(startLine, startColumn);
    }
  }

  /* private testing */ Token tokenizeJavaIdentifier(final int startLine, final int startColumn, final int character) {
    this.sourceMapper.addCodePoint(character);

    int current = -1;
    while ((current = this.reader.read()) != -1 && Tokenizer.isValidJavaIdentifierChar(current)) {
      this.sourceMapper.addCodePoint(current);
    }

    this.reader.unread(current);

    return this.valueToken(IDENTIFIER, startLine, startColumn);
  }
}
