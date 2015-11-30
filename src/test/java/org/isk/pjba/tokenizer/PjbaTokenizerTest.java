package org.isk.pjba.tokenizer;

import org.isk.pjba.tokenizer.core.token.CoreTokenType;
import org.isk.pjba.tokenizer.core.token.TokenType;
import org.junit.Test;

public class PjbaTokenizerTest {
  @Test
  public void tokenizeInsivibleChars_space() {
    final String s = " ";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeInsivibleChars().assertTokenType(CoreTokenType.WHITESPACE).value(s).start(1, 1).end(1, 2)
        .assertNoErrors();
  }

  @Test
  public void tokenizeInsivibleChars_tab() {
    final String s = "\t";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeInsivibleChars().assertTokenType(CoreTokenType.WHITESPACE).value(s).start(1, 1).end(1, 3)
        .assertNoErrors();
  }

  @Test
  public void tokenizeInsivibleChars_spacesAndTabs() {
    final String s = " \t  \t  \t  ";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeInsivibleChars().assertTokenType(CoreTokenType.WHITESPACE).value(s).start(1, 1).end(1, 14)
        .assertNoErrors();
  }

  @Test
  public void tokenizeInsivibleChars_cr() {
    final String s = "\r";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeInsivibleChars().assertTokenType(CoreTokenType.NEW_LINE).value(s).start(1, 1).end(2, 1)
        .assertNoErrors();
  }

  @Test
  public void tokenizeInsivibleChars_lf() {
    final String s = "\n";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeInsivibleChars().assertTokenType(CoreTokenType.NEW_LINE).value(s).start(1, 1).end(2, 1)
        .assertNoErrors();
  }

  @Test
  public void tokenizeInsivibleChars_crlf() {
    final String s = "\r\n";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeInsivibleChars().assertTokenType(CoreTokenType.NEW_LINE).value(s).start(1, 1).end(2, 1)
        .assertNoErrors();
  }

  @Test
  public void tokenizeInsivibleChars_whitespacesAndNewLines() {
    final String s = " \t \r\n \n";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeInsivibleChars().assertTokenType(CoreTokenType.NEW_LINE).value(s).start(1, 1).end(3, 1)
        .assertNoErrors();
  }

  @Test
  public void tokenizeInsivibleChars_whitespacesAndNewLinesWithSpaceIndentation() {
    final String s = " \t \r\n \n  ";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeInsivibleChars().assertTokenType(CoreTokenType.NEW_LINE).value(s).start(1, 1).end(3, 3)
        .assertNoErrors();
  }

  @Test
  public void tokenizeInsivibleChars_whitespacesAndNewLinesWithTabIndentation() {
    final String s = " \t \r\n \n\t";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeInsivibleChars().assertTokenType(CoreTokenType.NEW_LINE).value(s).start(1, 1).end(3, 3)
        .assertNoErrors();
  }

  @Test
  public void tokenizeOneLineComment_at() {
    final String s = "@ This is a one line comment ";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeOneLineComment().assertTokenType(PjbaTokenType.OL_COMMENT).value(s).start(1, 1).end(1, 30)
        .assertNoErrors();
  }

  @Test
  public void tokenizeOneLineComment_hash() {
    final String s = "# This is a one line comment ";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeOneLineComment().assertTokenType(PjbaTokenType.OL_COMMENT).value(s).start(1, 1).end(1, 30)
        .assertNoErrors();
  }

  @Test
  public void tokenizeMultiLinesComment() {
    final String s = "/* This is a \n * multi lines \n * comment \n */";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeMultiLinesComment().assertTokenType(PjbaTokenType.ML_COMMENT).value(s).start(1, 1).end(4, 4)
        .assertNoErrors();
  }

  @Test
  public void tokenizeEscapedCharacter_backslash() {
    final String s = "\\\\"; // Plume source as: \\

    PjbaTokenizerTester.fromString(s) //
        .tokenizeEscapedCharacter().assertTokenType(null).values(s, "\\").start(1, 1).end(1, 3).assertNoErrors();
  }

  @Test
  public void tokenizeEscapedCharacter_backspace() {
    final String s = "\\b"; // Plume source as: \b

    PjbaTokenizerTester.fromString(s) //
        .tokenizeEscapedCharacter().assertTokenType(null).values(s, "\b").start(1, 1).end(1, 3).assertNoErrors();
  }

  @Test
  public void tokenizeEscapedCharacter_ff() {
    final String s = "\\f"; // Plume source as: \f

    PjbaTokenizerTester.fromString(s) //
        .tokenizeEscapedCharacter().assertTokenType(null).values(s, "\f").start(1, 1).end(1, 3).assertNoErrors();
  }

  @Test
  public void tokenizeEscapedCharacter_nl() {
    final String s = "\\n"; // Plume source as: \n

    PjbaTokenizerTester.fromString(s) //
        .tokenizeEscapedCharacter().assertTokenType(null).values(s, "\n").start(1, 1).end(1, 3).assertNoErrors();
  }

  @Test
  public void tokenizeEscapedCharacter_cr() {
    final String s = "\\r"; // Plume source as: \r

    PjbaTokenizerTester.fromString(s) //
        .tokenizeEscapedCharacter().assertTokenType(null).values(s, "\r").start(1, 1).end(1, 3).assertNoErrors();
  }

  @Test
  public void tokenizeEscapedCharacter_space() {
    final String s = "\\s"; // Plume source as: \s

    PjbaTokenizerTester.fromString(s) //
        .tokenizeEscapedCharacter().assertTokenType(null).values(s, " ").start(1, 1).end(1, 3).assertNoErrors();
  }

  @Test
  public void tokenizeEscapedCharacter_tab() {
    final String s = "\\t"; // Plume source as: \t

    PjbaTokenizerTester.fromString(s) //
        .tokenizeEscapedCharacter().assertTokenType(null).values(s, "\t").start(1, 1).end(1, 3).assertNoErrors();
  }

  @Test
  public void tokenizeEscapedCharacter_unicode_bmp() {
    final String s = "\\u450"; // Plume source as: \ u450 (without the space - Thanks java comments)

    PjbaTokenizerTester.fromString(s) //
        .tokenizeEscapedCharacter().assertTokenType(null).values(s, "\u0450").start(1, 1).end(1, 6).assertNoErrors();
  }

  @Test
  public void tokenizeEscapedCharacter_unicode_outbmp() {
    final String s = "\\u10083"; // Plume source as: \ u10083

    PjbaTokenizerTester.fromString(s) //
        .tokenizeEscapedCharacter() //
        .assertTokenType(null).values(s, new String(new int[] { 0x10083 }, 0, 1)).start(1, 1).end(1, 8)
        .assertNoErrors();
  }

  @Test
  public void tokenizeEscapedCharacter_invalidUnicode_emptyHex() {
    final String s = "\\u"; // Plume source as: \ u

    PjbaTokenizerTester.fromString(s) //
        .tokenizeEscapedCharacter().assertTokenType(null).value(s).start(1, 1).end(1, 3) //
        .assertErrors(1) //
        .assertError(PjbaErrorType.INVALID_CODEPOINT, 1, 1);
  }

  @Test
  public void tokenizeEscapedCharacter_invalidUnicode_notValidHex() {
    final String s = "\\uzyu"; // Plume source as: \ uzyu

    PjbaTokenizerTester.fromString(s) //
        .tokenizeEscapedCharacter().assertTokenType(null).value("\\u").start(1, 1).end(1, 3) //
        .assertErrors(1) //
        .assertError(PjbaErrorType.INVALID_CODEPOINT, 1, 1);
  }

  @Test
  public void tokenizeEscapedCharacter_invalidUnicode_invalidCodePoint() {
    final String s = "\\uffffff"; // Plume source as: \ uffffff

    PjbaTokenizerTester.fromString(s) //
        .tokenizeEscapedCharacter().assertTokenType(null).value(s).start(1, 1).end(1, 9) //
        .assertErrors(1) //
        .assertError(PjbaErrorType.INVALID_CODEPOINT, 1, 1);
  }

  @Test
  public void tokenizeEscapedCharacter_invalidUnicode_tooBigValue() {
    final String s = "\\uffffffff"; // Plume source as: \ uffffffff

    PjbaTokenizerTester.fromString(s) //
        .tokenizeEscapedCharacter().assertTokenType(null).value(s).start(1, 1).end(1, 11) //
        .assertErrors(1) //
        .assertError(PjbaErrorType.INVALID_CODEPOINT, 1, 1);
  }

  @Test
  public void tokenizeEscapedCharacter_invalidEscapeSequence() {
    final String s = "\\z"; // Plume source as: \z

    PjbaTokenizerTester.fromString(s) //
        .tokenizeEscapedCharacter().assertTokenType(null).value(s).start(1, 1).end(1, 3) //
        .assertErrors(1) //
        .assertError(PjbaErrorType.INVALID_ESCAPE_SEQUENCE, 1, 1);
  }

  @Test
  public void tokenizeString_simple() {
    final String formattedValue = "This is a simple string";
    final String s = "'" + formattedValue + "'";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeString().assertTokenType(PjbaTokenType.STRING).values(s, formattedValue).start(1, 1).end(1, 26)
        .assertNoErrors();
  }

  @Test
  public void tokenizeString_emptyString() {
    final String s = "''";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeString().assertTokenType(PjbaTokenType.STRING).values(s, "").start(1, 1).end(1, 3).assertNoErrors();
  }

  @Test
  public void tokenizeString_escapedSimpleQuote() {
    final String formattedValue = "It's not christmas";
    final String s = "'It\\'s not christmas'";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeString().assertTokenType(PjbaTokenType.STRING).values(s, formattedValue).start(1, 1).end(1, 22)
        .assertNoErrors();
  }

  @Test
  public void tokenizeString_escapedChars() {
    final String formattedValue = "A string with \\\\ an escaped \t tab";
    final String s = "'A string with \\\\\\\\ an escaped \\t tab'";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeString().assertTokenType(PjbaTokenType.STRING).values(s, formattedValue).start(1, 1).end(1, 39)
        .assertNoErrors();
  }

  @Test
  public void tokenizeString_unicode() {
    final String formattedValue = "привет, весь мир";
    final String s = "'" + formattedValue + "'";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeString().assertTokenType(PjbaTokenType.STRING).values(s, formattedValue).start(1, 1).end(1, 19)
        .assertNoErrors();
  }

  @Test
  public void tokenizeString_escapedUnicode_singleCodePoint() {
    final String formattedValue = "п";
    final String s = "'\\u043f'";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeString().assertTokenType(PjbaTokenType.STRING).values(s, formattedValue).start(1, 1).end(1, 9)
        .assertNoErrors();
  }

  @Test
  public void tokenizeString_escapedUnicode() {
    final String formattedValue = "привет, весь мир";
    final String s = "'\\u043f\\u0440\\u0438\\u0432\\u0435\\u0442\\u002c\\u0020\\u0432\\u0435\\u0441\\u044c\\u0020\\u043c\\u0438\\u0440'";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeString().assertTokenType(PjbaTokenType.STRING).values(s, formattedValue).start(1, 1).end(1, 99)
        .assertNoErrors();
  }

  @Test
  public void tokenizeString_multiline() {
    final String formattedValue = "This string \n expands \r over \r\n 3 lines.";
    final String s = "'" + formattedValue + "'";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeString().assertTokenType(PjbaTokenType.STRING).values(s, formattedValue).start(1, 1).end(4, 11)
        .assertNoErrors();
  }

  @Test
  public void tokenizeString_noEndSimpleQuote() {
    final String formattedValue = "";
    final String s = "'This string doesn\\'t end properly";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeString().assertTokenType(PjbaTokenType.STRING).values(s, formattedValue).start(1, 1).end(1, 35)
        .assertErrors(1) //
        .assertError(PjbaErrorType.NOT_ENDING_STRING, 1, 35);
  }

  @Test
  public void tokenizeString_invalidUnicode() {
    final String formattedValue = "";
    final String s = "'This string contains an invalid \\uz450 unicode character'";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeString().assertTokenType(PjbaTokenType.STRING).values(s, formattedValue).start(1, 1).end(1, 59)
        .assertErrors(1) //
        .assertError(PjbaErrorType.INVALID_CODEPOINT, 1, 34);
  }

  @Test
  public void tokenizeString_invalidSequence() { // Invalid sequence (\e) has been removed
    final String formattedValue = "";
    final String s = "'This string contains an \\e invalid escape character but we don\\'t care'";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeString().assertTokenType(PjbaTokenType.STRING).values(s, formattedValue).start(1, 1).end(1, 73)
        .assertErrors(1) //
        .assertError(PjbaErrorType.INVALID_ESCAPE_SEQUENCE, 1, 26);
  }

  @Test
  public void tokenizeNumber_integer_oneDigit() {
    final String s = "9";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.INTEGER).values(s, s).start(1, 1).end(1, 2).assertNoErrors();
  }

  @Test
  public void tokenizeNumber_integer_oneDigit_plus() {
    final String s = "+9";
    final String formattedValue = "9";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.INTEGER).values(s, formattedValue).start(1, 1).end(1, 3)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_integer_oneDigit_minus() {
    final String s = "-9";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.INTEGER).values(s, s).start(1, 1).end(1, 3).assertNoErrors();
  }

  @Test
  public void tokenizeNumber_long_oneDigit_l() {
    final String s = "9l";
    final String formattedValue = "9";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.LONG).values(s, formattedValue).start(1, 1).end(1, 3)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_long_oneDigit_L() {
    final String s = "9l";
    final String formattedValue = "9";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.LONG).values(s, formattedValue).start(1, 1).end(1, 3)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_integer_multipleDigits() {
    final String s = "123456";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.INTEGER).values(s, s).start(1, 1).end(1, 7).assertNoErrors();
  }

  @Test
  public void tokenizeNumber_integer_withUnderscores_middle() {
    final String s = "1___2__3____45__6";
    final String formattedValue = "123456";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.INTEGER).values(s, formattedValue).start(1, 1).end(1, 18)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_integer_withUnderscore_end() {
    final String value = "123456";
    final String s = value + "_";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.INTEGER).values(value, value).start(1, 1).end(1, 7)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_integer_min() {
    final String s = "-2_147_483_648";
    final String formattedValue = "-2147483648";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.INTEGER).values(s, formattedValue).start(1, 1).end(1, 15)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_integer_tooSmall() {
    final String s = "-2_147_483_649";
    final String formattedValue = "";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.INTEGER).values(s, formattedValue).start(1, 1).end(1, 15) //
        .assertErrors(1) //
        .assertError(PjbaErrorType.INVALID_INTEGER_VALUE, 1, 1);
  }

  @Test
  public void tokenizeNumber_integer_max() {
    final String s = "2_147_483_647";
    final String formattedValue = "2147483647";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.INTEGER).values(s, formattedValue).start(1, 1).end(1, 14)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_integer_tooBig() {
    final String s = "2_147_483_648";
    final String formattedValue = "";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.INTEGER).values(s, formattedValue).start(1, 1).end(1, 14) //
        .assertErrors(1) //
        .assertError(PjbaErrorType.INVALID_INTEGER_VALUE, 1, 1);
  }

  @Test
  public void tokenizeNumber_long_min() {
    final String s = "-9_223_372_036_854_775_808l";
    final String formattedValue = "-9223372036854775808";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.LONG).values(s, formattedValue).start(1, 1).end(1, 28)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_long_tooSmall() {
    final String s = "-9_223_372_036_854_775_809l";
    final String formattedValue = "";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.LONG).values(s, formattedValue).start(1, 1).end(1, 28) //
        .assertErrors(1) //
        .assertError(PjbaErrorType.INVALID_LONG_VALUE, 1, 1);
  }

  @Test
  public void tokenizeNumber_long_max() {
    final String s = "9_223_372_036_854_775_807l";
    final String formattedValue = "9223372036854775807";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.LONG).values(s, formattedValue).start(1, 1).end(1, 27)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_long_tooBig() {
    final String s = "9_223_372_036_854_775_808l";
    final String formattedValue = "";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.LONG).values(s, formattedValue).start(1, 1).end(1, 27) //
        .assertErrors(1) //
        .assertError(PjbaErrorType.INVALID_LONG_VALUE, 1, 1);
  }

  @Test
  public void tokenizeNumber_float_f() {
    final String s = "1.1f";
    final String formattedValue = "1.1";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.FLOAT).values(s, formattedValue).start(1, 1).end(1, 5)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_float_F() {
    final String s = "1.1F";
    final String formattedValue = "1.1";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.FLOAT).values(s, formattedValue).start(1, 1).end(1, 5)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_double_1() {
    final String s = "1.1";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.DOUBLE).values(s, s).start(1, 1).end(1, 4).assertNoErrors();
  }

  @Test
  public void tokenizeNumber_double_2() {
    final String s = "12345.6789";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.DOUBLE).values(s, s).start(1, 1).end(1, 11).assertNoErrors();
  }

  @Test
  public void tokenizeNumber_double_3() {
    final String s = "0.123";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.DOUBLE).values(s, s).start(1, 1).end(1, 6).assertNoErrors();
  }

  @Test
  public void tokenizeNumber_double_4() {
    final String s = ".123";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.DOUBLE).values(s, s).start(1, 1).end(1, 5).assertNoErrors();
  }

  @Test
  public void tokenizeNumber_double_5() {
    final String s = "12.";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.DOUBLE).values(s, s).start(1, 1).end(1, 4).assertNoErrors();
  }

  @Test
  public void tokenizeNumber_double_6() {
    final String s = "12.0";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.DOUBLE).values(s, s).start(1, 1).end(1, 5).assertNoErrors();
  }

  @Test
  public void tokenizeNumber_double_7() {
    final String s = "5.0e-10";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.DOUBLE).values(s, s).start(1, 1).end(1, 8).assertNoErrors();
  }

  @Test
  public void tokenizeNumber_double_8() {
    final String s = "6.9e+5";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.DOUBLE).values(s, s).start(1, 1).end(1, 7).assertNoErrors();
  }

  @Test
  public void tokenizeNumber_double_8E() {
    final String s = "6.9E+5";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.DOUBLE).values(s, s).start(1, 1).end(1, 7).assertNoErrors();
  }

  @Test
  public void tokenizeNumber_double_9() {
    final String s = "6__9.0__9e+1__2";
    final String formattedValue = "69.09e+12";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.DOUBLE).values(s, formattedValue).start(1, 1).end(1, 16)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_double_underscoreBeforePeriod() {
    final String s = "1_.0";
    final String formattedValue = "1.0";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.DOUBLE).values(s, formattedValue).start(1, 1).end(1, 5)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_double_underscoreAfterPeriod() {
    final String s = "1._0";
    final String formattedValue = "1.0";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.DOUBLE).values(s, formattedValue).start(1, 1).end(1, 5)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_double_underscoreBeforeE() {
    final String s = "1_e2";
    final String formattedValue = "1e2";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.DOUBLE).values(s, formattedValue).start(1, 1).end(1, 5)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_double_underscoreAfterE() {
    final String s = "1e_2";
    final String formattedValue = "1e2";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.DOUBLE).values(s, formattedValue).start(1, 1).end(1, 5)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_double_dotFollowedByUnderscore() {
    final String s = "._";
    final String value = ".";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(CoreTokenType.UNDEFINED).value(value).start(1, 1).end(1, 2) //
        .assertErrors(1);
  }

  @Test
  public void tokenizeNumber_double_twoPeriods() {
    final String s = "1.2.3";
    final String value = "1.2";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.DOUBLE).values(value, value).start(1, 1).end(1, 4)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_double_twoFollowingPeriods() {
    final String s = "1..3";
    final String value = "1.";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.DOUBLE).values(value, value).start(1, 1).end(1, 3)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_double_twoEs() {
    final String s = "1e2e3";
    final String value = "1e2";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.DOUBLE).values(value, value).start(1, 1).end(1, 4)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_double_twoFollowingEs() {
    final String s = "1ee3";
    final String value = "1e";
    final String formattedValue = "";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.DOUBLE).values(value, formattedValue).start(1, 1).end(1, 3) //
        .assertErrors(1) //
        .assertError(PjbaErrorType.INVALID_DOUBLE_VALUE, 1, 1);
  }

  @Test
  public void tokenizeNumber_double_multipleSignsAfterE() {
    final String s = "1.e+-5";
    final String value = "1.e+";
    final String formattedValue = "";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.DOUBLE).values(value, formattedValue).start(1, 1).end(1, 5) //
        .assertErrors(1) //
        .assertError(PjbaErrorType.INVALID_DOUBLE_VALUE, 1, 1);
  }

  @Test
  public void tokenizeNumber_double_signWithoutE() {
    final String s = "1.0-5";
    final String value = "1.0";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.DOUBLE).values(value, value).start(1, 1).end(1, 4)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_double_signWrongPlaceAfterE() {
    final String s = "5.0e5-2";
    final String value = "5.0e5";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.DOUBLE).values(value, value).start(1, 1).end(1, 6)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_double_signFollowedByAWrongChar() {
    final String s = "5.0e-a";
    final String value = "5.0e-";
    final String formattedValue = "";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.DOUBLE).values(value, formattedValue).start(1, 1).end(1, 6) //
        .assertErrors(1) //
        .assertError(PjbaErrorType.INVALID_DOUBLE_VALUE, 1, 1);
  }

  @Test
  public void tokenizeNumber_base() {
    final String s = "16rEf10";
    final String formattedValue = "61200";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.INTEGER).values(s, formattedValue).start(1, 1).end(1, 8)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_base_withUnderscores() {
    final String s = "1__6rEf1__0";
    final String formattedValue = "61200";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.INTEGER).values(s, formattedValue).start(1, 1).end(1, 12)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_base_underscoreBeforeR() {
    final String s = "2_r1111";
    final String formattedValue = "15";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.INTEGER).values(s, formattedValue).start(1, 1).end(1, 8)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_base_underscoreAfterR() {
    final String s = "2r_1111";
    final String formattedValue = "15";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.INTEGER).values(s, formattedValue).start(1, 1).end(1, 8)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_base_withUnderscore_end() {
    final String s = "2r1111_";
    final String value = "2r1111";
    final String formattedValue = "15";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.INTEGER).values(value, formattedValue).start(1, 1).end(1, 7)
        .assertNoErrors();
  }

  @Test
  public void tokenizeNumber_base_invalidCharAfterR() {
    final String s = "2r-1111_";
    final String value = "2r";
    final String formattedValue = "";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.INTEGER).values(value, formattedValue).start(1, 1).end(1, 3) //
        .assertErrors(1) //
        .assertError(PjbaErrorType.INVALID_INTEGER_WITH_RADIX, 1, 1);
  }

  @Test
  public void tokenizeNumber_base_invalidRadixValue() {
    final String s = "64r1234";
    final String formattedValue = "";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.INTEGER).values(s, formattedValue).start(1, 1).end(1, 8) //
        .assertErrors(1) //
        .assertError(PjbaErrorType.INVALID_RADIX_VALUE, 1, 1);
  }

  @Test
  public void tokenizeNumber_base_invalidIntegerWithRadix() {
    final String s = "16r8fff_ffff_ffff_ffff_ffff";
    final String formattedValue = "";

    PjbaTokenizerTester.fromString(s) //
        .tokenizeNumber().assertTokenType(PjbaTokenType.INTEGER).values(s, formattedValue).start(1, 1).end(1, 28) //
        .assertErrors(1) //
        .assertError(PjbaErrorType.INVALID_INTEGER_WITH_RADIX, 1, 1);
  }

  @Test
  public void matchIdentifier_classStart() {
    this.testMatchIdentifier(PjbaTokenType.DIR_CLASS_START, ".class");
  }

  @Test
  public void matchIdentifier_classEnd() {
    this.testMatchIdentifier(PjbaTokenType.DIR_CLASS_END, ".classend");
  }

  @Test
  public void matchIdentifier_methodStart() {
    this.testMatchIdentifier(PjbaTokenType.DIR_METHOD_START, ".method");
  }

  @Test
  public void matchIdentifier_methodEnd() {
    this.testMatchIdentifier(PjbaTokenType.DIR_METHOD_END, ".methodend");
  }

  @Test
  public void matchIdentifier_final() {
    this.testMatchIdentifier(PjbaTokenType.FINAL, "final");
  }

  @Test
  public void matchIdentifier_abstract() {
    this.testMatchIdentifier(PjbaTokenType.ABSTRACT, "abstract");
  }

  @Test
  public void matchIdentifier_interface() {
    this.testMatchIdentifier(PjbaTokenType.INTERFACE, "interface");
  }

  @Test
  public void matchIdentifier_super() {
    this.testMatchIdentifier(PjbaTokenType.SUPER, "super");
  }

  @Test
  public void matchIdentifier_public() {
    this.testMatchIdentifier(PjbaTokenType.PUBLIC, "public");
  }

  @Test
  public void matchIdentifier_protected() {
    this.testMatchIdentifier(PjbaTokenType.PROTECTED, "protected");
  }

  @Test
  public void matchIdentifier_private() {
    this.testMatchIdentifier(PjbaTokenType.PRIVATE, "private");
  }

  @Test
  public void matchIdentifier_static() {
    this.testMatchIdentifier(PjbaTokenType.STATIC, "static");
  }

  @Test
  public void matchIdentifier_strictfp() {
    this.testMatchIdentifier(PjbaTokenType.STRICTFP, "strictfp");
  }

  @Test
  public void matchIdentifier_synchronized() {
    this.testMatchIdentifier(PjbaTokenType.SYNCHRONIZED, "synchronized");
  }

  @Test
  public void matchIdentifier_native() {
    this.testMatchIdentifier(PjbaTokenType.NATIVE, "native");
  }

  @Test
  public void getNextToken_space() {
    final String s = " ";

    PjbaTokenizerTester.fromString(s) //
        .getNextToken().assertTokenType(CoreTokenType.WHITESPACE).value(s).start(1, 1).end(1, 2).assertNoErrors() //
        .getNextToken().eos().assertNoErrors();
  }

  @Test
  public void getNextToken_tab() {
    final String s = "\t";

    PjbaTokenizerTester.fromString(s) //
        .getNextToken().assertTokenType(CoreTokenType.WHITESPACE).value(s).start(1, 1).end(1, 3).assertNoErrors() //
        .getNextToken().eos().assertNoErrors();
  }

  @Test
  public void getNextToken_spacesAndTabs() {
    final String s = " \t  \t  \t  ";

    PjbaTokenizerTester.fromString(s) //
        .getNextToken().assertTokenType(CoreTokenType.WHITESPACE).value(s).start(1, 1).end(1, 14).assertNoErrors() //
        .getNextToken().eos().assertNoErrors();
  }

  @Test
  public void getNextToken_cr() {
    final String s = "\r";

    PjbaTokenizerTester.fromString(s) //
        .getNextToken().assertTokenType(CoreTokenType.NEW_LINE).value(s).start(1, 1).end(2, 1).assertNoErrors() //
        .getNextToken().eos().assertNoErrors();
  }

  @Test
  public void getNextToken_lf() {
    final String s = "\n";

    PjbaTokenizerTester.fromString(s) //
        .getNextToken().assertTokenType(CoreTokenType.NEW_LINE).value(s).start(1, 1).end(2, 1).assertNoErrors() //
        .getNextToken().eos().assertNoErrors();
  }

  @Test
  public void getNextToken_crlf() {
    final String s = "\r\n";

    PjbaTokenizerTester.fromString(s) //
        .getNextToken().assertTokenType(CoreTokenType.NEW_LINE).value(s).start(1, 1).end(2, 1).assertNoErrors() //
        .getNextToken().eos().assertNoErrors();
  }

  @Test
  public void getNextToken_whitespacesAndNewLines() {
    final String s = " \t \r\n \n";

    PjbaTokenizerTester.fromString(s) //
        .getNextToken().assertTokenType(CoreTokenType.NEW_LINE).value(s).start(1, 1).end(3, 1).assertNoErrors() //
        .getNextToken().eos().assertNoErrors();
  }

  @Test
  public void getNextToken_whitespacesAndNewLinesWithSpaceIndentation() {
    final String s = " \t \r\n \n  ";

    PjbaTokenizerTester.fromString(s) //
        .getNextToken().assertTokenType(CoreTokenType.NEW_LINE).value(s).start(1, 1).end(3, 3).assertNoErrors() //
        .getNextToken().eos().assertNoErrors();
  }

  @Test
  public void getNextToken_whitespacesAndNewLinesWithTabIndentation() {
    final String s = " \t \r\n \n\t";

    PjbaTokenizerTester.fromString(s) //
        .getNextToken().assertTokenType(CoreTokenType.NEW_LINE).value(s).start(1, 1).end(3, 3).assertNoErrors() //
        .getNextToken().eos().assertNoErrors();
  }

  @Test
  public void getNextToken_allErrors() {
    final String s = " \t \r\n \n\t";

    PjbaTokenizerTester.fromString(s) //
        .getNextToken().assertTokenType(CoreTokenType.NEW_LINE).value(s).start(1, 1).end(3, 3).assertNoErrors() //
        .getNextToken().eos().assertNoErrors();
  }

  @Test
  public void getNextToken_ok1() {
    PjbaTokenizerTester.fromFile("ok01.pjb") //
        .getNextToken().assertTokenType(PjbaTokenType.DIR_CLASS_START).value(".class").start(1, 1).end(1, 7)
        .assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.WHITESPACE).value(" ").start(1, 7).end(1, 8).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.PUBLIC).value("public").start(1, 8).end(1, 14).assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.WHITESPACE).value(" ").start(1, 14).end(1, 15).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER).value("Test").start(1, 15).end(1, 19).assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.NEW_LINE).value("\n\n").start(1, 19).end(3, 1).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.DIR_CLASS_END).value(".classend").start(3, 1).end(3, 10)
        .assertNoErrors() //
        .getNextToken().eos().assertNoErrors();
  }

  @Test
  public void getNextToken_ok2() {
    PjbaTokenizerTester.fromFile("ok02.pjb") //
        .getNextToken().assertTokenType(PjbaTokenType.DIR_CLASS_START).value(".class").start(1, 1).end(1, 7)
        .assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.WHITESPACE).value(" ").start(1, 7).end(1, 8).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.PUBLIC).value("public").start(1, 8).end(1, 14).assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.WHITESPACE).value(" ").start(1, 14).end(1, 15).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER).value("org").start(1, 15).end(1, 18).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER_SEPARATOR).value("/").start(1, 18).end(1, 19)
        .assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER).value("isk").start(1, 19).end(1, 22).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER_SEPARATOR).value("/").start(1, 22).end(1, 23)
        .assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER).value("pjb").start(1, 23).end(1, 26).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER_SEPARATOR).value("/").start(1, 26).end(1, 27)
        .assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER).value("Test").start(1, 27).end(1, 31).assertNoErrors() //
        .assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.NEW_LINE).value("\n    ").start(1, 31).end(2, 5).assertNoErrors() //
        // Method Start
        .getNextToken().assertTokenType(PjbaTokenType.DIR_METHOD_START).value(".method").start(2, 5).end(2, 12)
        .assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.WHITESPACE).value(" ").start(2, 12).end(2, 13).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.PUBLIC).value("public").start(2, 13).end(2, 19).assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.WHITESPACE).value(" ").start(2, 19).end(2, 20).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.STATIC).value("static").start(2, 20).end(2, 26).assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.WHITESPACE).value(" ").start(2, 26).end(2, 27).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER).value("methodName").start(2, 27).end(2, 37)
        .assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.PARAMETER_START).value("(").start(2, 37).end(2, 38)
        .assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.PARAMETER_END).value(")").start(2, 38).end(2, 39).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER).value("V").start(2, 39).end(2, 40).assertNoErrors() //
        // Method End
        .getNextToken().assertTokenType(CoreTokenType.NEW_LINE).value("\n    ").start(2, 40).end(3, 5).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.DIR_METHOD_END).value(".methodend").start(3, 5).end(3, 15)
        .assertNoErrors() //
        // Class End
        .getNextToken().assertTokenType(CoreTokenType.NEW_LINE).value("\n").start(3, 15).end(4, 1).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.DIR_CLASS_END).value(".classend").start(4, 1).end(4, 10)
        .assertNoErrors() //
        .getNextToken().eos().assertNoErrors();
  }

  @Test
  public void getNextToken_ok3() {
    PjbaTokenizerTester.fromFile("ok03.pjb") //
        .getNextToken().assertTokenType(PjbaTokenType.OL_COMMENT).value("@ Comment ").start(1, 1).end(1, 11)
        .assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.NEW_LINE).value("\n").start(1, 11).end(2, 1).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.DIR_CLASS_START).value(".class").start(2, 1).end(2, 7)
        .assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.WHITESPACE).value(" ").start(2, 7).end(2, 8).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.PUBLIC).value("public").start(2, 8).end(2, 14).assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.WHITESPACE).value(" ").start(2, 14).end(2, 15).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.FINAL).value("final").start(2, 15).end(2, 20).assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.WHITESPACE).value(" ").start(2, 20).end(2, 21).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER).value("Test").start(2, 21).end(2, 25).assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.NEW_LINE).value("\n  ").start(2, 25).end(3, 3).assertNoErrors() //
        // Method Start
        .getNextToken().assertTokenType(PjbaTokenType.DIR_METHOD_START).value(".method").start(3, 3).end(3, 10)
        .assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.WHITESPACE).value(" ").start(3, 10).end(3, 11).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.PUBLIC).value("public").start(3, 11).end(3, 17).assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.WHITESPACE).value(" ").start(3, 17).end(3, 18).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER).value("methodName").start(3, 18).end(3, 28)
        .assertNoErrors() //
        // Parameters Descriptor
        .getNextToken().assertTokenType(PjbaTokenType.PARAMETER_START).value("(").start(3, 28).end(3, 29)
        .assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER).value("I").start(3, 29).end(3, 30).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.BRACKET_START).value("[").start(3, 30).end(3, 31).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.BRACKET_START).value("[").start(3, 31).end(3, 32).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER).value("Lorg").start(3, 32).end(3, 36).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER_SEPARATOR).value("/").start(3, 36).end(3, 37)
        .assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER).value("isk").start(3, 37).end(3, 40).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER_SEPARATOR).value("/").start(3, 40).end(3, 41)
        .assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER).value("pjb").start(3, 41).end(3, 44).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER_SEPARATOR).value("/").start(3, 44).end(3, 45)
        .assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER).value("Test").start(3, 45).end(3, 49).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.SEMICOLON).value(";").start(3, 49).end(3, 50).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER).value("Z").start(3, 50).end(3, 51).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.PARAMETER_END).value(")").start(3, 51).end(3, 52).assertNoErrors() //
        // Return Descriptor
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER).value("V").start(3, 52).end(3, 53).assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.NEW_LINE).value("\n    ").start(3, 53).end(4, 5).assertNoErrors() //
        // Instructions
        //  ldc /* comment */ 1
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER).value("ldc").start(4, 5).end(4, 8).assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.WHITESPACE).value(" ").start(4, 8).end(4, 9).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.OL_COMMENT).value("/* comment */").start(4, 9).end(4, 22)
        .assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.WHITESPACE).value(" ").start(4, 22).end(4, 23).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.INTEGER).values("1", "1").start(4, 23).end(4, 24).assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.NEW_LINE).value("\n    ").start(4, 24).end(5, 5).assertNoErrors() //
        // ldc .0f
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER).value("ldc").start(5, 5).end(5, 8).assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.WHITESPACE).value(" ").start(5, 8).end(5, 9).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.FLOAT).values(".0f", ".0").start(5, 9).end(5, 12).assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.NEW_LINE).value("\n    ").start(5, 12).end(6, 5).assertNoErrors() //
        // ldc "Hello World"
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER).value("ldc").start(6, 5).end(6, 8).assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.WHITESPACE).value(" ").start(6, 8).end(6, 9).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.STRING).values("\"Hello World\"", "Hello World").start(6, 9)
        .end(6, 22).assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.NEW_LINE).value("\n    ").start(6, 22).end(7, 5).assertNoErrors() //
        // areturn
        .getNextToken().assertTokenType(PjbaTokenType.IDENTIFIER).value("areturn").start(7, 5).end(7, 12)
        .assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.NEW_LINE).value("\n    ").start(7, 12).end(8, 5).assertNoErrors() //
        // // comment
        .getNextToken().assertTokenType(PjbaTokenType.OL_COMMENT).value("# comment").start(8, 5).end(8, 14)
        .assertNoErrors() //
        .getNextToken().assertTokenType(CoreTokenType.NEW_LINE).value("\n  ").start(8, 14).end(9, 3).assertNoErrors() //
        // Method End
        .getNextToken().assertTokenType(PjbaTokenType.DIR_METHOD_END).value(".methodend").start(9, 3).end(9, 13)
        // Class end
        .getNextToken().assertTokenType(CoreTokenType.NEW_LINE).value("\n").start(9, 13).end(10, 1).assertNoErrors() //
        .getNextToken().assertTokenType(PjbaTokenType.DIR_CLASS_END).value(".classend").start(10, 1).end(10, 10)
        .assertNoErrors() //
        .getNextToken().eos().assertNoErrors();
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Helpers
  // -------------------------------------------------------------------------------------------------------------------

  //  private String toString(final String file) {
  //    final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
  //
  //    final StringBuilder sb = new StringBuilder();
  //
  //    String line;
  //    try (final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
  //      while ((line = br.readLine()) != null) {
  //        sb.append(line);
  //      }
  //    } catch (final IOException e) {
  //      e.printStackTrace();
  //    }
  //
  //    return sb.toString();
  //  }

  private void testMatchIdentifier(final TokenType expectedTokenType, final String string) {
    this.testMatchIdentifier(expectedTokenType, string, string.length());
  }

  private void testMatchIdentifier(final TokenType expectedTokenType, final String string, final int readsBefore) {
    PjbaTokenizerTester.fromString(string).matchIdentifier(string, readsBefore).assertTokenType(expectedTokenType) //
        .value(string) //
        .assertNoErrors();
  }
}
