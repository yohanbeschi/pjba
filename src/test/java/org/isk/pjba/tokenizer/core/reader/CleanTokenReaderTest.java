package org.isk.pjba.tokenizer.core.reader;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.isk.pjba.tokenizer.PjbaTokenType;
import org.isk.pjba.tokenizer.core.token.CoreTokenType;
import org.isk.pjba.tokenizer.core.token.Token;
import org.junit.Test;

public class CleanTokenReaderTest {
  private static final String TEST_STRING = "idef 'String' 1";

  private static final String TEST_MULTLINE_STRING = //
  "  @ Comment \n" //
      + "  Line one  \n" //
      + "  Line /* comment */ two \n" //
      + "Line      three";

  @Test
  public void squashWhiteSpaces() {
    CleanTokenReaderTester.fromString(TEST_MULTLINE_STRING) //
        .next().assertTokenType(CoreTokenType.NEW_LINE).value("  @ Comment \n  ").start(1, 1).end(2, 3).assertNoErrors() //
        .next().assertTokenType(PjbaTokenType.IDENTIFIER).value("Line").start(2, 3).end(2, 7).assertNoErrors() //
        .next().assertTokenType(CoreTokenType.WHITESPACE).value(" ").start(2, 7).end(2, 8).assertNoErrors() //
        .next().assertTokenType(PjbaTokenType.IDENTIFIER).value("one").start(2, 8).end(2, 11).assertNoErrors() //
        .next().assertTokenType(CoreTokenType.NEW_LINE).value("  \n  ").start(2, 11).end(3, 3).assertNoErrors() //
        .next().assertTokenType(PjbaTokenType.IDENTIFIER).value("Line").start(3, 3).end(3, 7).assertNoErrors() //
        .next().assertTokenType(CoreTokenType.WHITESPACE).value(" /* comment */ ").start(3, 7).end(3, 22)
        .assertNoErrors() //
        .next().assertTokenType(PjbaTokenType.IDENTIFIER).value("two").start(3, 22).end(3, 25).assertNoErrors() //
        .next().assertTokenType(CoreTokenType.NEW_LINE).value(" \n").start(3, 25).end(4, 1).assertNoErrors() //
        .next().assertTokenType(PjbaTokenType.IDENTIFIER).value("Line").start(4, 1).end(4, 5).assertNoErrors() //
        .next().assertTokenType(CoreTokenType.WHITESPACE).value("      ").start(4, 5).end(4, 11).assertNoErrors() //
        .next().assertTokenType(PjbaTokenType.IDENTIFIER).value("three").start(4, 11).end(4, 16).assertNoErrors() //
        .next().assertTokenType(CoreTokenType.EOS).assertNoErrors();
  }

  @Test
  public void invalidateLine() {
    CleanTokenReaderTester.fromString(TEST_MULTLINE_STRING) //
        // Line 1
        .next().assertTokenType(CoreTokenType.NEW_LINE).assertNoErrors() //
        // Line 2
        .next().assertTokenType(PjbaTokenType.IDENTIFIER).assertNoErrors() //
        .invalidateLine() //
        .next().assertTokenType(CoreTokenType.NEW_LINE).assertNoErrors() //
        // Line 3
        .next().assertTokenType(PjbaTokenType.IDENTIFIER).assertNoErrors() //
        .next().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        .next().assertTokenType(PjbaTokenType.IDENTIFIER).assertNoErrors() //
        .next().assertTokenType(CoreTokenType.NEW_LINE).assertNoErrors() //
        // Line 4
        .next().assertTokenType(PjbaTokenType.IDENTIFIER).assertNoErrors() //
        .next().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        .invalidateLine() //
        .next().assertTokenType(CoreTokenType.EOS).assertNoErrors() //
        // Reread the list of tokens
        .restart() //
        .next().assertTokenType(CoreTokenType.NEW_LINE).assertNoErrors() //
        // Line 2
        .next().assertTokenType(PjbaTokenType.IDENTIFIER).assertNoErrors() //
        .next().assertTokenType(CoreTokenType.WHITESPACE).assertErrors(1) //
        .next().assertTokenType(PjbaTokenType.IDENTIFIER).assertErrors(1) //
        .next().assertTokenType(CoreTokenType.NEW_LINE).assertNoErrors() //
        // Line 3
        .next().assertTokenType(PjbaTokenType.IDENTIFIER).assertNoErrors() //
        .next().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        .next().assertTokenType(PjbaTokenType.IDENTIFIER).assertNoErrors() //
        .next().assertTokenType(CoreTokenType.NEW_LINE).assertNoErrors() //
        // Line 4
        .next().assertTokenType(PjbaTokenType.IDENTIFIER).assertNoErrors() //
        .next().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        .next().assertTokenType(PjbaTokenType.IDENTIFIER).assertErrors(1) //
        .next().assertTokenType(CoreTokenType.EOS).assertNoErrors();
  }

  @Test
  public void tokens() {
    final List<Token> tokens = CleanTokenReaderTester.fromString(TEST_MULTLINE_STRING).get().tokens();
    assertThat(tokens).hasSize(12);
  }

  @Test
  public void read() {
    CleanTokenReaderTester.fromString(TEST_STRING) //
        .next().assertTokenType(PjbaTokenType.IDENTIFIER) //
        .next().assertTokenType(CoreTokenType.WHITESPACE) //
        .next().assertTokenType(PjbaTokenType.STRING) //
        .next().assertTokenType(CoreTokenType.WHITESPACE) //
        .next().assertTokenType(PjbaTokenType.INTEGER) //
        .next().assertTokenType(CoreTokenType.EOS) //
        .next().assertTokenType(CoreTokenType.EOS) //
        .next().assertTokenType(CoreTokenType.EOS);
  }

  @Test
  public void peek() {
    CleanTokenReaderTester.fromString(TEST_STRING) //
        .peek().assertTokenType(PjbaTokenType.IDENTIFIER) //
        .next().assertTokenType(PjbaTokenType.IDENTIFIER) //
        .peek().assertTokenType(CoreTokenType.WHITESPACE) //
        .next().assertTokenType(CoreTokenType.WHITESPACE) //
        .next().assertTokenType(PjbaTokenType.STRING) //
        .next().assertTokenType(CoreTokenType.WHITESPACE) //
        .next().assertTokenType(PjbaTokenType.INTEGER) //
        .peek().assertTokenType(CoreTokenType.EOS) //
        .next().assertTokenType(CoreTokenType.EOS);
  }

  @Test
  public void unread() {
    CleanTokenReaderTester.fromString(TEST_STRING) //
        .unread() //
        .next().assertTokenType(PjbaTokenType.IDENTIFIER) //
        .unread() //
        .next().assertTokenType(PjbaTokenType.IDENTIFIER) //
        .next().assertTokenType(CoreTokenType.WHITESPACE) //
        .next().assertTokenType(PjbaTokenType.STRING) //
        .next().assertTokenType(CoreTokenType.WHITESPACE) //
        .next().assertTokenType(PjbaTokenType.INTEGER) //
        .next().assertTokenType(CoreTokenType.EOS) //
        .unread() //
        .next().assertTokenType(PjbaTokenType.INTEGER) //
        .next().assertTokenType(CoreTokenType.EOS) //
        .next().assertTokenType(CoreTokenType.EOS);
  }

  @Test
  public void skipToBefore_present() {
    CleanTokenReaderTester.fromString(TEST_STRING) //
        .skipToBefore(PjbaTokenType.INTEGER) //
        .next().assertTokenType(PjbaTokenType.INTEGER) //
        .next().assertTokenType(CoreTokenType.EOS);
  }

  @Test
  public void skipToBefore_notPresent() {
    CleanTokenReaderTester.fromString(TEST_STRING) //
        .skipToBefore(PjbaTokenType.FLOAT) //
        .next().assertTokenType(CoreTokenType.EOS);
  }

  @Test
  public void skipTo_present() {
    CleanTokenReaderTester.fromString(TEST_STRING) //
        .skipTo(PjbaTokenType.STRING) //
        .next().assertTokenType(CoreTokenType.WHITESPACE) //
        .next().assertTokenType(PjbaTokenType.INTEGER) //
        .next().assertTokenType(CoreTokenType.EOS);
  }

  @Test
  public void skipTo_notPresent() {
    CleanTokenReaderTester.fromString(TEST_STRING) //
        .skipTo(PjbaTokenType.FLOAT) //
        .next().assertTokenType(CoreTokenType.EOS);
  }
}
