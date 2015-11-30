package org.isk.pjba.tokenizer;

import java.util.Arrays;

import org.isk.pjba.tokenizer.core.Tokenizer;
import org.isk.pjba.tokenizer.core.token.Token;
import org.isk.pjba.tokenizer.core.token.TokenType;
import org.isk.pjba.unicode.CodePoints;
import org.isk.pjba.unicode.CodePoints.Reader;

/**
 * A <code>PjbaTokenizer</code> offers the ability to group successive code points into {@link Token}s of type
 * {@link PjbaTokenType}.
 */
public class PjbaTokenizer extends Tokenizer {

  /* private testing */ static final TokenType[] SINGLE_TOKENS = new TokenType[128];
  /* private testing */ static final TokenType[] COREIDENTIFIER_TOKENS;

  static {
    SINGLE_TOKENS[PjbaTokenType.PARAMETER_START.value()[0]] = PjbaTokenType.PARAMETER_START;
    SINGLE_TOKENS[PjbaTokenType.PARAMETER_END.value()[0]] = PjbaTokenType.PARAMETER_END;
    SINGLE_TOKENS[PjbaTokenType.IDENTIFIER_SEPARATOR.value()[0]] = PjbaTokenType.IDENTIFIER_SEPARATOR;
    SINGLE_TOKENS[PjbaTokenType.STRING_DELIMITER.value()[0]] = PjbaTokenType.STRING_DELIMITER;
    SINGLE_TOKENS[PjbaTokenType.BRACKET_START.value()[0]] = PjbaTokenType.BRACKET_START;
    SINGLE_TOKENS[PjbaTokenType.SEMICOLON.value()[0]] = PjbaTokenType.SEMICOLON;

    COREIDENTIFIER_TOKENS = new TokenType[] { //
        PjbaTokenType.DIR_CLASS_START, //
        PjbaTokenType.DIR_CLASS_END, //
        PjbaTokenType.DIR_METHOD_START, //
        PjbaTokenType.DIR_METHOD_END, //
        PjbaTokenType.FINAL, //
        PjbaTokenType.ABSTRACT, //
        PjbaTokenType.INTERFACE, //
        PjbaTokenType.SUPER, //
        PjbaTokenType.PUBLIC, //
        PjbaTokenType.PROTECTED, //
        PjbaTokenType.PRIVATE, //
        PjbaTokenType.STATIC, //
        PjbaTokenType.STRICTFP, //
        PjbaTokenType.SYNCHRONIZED, //
        PjbaTokenType.NATIVE //
    };
  }

  /**
   * Instantiates a new <code>PjbaTokenizer</code> from an {@link Reader}.
   *
   * @param reader
   *          is the reader containing the code points to read.
   */
  public PjbaTokenizer(final Reader reader) {
    super(reader);
  }

  /**
   * Creates a {@link Token} of successive code points to make easier the parsing process of a PJBA file.
   *
   * @return a new {@link Token}.
   */
  @Override
  public Token tokenizeNextElement() {
    final int startColumn = this.sourceMapper.currentColumn();
    final int startLine = this.sourceMapper.currentLine();
    final int character = this.reader.read();

    if (isInvisibleChar(character)) { // WHITESPACE or NEW_LINE
      return this.tokenizeInsivibleChars(startLine, startColumn, character);
    } else if (character == '#' || character == '@') { // One line comment
      return this.tokenizeOneLineComment(startLine, startColumn, character);
    } else if (character == '/' && this.reader.peek() == '*') { // Multi-lines Comment
      return this.tokenizeMultiLinesComment(startLine, startColumn);
    } else if (character == '"' || character == '\'') {
      return this.tokenizeString(startLine, startColumn, character);
    } else if (Tokenizer.isValidJavaIdentifierStartChar(character)
        || character == '.' && isValidJavaIdentifierStartChar(this.reader.peek())) { // Identifier (core and user) or directive
      return this.tokenizeJavaIdentifier(startLine, startColumn, character);
    } else if (isDigit(character) || character == '.' || character == '+' || character == '-') {
      return this.tokenizeNumber(startLine, startColumn, character);
    } else {
      final TokenType tokenType = SINGLE_TOKENS[character];
      if (tokenType != null) {
        this.sourceMapper.incCurrentColumn();
        return this.constantToken(tokenType, startLine, startColumn);
      } else {
        this.sourceMapper.addCodePoint(character);
        return this.undefinedToken(startLine, startColumn);
      }
    }
  }

  /**
   * <p>
   * Returns a token of type {@link PjbaTokenType#OL_COMMENT}.
   * <p>
   * Examples:
   *
   * <pre>
   * &#64; Comment
   * # Comment
   * </pre>
   *
   * @param startLine
   *          is the line of the first code point of the token.
   * @param startColumn
   *          is the column of the first code point of the token.
   * @param character
   *          is the first code point of the token.
   * @return a one line comment.
   */
  /* private testing */ Token tokenizeOneLineComment(final int startLine, final int startColumn, final int character) {
    this.sourceMapper.addCodePoint(character);

    int current = -1;
    while ((current = this.reader.read()) != -1 && !isNewLine(current)) {
      this.sourceMapper.addCodePoint(current);
    }

    this.reader.unread(current);

    return this.valueToken(PjbaTokenType.OL_COMMENT, startLine, startColumn);
  }

  /**
   * <p>
   * Returns a token of type {@link PjbaTokenType#OL_COMMENT} or {@link PjbaTokenType#ML_COMMENT}.
   * <p>
   * If the comment starts and ends at the same line, the type of the token will be {@link PjbaTokenType#OL_COMMENT}.
   * <p>
   * Examples:
   *
   * <pre>
   * /*
   *  Comment
   *  spreading over
   *  multiple lines
   * &#42;/
   * </pre>
   *
   * @param startLine
   *          is the line of the first code point of the token.
   * @param startColumn
   *          is the column of the first code point of the token.
   * @return a one line comment.
   */
  /* private testing */ Token tokenizeMultiLinesComment(final int startLine, final int startColumn) {
    this.sourceMapper.addCodePoint('/');
    this.sourceMapper.addCodePoint('*');

    this.reader.read(); // *

    int current = -1;
    while ((current = this.reader.read()) != -1) {
      if (current == '*' && this.reader.peek() == '/') {
        this.reader.read();
        this.sourceMapper.addCodePoint('*');
        this.sourceMapper.addCodePoint('/');
        break;
      } else {
        this.sourceMapper.addCodePoint(current);
      }
    }

    if (startLine != this.sourceMapper.currentLine()) {
      return this.valueToken(PjbaTokenType.ML_COMMENT, startLine, startColumn);
    } else {
      return this.valueToken(PjbaTokenType.OL_COMMENT, startLine, startColumn);
    }
  }

  /**
   * <p>
   * Returns a token of type {@link PjbaTokenType#STRING}
   * <p>
   * An error of type {@link PjbaErrorType#NOT_ENDING_STRING} is added to the token if the end delimiter (' or ") is not
   * found.
   * <p>
   * Examples:
   *
   * <pre>
   * "Hello world"
   * 'Hello World'
   * 'It\'s a String'
   * "A string with escaped \"quotes\""
   * "A string with \t \n \r \b \f"
   * "A string \u0077ith unicode"
   * </pre>
   *
   * @param startLine
   *          is the line of the first code point of the token.
   * @param startColumn
   *          is the column of the first code point of the token.
   * @param character
   *          is the first code point of the token.
   * @return a one line comment.
   */
  /* private testing */ Token tokenizeString(final int startLine, final int startColumn, final int character) {
    final int delimiter = character;
    this.sourceMapper.addCodePoint(character);

    int current = -1;
    int previous = -1;
    while ((current = this.reader.read()) != -1) {
      if (previous != '\\' && current == delimiter) {
        this.sourceMapper.addCodePoint(current);
        break;
      } else if (previous == '\\' && current != delimiter) {
        this.tokenizeEscapedCharacter(current);
        previous = -1; // We don't know and we don't care. It has already been parsed!
      } else {
        this.sourceMapper.addCodePoint(current);

        if (current != '\\') {
          this.sourceMapper.addFormattedCodePoint(current);
        }

        previous = current;
      }
    }

    // We reached the end of the stream without meeting a single quote
    if (current != delimiter) {
      this.sourceMapper.addError(PjbaErrorType.NOT_ENDING_STRING);
    }

    if (this.sourceMapper.hasErrors()) {
      this.sourceMapper.clearFormattedValue();
    }

    return this.formattedValueToken(PjbaTokenType.STRING, startLine, startColumn);
  }

  /**
   * <p>
   * Tokenize an escaped character.
   * <p>
   * An error of type {@link PjbaErrorType#INVALID_ESCAPE_SEQUENCE} is added to the token if .
   * <p>
   * An error of type {@link PjbaErrorType#INVALID_CODEPOINT} is added to the token if value of an escaped Unicode
   * character (in hexadceiment) is not a valid code point as defined in {@link CodePoints#isCodePoint(int)}. Examples:
   *
   * <pre>
   * \b
   * \f
   * \l
   * \n
   * \r
   * \u0077
   * </pre>
   *
   * @param current
   *          is the last code point read.
   */
  /* private testing */ void tokenizeEscapedCharacter(int current) {
    final int line = this.sourceMapper.currentLine();
    final int column = this.sourceMapper.currentColumn() - 1; // We already added the backslash

    this.sourceMapper.addCodePoint(current);

    switch (current) {
    case '\\':
      this.sourceMapper.addFormattedCodePoint('\\');
      break;
    case 'b':
      this.sourceMapper.addFormattedCodePoint('\b');
      break;
    case 'f':
      this.sourceMapper.addFormattedCodePoint('\f');
      break;
    case 'n':
      this.sourceMapper.addFormattedCodePoint('\n');
      break;
    case 'r':
      this.sourceMapper.addFormattedCodePoint('\r');
      break;
    case 's':
      this.sourceMapper.addFormattedCodePoint(' ');
      break;
    case 't':
      this.sourceMapper.addFormattedCodePoint('\t');
      break;
    case 'u':
      final CodePoints cp = new CodePoints();
      while ((current = this.reader.read()) != -1 && isHexChar(current)) {
        this.sourceMapper.addCodePoint(current);
        cp.add(current);
      }
      this.reader.unread(current);

      final Integer codePoint = toInteger(cp.toStringAndReset(), 16);
      if (codePoint != null && CodePoints.isCodePoint(codePoint.intValue())) {
        this.sourceMapper.addFormattedCodePoint(codePoint.intValue());
      } else {
        this.sourceMapper.addError(PjbaErrorType.INVALID_CODEPOINT, line, column);
      }
      break;
    default:
      this.sourceMapper.addError(PjbaErrorType.INVALID_ESCAPE_SEQUENCE, line, column);
    }
  }

  /**
   * <p>
   * Returns a token of type:
   * <ul>
   * <li>{@link PjbaTokenType#INTEGER} ;</li>
   * <li>{@link PjbaTokenType#LONG} ;</li>
   * <li>{@link PjbaTokenType#FLOAT} or</li>
   * <li>{@link PjbaTokenType#DOUBLE}</li>
   * </ul>
   * <p>
   * The errors of the following types are added to the token:
   * <ul>
   * <li>{@link PjbaErrorType#INVALID_NUMBER} if the number starts with a + or - but is not followed by a digit or the
   * character '.' ;</li>
   * <li>{@link PjbaErrorType#INVALID_INTEGER_VALUE} if the sequence of code points can't be converted to an Integer ;
   * </li>
   * <li>{@link PjbaErrorType#INVALID_LONG_VALUE} if the sequence of code points can't be converted to a Long ;</li>
   * <li>{@link PjbaErrorType#INVALID_RADIX_VALUE} if the sequence doesn't have a radix or the radix is not between 2
   * and 16 (included)</li>
   * <li>{@link PjbaErrorType#INVALID_INTEGER_WITH_RADIX} if the sequence of code points can't be converted to an
   * Integer</li>
   * <li>{@link PjbaErrorType#INVALID_LONG_WITH_RADIX} if the sequence of code points can't be converted to a Long</li>
   * <li>{@link PjbaErrorType#INVALID_FLOAT_VALUE} if the sequence of code points can't be converted to a Float ;</li>
   * <li>{@link PjbaErrorType#INVALID_DOUBLE_VALUE} if
   * <ul>
   * <li>the sequence of code points can't be converted to a Double or</li>
   * <li>the character 'e'/'E' is not followed by a digit or the character '_' or</li>
   * <li>a sign character following the character 'e'/'E' is not a digit</li>
   * </ul>
   * </li>
   * </ul>
   * Examples:
   *
   * <pre>
   * &#64; 32-bit integers
   * 5
   * -5
   * +5
   * 1_000
   * 2r11111111
   * 2r1111_1111
   * 8r151
   * 16rAf05
   *
   * &#64; 64-bit integers (Long)
   * 5l
   * -5L
   * +5l
   * -9_223_372_036_854_775_808l
   *
   * &#64; 32-bit IEEE 754 floating point
   * 1.5f
   * -.0F
   * 6.9E+5
   * 6__9.0__9e+1__2
   *
   * &#64; 64-bit IEEE 754 floating point (double)
   * &#64; Same as float but 64-bit and not ending with 'f' or 'F'
   * &#64; Note: A double can't end with a 'd' or 'D'
   * </pre>
   *
   * @param current
   *          is the last code point read.
   */
  /* private testing */ Token tokenizeNumber(final int startLine, final int startColumn, final int character) {
    final CodePoints readCPs = new CodePoints(26);
    final CodePoints formattedCPs = new CodePoints(26);
    Token token = null;

    if (character == '+' || character == '-') {
      final int peek = this.reader.peek();

      if (!isDigit(peek) && peek != '.') {
        formattedCPs.clear();
        readCPs.add(peek);
        this.sourceMapper.addError(PjbaErrorType.INVALID_NUMBER, startLine, startColumn);
        this.addNumberCodePoints(readCPs, formattedCPs);
        token = this.formattedValueToken(PjbaTokenType.INTEGER, startLine, startColumn);
      }
    } else if (character == '.') {
      if (isDigit(this.reader.peek())) {
        return this.tokenizeFloat(readCPs, formattedCPs, startLine, startColumn, '.');
      } else {
        readCPs.add(".");
        this.addNumberCodePoints(readCPs, formattedCPs);
        return this.undefinedToken(startLine, startColumn);
      }
    }

    readCPs.add(character);
    if (character != '+') {
      formattedCPs.add(character);
    }

    int current = -1;
    while (isValidNumberChar(current = this.reader.read())) {
      readCPs.add(current);

      if (isDigit(current)) {
        formattedCPs.add(current);
      }
    }

    switch (current) {
    case '.':
      token = this.tokenizeFloat(readCPs, formattedCPs, startLine, startColumn, current);
      break;
    case 'e':
    case 'E':
      if (isValidNumberChar(this.reader.peek())) {
        token = this.tokenizeFloat(readCPs, formattedCPs, startLine, startColumn, current);
      } else {
        formattedCPs.clear();
        readCPs.add(current);
        this.sourceMapper.addError(PjbaErrorType.INVALID_DOUBLE_VALUE, startLine, startColumn);
        this.addNumberCodePoints(readCPs, formattedCPs);
        token = this.formattedValueToken(PjbaTokenType.DOUBLE, startLine, startColumn);
      }
      break;
    case 'r':
      if (isValidRadixValueChar(this.reader.peek())) {
        token = this.tokenizeRadixValue(readCPs, formattedCPs, startLine, startColumn);
      } else {
        formattedCPs.clear();
        readCPs.add(current);
        this.sourceMapper.addError(PjbaErrorType.INVALID_INTEGER_WITH_RADIX, startLine, startColumn);
        this.addNumberCodePoints(readCPs, formattedCPs);
        token = this.formattedValueToken(PjbaTokenType.INTEGER, startLine, startColumn);
      }
      break;
    default:
      this.reader.unread(current);

      final TokenType tokenType = this.getIntegerTokenType(readCPs);
      if (tokenType == PjbaTokenType.INTEGER && toInteger(formattedCPs.toString(), 10) == null) {
        this.sourceMapper.addError(PjbaErrorType.INVALID_INTEGER_VALUE, startLine, startColumn);
      } else if (tokenType == PjbaTokenType.LONG && toLong(formattedCPs.toString(), 10) == null) {
        this.sourceMapper.addError(PjbaErrorType.INVALID_LONG_VALUE, startLine, startColumn);
      }

      if (this.sourceMapper.hasErrors()) {
        formattedCPs.clear();
      }

      this.addNumberCodePoints(readCPs, formattedCPs);
      token = this.formattedValueToken(tokenType, startLine, startColumn);
    }

    return token;
  }

  /* private testing */ Token tokenizeFloat(final CodePoints readCPs, final CodePoints formattedCPs, //
      final int startLine, final int startColumn, final int character) {
    boolean isPeriodConsumed = false;
    boolean isExponentConsumed = false;

    if (character == '.') {
      isPeriodConsumed = true;
    } else if (character == 'e' || character == 'E') {
      isExponentConsumed = true;
    }

    readCPs.add(character);
    formattedCPs.add(character);

    int current = -1;
    while ((current = this.reader.read()) != -1) {
      if (current == '.' && !isPeriodConsumed) {
        isPeriodConsumed = true;

        readCPs.add(current);
        formattedCPs.add(current);
        current = this.reader.read();

        // Followed by a digit
        if (isValidNumberChar(current)) {
          readCPs.add(current);

          if (current != '_') {
            formattedCPs.add(current);
          }
        } else {
          break;
        }
      }
      // Exponent
      else if ((current == 'e' || current == 'E') && !isExponentConsumed) {
        isExponentConsumed = true;

        readCPs.add(current);
        formattedCPs.add(current);
        current = this.reader.read();

        // Followed by a digit
        if (isValidNumberChar(current)) {
          readCPs.add(current);

          if (current != '_') {
            formattedCPs.add(current);
          }
        }
        // Followed by a sign
        else if (current == '+' || current == '-') {
          readCPs.add(current);
          formattedCPs.add(current);
          current = this.reader.read();

          // Followed by a digit
          if (isDigit(current)) {
            readCPs.add(current);
            formattedCPs.add(current);
          } else {
            this.reader.unread(current);
            this.sourceMapper.addError(PjbaErrorType.INVALID_DOUBLE_VALUE, startLine, startColumn);
            break;
          }
        } else {
          this.reader.unread(current);
          this.sourceMapper.addError(PjbaErrorType.INVALID_DOUBLE_VALUE, startLine, startColumn);
          break;
        }
      }
      // Digit
      else if (isValidNumberChar(current)) {
        readCPs.add(current);

        if (current != '_') {
          formattedCPs.add(current);
        }
      }
      // Nothing
      else {
        this.reader.unread(current);
        break;
      }
    }

    TokenType tokenType = PjbaTokenType.DOUBLE;
    if (!this.sourceMapper.hasErrors()) {
      tokenType = this.getFloatTokenType(readCPs);

      if (tokenType == PjbaTokenType.FLOAT && toFloat(formattedCPs.toString()) == null) {
        this.sourceMapper.addError(PjbaErrorType.INVALID_FLOAT_VALUE, startLine, startColumn);
      } else if (tokenType == PjbaTokenType.DOUBLE && toDouble(formattedCPs.toString()) == null) {
        this.sourceMapper.addError(PjbaErrorType.INVALID_DOUBLE_VALUE, startLine, startColumn);
      }
    }

    if (this.sourceMapper.hasErrors()) {
      formattedCPs.clear();
    }

    this.addNumberCodePoints(readCPs, formattedCPs);

    return this.formattedValueToken(tokenType, startLine, startColumn);
  }

  /* private testing */ Token tokenizeRadixValue(final CodePoints readCPs, final CodePoints formattedCPs, //
      final int startLine, final int startColumn) {
    final Integer radix = toInteger(formattedCPs.toString(), 10);
    if (radix == null || radix < 2 || radix > 16) {
      this.sourceMapper.addError(PjbaErrorType.INVALID_RADIX_VALUE, startLine, startColumn);
    }

    readCPs.add('r');
    formattedCPs.clear();

    final CodePoints cp = new CodePoints();
    int current = -1;
    while ((current = this.reader.read()) != -1 && isValidRadixValueChar(current)) {
      readCPs.add(current);

      if (current != '_') {
        cp.add(current);
      }
    }

    this.reader.unread(current);

    TokenType tokenType = PjbaTokenType.INTEGER;
    if (!this.sourceMapper.hasErrors()) {
      tokenType = this.getIntegerTokenType(readCPs);

      if (tokenType == PjbaTokenType.INTEGER) {
        final Integer value = toInteger(cp.toStringAndReset(), radix.intValue());
        if (value == null) {
          this.sourceMapper.addError(PjbaErrorType.INVALID_INTEGER_WITH_RADIX, startLine, startColumn);
        } else {
          formattedCPs.add(String.valueOf(value));
        }
      } else {
        final Long value = toLong(cp.toStringAndReset(), radix.intValue());
        if (value == null) {
          this.sourceMapper.addError(PjbaErrorType.INVALID_LONG_WITH_RADIX, startLine, startColumn);
        } else {
          formattedCPs.add(String.valueOf(value));
        }
      }
    }

    this.addNumberCodePoints(readCPs, formattedCPs);
    return this.formattedValueToken(tokenType, startLine, startColumn);
  }

  private void addNumberCodePoints(final CodePoints readCPs, final CodePoints formattedCPs) {
    for (int i = readCPs.length() - 1; i >= 0; i--) {
      final int codePoint = readCPs.at(i);
      if (codePoint == '_') {
        readCPs.remove();
        this.reader.unread(codePoint);
      } else {
        break;
      }
    }

    this.sourceMapper.addCodePoints(readCPs);
    this.sourceMapper.addFormattedCodePoints(formattedCPs);
  }

  private TokenType getIntegerTokenType(final CodePoints readCPs) {
    TokenType tokenType;
    final int peek = this.reader.peek();
    if (peek == 'l' || peek == 'L') {
      readCPs.add(peek);
      this.reader.read();
      tokenType = PjbaTokenType.LONG;
    } else {
      tokenType = PjbaTokenType.INTEGER;
    }
    return tokenType;
  }

  private TokenType getFloatTokenType(final CodePoints readCPs) {
    TokenType tokenType;
    final int peek = this.reader.peek();
    if (peek == 'f' || peek == 'F') {
      readCPs.add(peek);
      this.reader.read();
      tokenType = PjbaTokenType.FLOAT;
    } else {
      tokenType = PjbaTokenType.DOUBLE;
    }
    return tokenType;
  }

  /**
   * Returns a token of type {@link PjbaTokenType#IDENTIFIER} or a modifier or a directive.
   *
   * @param startLine
   *          is the line of the first code point of the token.
   * @param startColumn
   *          is the column of the first code point of the token.
   * @param character
   *          is the first code point of the token.
   * @return a token of type {@link PjbaTokenType#IDENTIFIER} or a modifier or a directive.
   */
  /* private testing */ Token tokenizeJavaIdentifier(final int startLine, final int startColumn, final int character) {
    this.sourceMapper.addCodePoint(character);

    int current = -1;
    while ((current = this.reader.read()) != -1 && Tokenizer.isValidJavaIdentifierChar(current)) {
      this.sourceMapper.addCodePoint(current);
    }

    this.reader.unread(current);

    final TokenType tokenType = this.matchIdentifier(this.sourceMapper.value());
    if (tokenType == PjbaTokenType.IDENTIFIER) {
      return this.valueToken(tokenType, startLine, startColumn);
    } else {
      return this.constantToken(tokenType, startLine, startColumn);
    }
  }

  /* private testing */ TokenType matchIdentifier(final int[] identifier) {
    for (final TokenType tokenType : COREIDENTIFIER_TOKENS) {
      if (Arrays.equals(tokenType.value(), identifier)) {
        return tokenType;
      }
    }

    return PjbaTokenType.IDENTIFIER;
  }
}
