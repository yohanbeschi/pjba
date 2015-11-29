package org.isk.pjba.tokenizer.core;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.isk.pjba.tokenizer.core.token.EndOfStreamToken;
import org.isk.pjba.tokenizer.core.token.Token;
import org.isk.pjba.unicode.CodePoints.Charset;
import org.isk.pjba.unicode.CodePoints.Reader;
import org.isk.pjba.unicode.UnicodeInputStream;

public class TokenizerTester extends GenericTokenizerTester<FakeTokenizer, TokenizerTester> {
  public TokenizerTester(final FakeTokenizer tokenizer) {
    super(tokenizer);
  }

  public static TokenizerTester fromString(final String source) {
    return new TokenizerTester(getTokenizer(source));
  }

  public static TokenizerTester fromFile(final String path) {
    return new TokenizerTester(getTokenizerFromFile(path));
  }

  private static FakeTokenizer getTokenizer(final String string) {
    final Reader reader = new Reader(Charset.UTF8, new UnicodeInputStream(string.getBytes(StandardCharsets.UTF_8)));
    return new FakeTokenizer(reader);
  }

  private static FakeTokenizer getTokenizerFromFile(final String path) {
    final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    final Reader reader = new Reader(Charset.UTF8, new UnicodeInputStream(is));
    return new FakeTokenizer(reader);
  }

  @Override
  public TokenizerTester getThis() {
    return this;
  }

  //-------------------------------------------------------------------------------------------------------------------
  // Helpers
  // -------------------------------------------------------------------------------------------------------------------

  public static void generateExpectedResult(final String file) {
    final Tokenizer tokenizer = getTokenizerFromFile(file);
    Token token = null;
    while ((token = tokenizer.getNextToken()) != EndOfStreamToken.EOS) {
      System.out.println(".getNextToken().assertTokenType(TokenType." + token.type().name() //
          + ").values(\"" //
          + toString(token.value()) + "\", \"" + toString(token.formattedValue()) //
          + "\").start(" + token.startLine() + ", " + token.startColumn() //
          + ").end(" + token.endLine() + ", " + token.endColumn() + ").assertNoErrors() //");
    }
  }

  private static String toString(final int[] value) {
    final StringBuilder sb = new StringBuilder();

    for (final int i : value) {
      final char c = (char) i;

      switch (c) {
      case '\n':
        sb.append("\\n");
        break;
      default:
        sb.append(c);
      }

    }

    return sb.toString();
  }
}
