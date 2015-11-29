package org.isk.pjba.tokenizer.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class TokenizerTest {

  // -------------------------------------------------------------------------------------------------------------------
  // Static methods
  // -------------------------------------------------------------------------------------------------------------------

  @Test
  public void isWhitespace_space() {
    final boolean isWhitespace = Tokenizer.isWhitespace(' ');

    assertThat(isWhitespace).isTrue();
  }

  @Test
  public void isWhitespace_tab() {
    final boolean isWhitespace = Tokenizer.isWhitespace('\t');

    assertThat(isWhitespace).isTrue();
  }

  @Test
  public void isWhitespace_nl() {
    final boolean isWhitespace = Tokenizer.isWhitespace('\n');

    assertThat(isWhitespace).isFalse();
  }

  @Test
  public void isNewLine_nl() {
    final boolean isNewLine = Tokenizer.isNewLine('\n');

    assertThat(isNewLine).isTrue();
  }

  @Test
  public void isNewLine_cr() {
    final boolean isNewLine = Tokenizer.isNewLine('\r');

    assertThat(isNewLine).isTrue();
  }

  @Test
  public void isNewLine_space() {
    final boolean isNewLine = Tokenizer.isNewLine('\r');

    assertThat(isNewLine).isTrue();
  }

  @Test
  public void isInvisibleChar_nl() {
    final boolean isInvisibleChar = Tokenizer.isInvisibleChar('\n');

    assertThat(isInvisibleChar).isTrue();
  }

  @Test
  public void isInvisibleChar_space() {
    final boolean isInvisibleChar = Tokenizer.isInvisibleChar(' ');

    assertThat(isInvisibleChar).isTrue();
  }

  @Test
  public void isInvisibleChar_letter() {
    final boolean isInvisibleChar = Tokenizer.isInvisibleChar('a');

    assertThat(isInvisibleChar).isFalse();
  }

  @Test
  public void isDigit_ok() {
    final boolean isDigit = Tokenizer.isDigit('2');

    assertThat(isDigit).isTrue();
  }

  @Test
  public void isDigit_ko() {
    final boolean isDigit = Tokenizer.isDigit(' ');

    assertThat(isDigit).isFalse();
  }

  @Test
  public void isAsciiLetter_ok() {
    final boolean isAsciiLetter = Tokenizer.isAsciiLetter('p');

    assertThat(isAsciiLetter).isTrue();
  }

  @Test
  public void isAsciiLetter_ko() {
    final boolean isAsciiLetter = Tokenizer.isAsciiLetter('1');

    assertThat(isAsciiLetter).isFalse();
  }

  @Test
  public void isHexChar_ok() {
    final boolean isHexChar = Tokenizer.isAsciiLetter('a');

    assertThat(isHexChar).isTrue();
  }

  @Test
  public void isHexChar_ko() {
    final boolean isHexChar = Tokenizer.isHexChar('g');

    assertThat(isHexChar).isFalse();
  }

  @Test
  public void isDigitOrAsciiLetter_letter() {
    final boolean isDigitOrAsciiLetter = Tokenizer.isDigitOrAsciiLetter('g');

    assertThat(isDigitOrAsciiLetter).isTrue();
  }

  @Test
  public void isDigitOrAsciiLetter_digit() {
    final boolean isDigitOrAsciiLetter = Tokenizer.isDigitOrAsciiLetter('0');

    assertThat(isDigitOrAsciiLetter).isTrue();
  }

  @Test
  public void isDigitOrAsciiLetter_underscore() {
    final boolean isDigitOrAsciiLetter = Tokenizer.isDigitOrAsciiLetter('_');

    assertThat(isDigitOrAsciiLetter).isFalse();
  }

  @Test
  public void isHexChar_ok_1() {
    final boolean isHexChar = Tokenizer.isHexChar('a');

    assertThat(isHexChar).isTrue();
  }

  @Test
  public void isHexChar_ok_2() {
    final boolean isHexChar = Tokenizer.isHexChar('9');

    assertThat(isHexChar).isTrue();
  }

  @Test
  public void isValidJavaIdentifierStartChar_ok_1() {
    final boolean b = Tokenizer.isValidJavaIdentifierStartChar('_');
    assertThat(b).isTrue();
  }

  @Test
  public void isValidJavaIdentifierStartChar_ok_2() {
    for (char c = 'A'; c <= 'Z'; c++) {
      final boolean b = Tokenizer.isValidJavaIdentifierStartChar(c);
      assertThat(b).isTrue();
    }
  }

  @Test
  public void isValidJavaIdentifierStartChar_ok_3() {
    for (char c = 'a'; c <= 'z'; c++) {
      final boolean b = Tokenizer.isValidJavaIdentifierStartChar(c);
      assertThat(b).isTrue();
    }
  }

  @Test
  public void isValidJavaIdentifierStartChar_ko_1() {
    for (char c = 0; c < '$'; c++) {
      final boolean b = Tokenizer.isValidJavaIdentifierStartChar(c);
      assertThat(b).isFalse();
    }
  }

  @Test
  public void isValidJavaIdentifierStartChar_ko_2() {
    for (char c = '$' + 1; c < 'A'; c++) {
      final boolean b = Tokenizer.isValidJavaIdentifierStartChar(c);
      assertThat(b).isFalse();
    }
  }

  @Test
  public void isValidJavaIdentifierStartChar_ko_3() {
    for (char c = 'Z' + 1; c < '_'; c++) {
      final boolean b = Tokenizer.isValidJavaIdentifierStartChar(c);
      assertThat(b).isFalse();
    }
  }

  @Test
  public void isValidJavaIdentifierStartChar_ko_4() {
    for (char c = '_' + 1; c < 'a'; c++) {
      final boolean b = Tokenizer.isValidJavaIdentifierStartChar(c);
      assertThat(b).isFalse();
    }
  }

  @Test
  public void isValidJavaIdentifierStartChar_ko_5() {
    for (char c = 'z' + 1; c <= 255; c++) {
      final boolean b = Tokenizer.isValidJavaIdentifierStartChar(c);
      assertThat(b).isFalse();
    }
  }

  @Test
  public void isValidJavaIdentifierChar_ok_1() {
    final boolean b = Tokenizer.isValidJavaIdentifierChar('_');
    assertThat(b).isTrue();
  }

  @Test
  public void isValidJavaIdentifierChar_ok_2() {
    for (char c = 'A'; c <= 'Z'; c++) {
      final boolean b = Tokenizer.isValidJavaIdentifierChar(c);
      assertThat(b).isTrue();
    }
  }

  @Test
  public void isValidJavaIdentifierChar_ok_3() {
    for (char c = 'a'; c <= 'z'; c++) {
      final boolean b = Tokenizer.isValidJavaIdentifierChar(c);
      assertThat(b).isTrue();
    }
  }

  @Test
  public void isValidJavaIdentifierChar_ok_4() {
    for (char c = '0'; c <= '9'; c++) {
      final boolean b = Tokenizer.isValidJavaIdentifierChar(c);
      assertThat(b).isTrue();
    }
  }

  @Test
  public void isValidJavaIdentifierChar_ko_1() {
    for (char c = 0; c < '$'; c++) {
      final boolean b = Tokenizer.isValidJavaIdentifierChar(c);
      assertThat(b).isFalse();
    }
  }

  @Test
  public void isValidJavaIdentifierChar_ko_2() {
    for (char c = '$' + 1; c < '0'; c++) {
      final boolean b = Tokenizer.isValidJavaIdentifierChar(c);
      assertThat(b).isFalse();
    }
  }

  @Test
  public void isValidJavaIdentifierChar_ko_3() {
    for (char c = '9' + 1; c < 'A'; c++) {
      final boolean b = Tokenizer.isValidJavaIdentifierChar(c);
      assertThat(b).isFalse();
    }
  }

  @Test
  public void isValidJavaIdentifierChar_ko_4() {
    for (char c = 'Z' + 1; c < '_'; c++) {
      final boolean b = Tokenizer.isValidJavaIdentifierChar(c);
      assertThat(b).isFalse();
    }
  }

  @Test
  public void isValidJavaIdentifierChar_ko_5() {
    for (char c = '_' + 1; c < 'a'; c++) {
      final boolean b = Tokenizer.isValidJavaIdentifierChar(c);
      assertThat(b).isFalse();
    }
  }

  @Test
  public void isValidJavaIdentifierChar_ko_6() {
    for (char c = 'z' + 1; c <= 255; c++) {
      final boolean b = Tokenizer.isValidJavaIdentifierChar(c);
      assertThat(b).isFalse();
    }
  }
}
