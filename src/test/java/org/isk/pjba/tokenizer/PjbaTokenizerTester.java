package org.isk.pjba.tokenizer;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.isk.pjba.tokenizer.core.GenericTokenizerTester;
import org.isk.pjba.tokenizer.core.token.TokenType;
import org.isk.pjba.unicode.CodePoints.Charset;
import org.isk.pjba.unicode.CodePoints.Reader;
import org.isk.pjba.unicode.UnicodeInputStream;

public class PjbaTokenizerTester extends GenericTokenizerTester<PjbaTokenizerWrapper, PjbaTokenizerTester> {

  public PjbaTokenizerTester(final PjbaTokenizerWrapper tokenizer) {
    super(tokenizer);
  }

  public static PjbaTokenizerTester fromString(final String source) {
    return new PjbaTokenizerTester(getTokenizer(source));
  }

  public static PjbaTokenizerTester fromFile(final String path) {
    return new PjbaTokenizerTester(getTokenizerFromFile(path));
  }

  private static PjbaTokenizerWrapper getTokenizer(final String string) {
    final Reader reader = new Reader(Charset.UTF8, new UnicodeInputStream(string.getBytes(StandardCharsets.UTF_8)));
    return new PjbaTokenizerWrapper(reader);
  }

  private static PjbaTokenizerWrapper getTokenizerFromFile(final String path) {
    final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    final Reader reader = new Reader(Charset.UTF8, new UnicodeInputStream(is));
    return new PjbaTokenizerWrapper(reader);
  }

  @Override
  public PjbaTokenizerTester getThis() {
    return this;
  }

  public PjbaTokenizerTester tokenizeOneLineComment() {
    this.actualToken = this.tokenizer.tokenizeOneLineComment(1, 1, this.tokenizer.reader().read());
    return this;
  }

  public PjbaTokenizerTester tokenizeMultiLinesComment() {
    this.tokenizer.reader().read();
    this.actualToken = this.tokenizer.tokenizeMultiLinesComment(1, 1);
    return this;
  }

  public PjbaTokenizerTester tokenizeEscapedCharacter() {
    final int codePoint = this.tokenizer.reader().read();
    this.tokenizer.sourceMapper().addCodePoint(codePoint);
    this.tokenizer.tokenizeEscapedCharacter(this.tokenizer.reader().read());

    this.actualToken = this.tokenizer.formattedValueToken(null, 1, 1);
    return this;
  }

  public PjbaTokenizerTester tokenizeString() {
    this.actualToken = this.tokenizer.tokenizeString(1, 1, this.tokenizer.reader().read());
    return this;
  }

  public PjbaTokenizerTester tokenizeNumber() {
    this.actualToken = this.tokenizer.tokenizeNumber(1, 1, this.tokenizer.reader().read());
    return this;
  }

  public PjbaTokenizerTester matchIdentifier(final String value) {
    return this.matchIdentifier(value, value.length());
  }

  public PjbaTokenizerTester matchIdentifier(final String value, final int readsBefore) {
    for (int i = 0; i < readsBefore; i++) {
      this.tokenizer.sourceMapper().addCodePoint(this.tokenizer.reader().read());
    }

    final TokenType tokenType = this.tokenizer.matchIdentifier(value.codePoints().toArray());
    this.actualToken = this.tokenizer.valueToken(tokenType, 1, 1);
    return this;
  }

  public PjbaTokenizerTester tokenizeIdentifier() {
    this.actualToken = this.tokenizer.tokenizeJavaIdentifier(1, 1, this.tokenizer.reader().read());
    return this;
  }
}
