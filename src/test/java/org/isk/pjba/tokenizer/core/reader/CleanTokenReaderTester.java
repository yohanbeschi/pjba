package org.isk.pjba.tokenizer.core.reader;

import java.nio.charset.StandardCharsets;

import org.isk.pjba.tokenizer.PjbaTokenizer;
import org.isk.pjba.tokenizer.core.token.GenericTokenTester;
import org.isk.pjba.tokenizer.core.token.TokenType;
import org.isk.pjba.unicode.CodePoints.Charset;
import org.isk.pjba.unicode.CodePoints.Reader;
import org.isk.pjba.unicode.UnicodeInputStream;

public class CleanTokenReaderTester extends GenericTokenTester<CleanTokenReaderTester> {

  private final CleanTokenReader reader;

  public CleanTokenReaderTester(final CleanTokenReader reader) {
    super();
    this.reader = reader;
  }

  public static CleanTokenReaderTester fromString(final String source) {
    return new CleanTokenReaderTester(getReader(source));
  }

  private static CleanTokenReader getReader(final String string) {
    final Reader reader = new Reader(Charset.UTF8, new UnicodeInputStream(string.getBytes(StandardCharsets.UTF_8)));
    return new CleanTokenReader(new PjbaTokenizer(reader));
  }

  @Override
  public CleanTokenReaderTester getThis() {
    return this;
  }

  public CleanTokenReaderTester next() {
    this.actualToken = this.reader.next();
    return this.getThis();
  }

  public CleanTokenReaderTester peek() {
    this.actualToken = this.reader.peek();
    return this.getThis();
  }

  public CleanTokenReaderTester unread() {
    this.reader.unread(null);
    return this.getThis();
  }

  public CleanTokenReaderTester skipToBefore(final TokenType tokenType) {
    this.reader.skipToBefore(tokenType);
    return this.getThis();
  }

  public CleanTokenReaderTester skipTo(final TokenType tokenType) {
    this.reader.skipTo(tokenType);
    return this.getThis();
  }

  public CleanTokenReaderTester restart() {
    this.reader.restart();
    return this.getThis();
  }

  public CleanTokenReaderTester invalidateLine() {
    this.reader.invalidateLine();
    return this.getThis();
  }

  public CleanTokenReader get() {
    return this.reader;
  }
}
