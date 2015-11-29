package org.isk.pjba.tokenizer.core;

import org.isk.pjba.tokenizer.core.error.CoreErrorType;
import org.isk.pjba.tokenizer.core.token.CoreTokenType;
import org.isk.pjba.tokenizer.core.token.EndOfStreamToken;
import org.isk.pjba.tokenizer.core.token.Token;
import org.isk.pjba.tokenizer.core.token.TokenType;
import org.isk.pjba.tokenizer.core.token.ValueToken;
import org.isk.pjba.unicode.CodePoints.Reader;

/**
 * A <code>Tokenizer</code> offers the ability to group successive code points into {@link Token}s.
 */
public abstract class Tokenizer {

  protected final Reader reader;
  protected final SourceMapper sourceMapper = new SourceMapper();

  /**
   * Instantiates a new <code>Tokenizer</code> from an {@link Reader}.
   *
   * @param reader
   *          is the reader containing the code points to read.
   */
  public Tokenizer(final Reader reader) {
    this.reader = reader;
  }

  /**
   * Is a code point a TAB (0x09) or a SPACE (0x20) ?
   *
   * @param character
   *          is the code point.
   * @return <code>true</code> if the code point is a white space, <code>false</code> otherwise.
   */
  public static boolean isWhitespace(final int character) {
    return character == SourceMapper.TAB || character == SourceMapper.SPACE;
  }

  /**
   * Is a code point a LF (0x0A) or a CR (0x0D) ?
   *
   * @param character
   *          is the code point.
   * @return <code>true</code> if the code point is a new line, <code>false</code> otherwise.
   */
  public static boolean isNewLine(final int character) {
    return character == SourceMapper.LF || character == SourceMapper.CR;
  }

  /**
   * Is a code point a TAB (0x09), a LF (0x0A), a CR (0x0D) or a SPACE (0x20) ?
   *
   * @param character
   *          is the code point.
   * @return <code>true</code> if the code point is an invisible character, <code>false</code> otherwise.
   */
  public static boolean isInvisibleChar(final int character) {
    return isWhitespace(character) || isNewLine(character);
  }

  /**
   * Is a code point a digit (0 to 9) ?
   *
   * @param character
   *          is the code point.
   * @return <code>true</code> if the code point is a digit, <code>false</code> otherwise.
   */
  public static boolean isDigit(final int character) {
    return character >= '0' && character <= '9';
  }

  /**
   * Is a code point a digit (0 to 9) or an underscore ('_') ?
   *
   * @param character
   *          is the code point.
   * @return <code>true</code> if the code point is a digit or an underscore, <code>false</code> otherwise.
   */
  public static boolean isValidNumberChar(final int character) {
    return isDigit(character) || character == '_';
  }

  /**
   * Is a code point an ASCII letter (a to z and A to Z) ?
   *
   * @param character
   *          is the code point.
   * @return <code>true</code> if the code point is an ASCII letter, <code>false</code> otherwise.
   */
  public static boolean isAsciiLetter(final int character) {
    return character >= 'a' && character <= 'z' || character >= 'A' && character <= 'Z';
  }

  /**
   * Is a code point a digit (0 to 9) or an ASCII letter (a to z and A to Z) ?
   *
   * @param character
   *          is the code point.
   * @return <code>true</code> if the code point is a digit or an ASCII letter, <code>false</code> otherwise.
   */
  public static boolean isDigitOrAsciiLetter(final int character) {
    return isDigit(character) || isAsciiLetter(character);
  }

  /**
   * Is a code point a valid value in a radix number ?
   *
   * @param character
   *          is the code point.
   * @return <code>true</code> if the code point is a digit or an ASCII letter or an underscore, <code>false</code>
   *         otherwise.
   */
  public static boolean isValidRadixValueChar(final int character) {
    return isDigitOrAsciiLetter(character) || character == '_';
  }

  /**
   * Is a code point a valid value in a hexadecimal number ?
   *
   * @param character
   *          is the code point.
   * @return <code>true</code> if the code point is a digit or a letter (a to f and A to F), <code>false</code>
   *         otherwise.
   */
  public static boolean isHexChar(final int character) {
    return character >= '0' && character <= '9' || character >= 'a' && character <= 'f'
        || character >= 'A' && character <= 'F';
  }

  /**
   * <p>
   * Is a code point a valid character as the first character of a Java identifier ?
   * <p>
   * Note: We use this method instead of {@link Character#isJavaIdentifierStart(int)} to forbid the use of non ASCII
   * letters, which don't have their place in a source code outside String constants and comments.
   *
   * @param character
   *          is the code point.
   * @return <code>true</code> if the code point is an ASCII letter, an underscore ('_') or a dollar ('$'),
   *         <code>false</code> otherwise.
   */
  public static boolean isValidJavaIdentifierStartChar(final int character) {
    return character == '$' || character == '_' || isAsciiLetter(character);
  }

  /**
   * <p>
   * Is a code point a valid character as a character of a Java identifier ?
   * <p>
   * Note: We use this method instead of {@link Character#isJavaIdentifierPart(int)} to forbid the use of non ASCII
   * letters, which don't have their place in a source code outside String constants and comments.
   *
   * @param character
   *          is the code point.
   * @return <code>true</code> if the code point is a digit, an ASCII letter, an underscore ('_') or a dollar ('$'),
   *         <code>false</code> otherwise.
   */

  public static boolean isValidJavaIdentifierChar(final int character) {
    return isValidJavaIdentifierStartChar(character) || isDigit(character);
  }

  /**
   * <p>
   * Returns an <code>Integer</code> object holding the value extracted from the specified <code>String</code> when
   * parsed with the radix given by the second argument. The first argument is interpreted as representing a signed
   * integer in the radix specified by the second argument.
   * <p>
   * The result is an <code>Integer</code> object that represents the integer value specified by the string.
   *
   * @param s
   *          is the string to be parsed.
   * @param radix
   *          is the radix to be used in interpreting <code>s</code>.
   * @return an <code>Integer</code> object holding the value represented by the string argument in the specified radix
   *         or <code>null</code> if the <code>String</code> does not contain a parsable <code>int</code> .
   */
  public static Integer toInteger(final String s, final int radix) {
    if (s == null || s.isEmpty()) {
      return null;
    }

    try {
      return Integer.valueOf(s, radix);
    } catch (final NumberFormatException e) {
      return null;
    }
  }

  /**
   * <p>
   * Returns an <code>Long</code> object holding the value extracted from the specified <code>String</code> when parsed
   * with the radix given by the second argument. The first argument is interpreted as representing a signed integer in
   * the radix specified by the second argument.
   * <p>
   * The result is an <code>Long</code> object that represents the long value specified by the string.
   *
   * @param s
   *          is the string to be parsed.
   * @param radix
   *          is the radix to be used in interpreting <code>s</code>.
   * @return an <code>Long</code> object holding the value represented by the string argument in the specified radix or
   *         <code>null</code> if the <code>String</code> does not contain a parsable <code>long</code> .
   */
  public static Long toLong(final String s, final int radix) {
    if (s == null || s.isEmpty()) {
      return null;
    }

    try {
      return Long.valueOf(s, radix);
    } catch (final NumberFormatException e) {
      return null;
    }
  }

  /**
   * Returns a <code>Float</code> object holding the <code>float</code> value represented by the argument string
   * <code>s</code>.
   *
   * @param s
   *          is the string to be parsed.
   * @return an <code>Float</code> object holding the value represented by the string argument or <code>null</code> if
   *         the <code>String</code> does not contain a parsable <code>float</code> .
   */
  public static Float toFloat(final String s) {
    if (s == null || s.isEmpty()) {
      return null;
    }

    try {
      return Float.valueOf(s);
    } catch (final NumberFormatException e) {
      return null;
    }
  }

  /**
   * Returns a <code>Double</code> object holding the <code>double</code> value represented by the argument string
   * <code>s</code>.
   *
   * @param s
   *          is the string to be parsed.
   * @return an <code>Double</code> object holding the value represented by the string argument or <code>null</code> if
   *         the <code>String</code> does not contain a parsable <code>double</code> .
   */
  public static Double toDouble(final String s) {
    if (s == null || s.isEmpty()) {
      return null;
    }

    try {
      return Double.valueOf(s);
    } catch (final NumberFormatException e) {
      return null;
    }
  }

  /**
   * Returns the next token.
   *
   * @return the next token or {@link EndOfStreamToken#EOS} if the {@link Reader} doesn't have any code points to be
   *         read.
   */
  public Token getNextToken() {
    if (this.reader.hasNext()) {
      return this.tokenizeNextElement();
    } else {
      return EndOfStreamToken.EOS;
    }
  }

  /**
   * Creates a {@link Token} of successive invisible characters: TAB (0x09), LF (0x0A), CR (0x0D) and SPACE (0x20)
   *
   * @param startLine
   *          is the line of the first code point of the token.
   * @param startColumn
   *          is the column of the first code point of the token.
   * @param character
   *          is the first code point of the token.
   * @return a {@link ValueToken} of type {@link CoreTokenType#NEW_LINE} or {@link CoreTokenType#WHITESPACE}.
   */
  protected Token tokenizeInsivibleChars(final int startLine, final int startColumn, final int character) {
    boolean containsNewLine = this.sourceMapper.addCodePoint(character);

    int current = -1;
    while ((current = this.reader.read()) != -1 && isInvisibleChar(current)) {
      final boolean newLine = this.sourceMapper.addCodePoint(current);

      if (newLine) {
        containsNewLine = true;
      }
    }

    this.reader.unread(current);

    if (containsNewLine) {
      return this.valueToken(CoreTokenType.NEW_LINE, startLine, startColumn);
    } else {
      return this.valueToken(CoreTokenType.WHITESPACE, startLine, startColumn);
    }
  }

  /**
   * Creates a <code>CoreErrorType.UNDEFINED_TOKEN</code> from the current raw value of the {@link SourceMapper}.
   *
   * @param startLine
   *          is the line of the first character of the token.
   * @param startColumn
   *          is the column of the first character of the token.
   * @return a {@link ValueToken} of type {@link CoreErrorType#UNDEFINED_TOKEN}
   */
  protected Token undefinedToken(final int startLine, final int startColumn) {
    this.sourceMapper.addError(CoreErrorType.UNDEFINED_TOKEN);
    return this.valueToken(CoreTokenType.UNDEFINED, startLine, startColumn);
  }

  /**
   * <p>
   * Creates a constant token where the value is defined in a {@link TokenType}.
   * <p>
   * The current raw and formatted values of the {@link SourceMapper} are discarded.
   *
   * @param tokenType
   *          is the type of the token to be created.
   * @param startLine
   *          is the line of the first code point of the token.
   * @param startColumn
   *          is the column of the first code point of the token.
   * @return a new {@link ValueToken}.
   */
  protected Token constantToken(final TokenType tokenType, final int startLine, final int startColumn) {
    this.sourceMapper.clearValue();
    this.sourceMapper.clearFormattedValue();

    return new ValueToken(tokenType, Token.EMPTY_INT_ARRAY, //
        startLine, startLine, startColumn, startColumn + tokenType.value().length, //
        this.sourceMapper.errorsAndReset());
  }

  /**
   * <p>
   * Creates a new {@link ValueToken} with the current raw value of the {@link SourceMapper}.
   * <p>
   * Note: The current raw and formatted values of the {@link SourceMapper} are discarded after being used.
   *
   * @param tokenType
   *          is the type of the token to be created.
   * @param startLine
   *          is the line of the first code point of the token.
   * @param startColumn
   *          is the column of the first code point of the token.
   * @return a new {@link ValueToken}.
   */
  protected Token valueToken(final TokenType tokenType, final int startLine, final int startColumn) {
    return new ValueToken(tokenType, this.sourceMapper.valueAndReset(), //
        startLine, this.sourceMapper.currentLine, startColumn, this.sourceMapper.currentColumn, //
        this.sourceMapper.errorsAndReset());
  }

  /**
   * <p>
   * Creates a new {@link ValueToken} with the current raw and formatted values of the {@link SourceMapper}.
   * <p>
   * Note: The current raw and formatted values of the {@link SourceMapper} are discarded after being used.
   *
   * @param tokenType
   *          is the type of the token to be created.
   * @param startLine
   *          is the line of the first code point of the token.
   * @param startColumn
   *          is the column of the first code point of the token.
   * @return a new {@link ValueToken}.
   */
  protected Token formattedValueToken(final TokenType tokenType, final int startLine, final int startColumn) {
    return new ValueToken(tokenType, this.sourceMapper.valueAndReset(), this.sourceMapper.formattedValueAndReset(), //
        startLine, this.sourceMapper.currentLine, startColumn, this.sourceMapper.currentColumn, //
        this.sourceMapper.errorsAndReset());
  }

  /**
   * Creates a {@link Token} of successive code points to make easier the parsing process.
   *
   * @return a new {@link Token}.
   */
  protected abstract Token tokenizeNextElement();
}
