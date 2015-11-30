package org.isk.pjba.tokenizer;

import org.isk.pjba.tokenizer.core.SourceMapper;
import org.isk.pjba.tokenizer.core.token.Token;
import org.isk.pjba.tokenizer.core.token.TokenType;
import org.isk.pjba.unicode.CodePoints.Reader;

public class PjbaTokenizerWrapper extends PjbaTokenizer {

  public PjbaTokenizerWrapper(final Reader reader) {
    super(reader);
  }

  public Reader reader() {
    return this.reader;
  }

  public SourceMapper sourceMapper() {
    return this.sourceMapper;
  }

  @Override
  public Token constantToken(final TokenType tokenType, final int startLine, final int startColumn) {
    return super.constantToken(tokenType, startLine, startColumn);
  }

  @Override
  public Token valueToken(final TokenType tokenType, final int startLine, final int startColumn) {
    return super.valueToken(tokenType, startLine, startColumn);
  }

  @Override
  public Token formattedValueToken(final TokenType tokenType, final int startLine, final int startColumn) {
    return super.formattedValueToken(tokenType, startLine, startColumn);
  }
}
