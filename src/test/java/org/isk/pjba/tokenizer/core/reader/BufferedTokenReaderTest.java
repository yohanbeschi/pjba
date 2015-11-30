package org.isk.pjba.tokenizer.core.reader;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;

import org.isk.pjba.tokenizer.core.FakeTokenizer;
import org.isk.pjba.tokenizer.core.token.CoreTokenType;
import org.isk.pjba.tokenizer.core.token.EndOfStreamToken;
import org.isk.pjba.tokenizer.core.token.Token;
import org.isk.pjba.unicode.CodePoints.Charset;
import org.isk.pjba.unicode.CodePoints.Reader;
import org.isk.pjba.unicode.UnicodeInputStream;
import org.junit.Test;

public class BufferedTokenReaderTest {
  private static final byte[] TEST_STRING = "idef var foo".getBytes(StandardCharsets.UTF_8);
  private static final byte[] TEST_MULTLINE_STRING = "Line one\nLine two \nLine three".getBytes(StandardCharsets.UTF_8);

  @Test
  public void next() {
    final TokenReader tokenReader = this.bufferedTokenReader(TEST_STRING);
    assertThat(tokenReader.next().type()).isEqualTo(FakeTokenizer.IDENTIFIER);
    assertThat(tokenReader.next().type()).isEqualTo(CoreTokenType.WHITESPACE);
    assertThat(tokenReader.next().type()).isEqualTo(FakeTokenizer.IDENTIFIER);
    assertThat(tokenReader.next().type()).isEqualTo(CoreTokenType.WHITESPACE);
    assertThat(tokenReader.next().type()).isEqualTo(FakeTokenizer.IDENTIFIER);
    assertThat(tokenReader.next()).isEqualTo(EndOfStreamToken.EOS);
    assertThat(tokenReader.next()).isEqualTo(EndOfStreamToken.EOS);
  }

  @Test
  public void unread() {
    final TokenReader tokenReader = this.bufferedTokenReader(TEST_STRING);
    tokenReader.unread(null);

    Token next = tokenReader.next();
    assertThat(next.type()).isEqualTo(FakeTokenizer.IDENTIFIER);

    tokenReader.unread(next);

    next = tokenReader.next();
    assertThat(next.type()).isEqualTo(FakeTokenizer.IDENTIFIER);

    next = tokenReader.next();
    assertThat(next.type()).isEqualTo(CoreTokenType.WHITESPACE);

    next = tokenReader.next();
    assertThat(next.type()).isEqualTo(FakeTokenizer.IDENTIFIER);

    next = tokenReader.next();
    assertThat(next.type()).isEqualTo(CoreTokenType.WHITESPACE);

    final Token next1 = tokenReader.next();
    assertThat(next1.type()).isEqualTo(FakeTokenizer.IDENTIFIER);

    final Token next2 = tokenReader.next();
    assertThat(next2).isEqualTo(EndOfStreamToken.EOS);

    final Token next3 = tokenReader.next();
    assertThat(next3).isEqualTo(EndOfStreamToken.EOS);

    tokenReader.unread(next3);
    tokenReader.unread(next2);
    tokenReader.unread(next1);

    next = tokenReader.next();
    assertThat(next.type()).isEqualTo(FakeTokenizer.IDENTIFIER);
  }

  @Test
  public void peek() {
    final TokenReader tokenReader = this.bufferedTokenReader(TEST_STRING);
    tokenReader.unread(null);
    assertThat(tokenReader.peek().type()).isEqualTo(FakeTokenizer.IDENTIFIER);

    final Token next = tokenReader.next();
    assertThat(next.type()).isEqualTo(FakeTokenizer.IDENTIFIER);
    tokenReader.unread(next);
    assertThat(tokenReader.next().type()).isEqualTo(FakeTokenizer.IDENTIFIER);
    assertThat(tokenReader.peek().type()).isEqualTo(CoreTokenType.WHITESPACE);
  }

  @Test
  public void ignoreLine() {
    final TokenReader tokenReader = this.bufferedTokenReader(TEST_MULTLINE_STRING);
    assertThat(tokenReader.next().type()).isEqualTo(FakeTokenizer.IDENTIFIER); // Line
    assertThat(tokenReader.next().type()).isEqualTo(CoreTokenType.WHITESPACE); // SPACE
    assertThat(tokenReader.next().type()).isEqualTo(FakeTokenizer.IDENTIFIER); // 1
    assertThat(tokenReader.next().type()).isEqualTo(CoreTokenType.NEW_LINE); // NL
    assertThat(tokenReader.next().type()).isEqualTo(FakeTokenizer.IDENTIFIER); // Line
    tokenReader.ignoreLine();
    assertThat(tokenReader.next().type()).isEqualTo(CoreTokenType.NEW_LINE); // NL
    assertThat(tokenReader.next().type()).isEqualTo(FakeTokenizer.IDENTIFIER);
    tokenReader.ignoreLine();
    assertThat(tokenReader.next()).isEqualTo(EndOfStreamToken.EOS);
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Helper
  // -------------------------------------------------------------------------------------------------------------------

  private TokenReader bufferedTokenReader(final byte[] s) {
    final Reader reader = new Reader(Charset.UTF8, new UnicodeInputStream(s));
    return new BufferedTokenReader(new FakeTokenizer(reader));
  }
}
