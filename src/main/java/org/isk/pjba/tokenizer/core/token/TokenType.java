package org.isk.pjba.tokenizer.core.token;

public class TokenType {

  /**
   * The name of the token type.
   */
  private final String name;

  /**
   * The constant value of the token type (can be <code>null</code>).
   */
  private final int[] value;

  /**
   * Is it a white space token type ?
   */
  private final boolean isWhitespace;

  /**
   * Is it a one line white space token type (Composed of TAB and SPACE only) /
   */
  private final boolean isOneLineWhitespace;

  /**
   * Is it a multiple lines white space token type (Composed of CR, LF, TAB and SPACE) ?
   */
  private final boolean isMultiLinesWhitespace;

  /**
   * Instantiates a new <code>TokenType</code>.
   *
   * @param name
   *          is the name of the <code>TokenType</code>.
   */
  public TokenType(final String name) {
    this(name, (int[]) null, false, false, false);
  }

  /**
   * Instantiates a new (non whitespace) <code>TokenType</code> with a constant value (where where value is a code
   * point).
   *
   * @param name
   *          is the name of the <code>TokenType</code>.
   * @param value
   *          is the constant value of the <code>TokenType</code> as a code point.
   */
  public TokenType(final String name, final int value) {
    this(name, new int[] { value }, false, false, false);
  }

  /**
   * Instantiates a new (non whitespace) <code>TokenType</code> with a constant value.
   *
   * @param name
   *          is the name of the <code>TokenType</code>.
   * @param value
   *          is the constant value of the <code>TokenType</code>.
   */
  public TokenType(final String name, final String value) {
    this(name, value, false, false, false);
  }

  /**
   * Instantiates a new <code>TokenType</code>.
   *
   * @param name
   *          is the name of the <code>TokenType</code>.
   * @param isWhitespace
   *          does the token type a white space ?
   * @param isOneLineWhitespace
   *          does the token type a one line white space ?
   * @param isMultiLinesWhitespace
   *          does the token type a multiple lines white space ?
   */
  public TokenType(final String name, final boolean isWhitespace, final boolean isOneLineWhitespace,
      final boolean isMultiLinesWhitespace) {
    this(name, (int[]) null, isWhitespace, isOneLineWhitespace, isMultiLinesWhitespace);
  }

  /**
   * Instantiates a new <code>TokenType</code>.
   *
   * @param name
   *          is the name of the <code>TokenType</code>.
   * @param value
   *          is the constant value of the <code>TokenType</code>.
   * @param isWhitespace
   *          does the token type a white space ?
   * @param isOneLineWhitespace
   *          does the token type a one line white space ?
   * @param isMultiLinesWhitespace
   *          does the token type a multiple lines white space ?
   */
  public TokenType(final String name, final String value, final boolean isWhitespace, final boolean isOneLineWhitespace,
      final boolean isMultiLinesWhitespace) {
    this(name, value.codePoints().toArray(), isWhitespace, isOneLineWhitespace, isMultiLinesWhitespace);
  }

  /**
   * Instantiates a new <code>TokenType</code>.
   *
   * @param name
   *          is the name of the <code>TokenType</code>.
   * @param value
   *          is the constant value of the <code>TokenType</code> as an array of code points.
   * @param isWhitespace
   *          does the token type a white space ?
   * @param isOneLineWhitespace
   *          does the token type a one line white space ?
   * @param isMultiLinesWhitespace
   *          does the token type a multiple lines white space ?
   */
  public TokenType(final String name, final int[] value, final boolean isWhitespace, final boolean isOneLineWhitespace,
      final boolean isMultiLinesWhitespace) {
    this.name = name;
    this.value = value;
    this.isWhitespace = isWhitespace;
    this.isOneLineWhitespace = isOneLineWhitespace;
    this.isMultiLinesWhitespace = isMultiLinesWhitespace;
  }

  /**
   * Returns the name of the token type.
   *
   * @return the name of the token type.
   */
  public String name() {
    return this.name;
  }

  /**
   * Return the constant value of the token type.
   *
   * @return the constant value of the token type.
   */
  public int[] value() {
    return this.value;
  }

  /**
   * Is it a white space token type ?
   *
   * @return <code>true</code> if it's a white space token type, <code>false</code> otherwise.
   */
  public boolean isWhitespace() {
    return this.isWhitespace;
  }

  /**
   * Is it a one line white space token type ?
   *
   * @return <code>true</code> if it's a one line white space token type, <code>false</code> otherwise.
   */
  public boolean isOnelineWhitespace() {
    return this.isOneLineWhitespace;
  }

  /**
   * Is it a multiple lines white space token type ?
   *
   * @return <code>true</code> if it's a multiple lines white space token type, <code>false</code> otherwise.
   */
  public boolean isMultiLinesWhitespace() {
    return this.isMultiLinesWhitespace;
  }

  @Override
  public String toString() {
    return "TokenType [name=" + this.name + "]";
  }
}
