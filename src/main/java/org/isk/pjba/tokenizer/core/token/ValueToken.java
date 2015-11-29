package org.isk.pjba.tokenizer.core.token;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.isk.pjba.tokenizer.core.error.Error;
import org.isk.pjba.util.ArraysUtil;

/**
 * A <code>ValueToken</code> offers the ability to hold informations about a {@link Token} like:
 * <ul>
 * <li>its type ;</li>
 * <li>its raw value ;</li>
 * <li>its formatted value ;</li>
 * <li>the line of the first code point ;</li>
 * <li>the column of the first code point ;</li>
 * <li>the line of the last code point ;</li>
 * <li>the column of the last code point and</li>
 * <li>errors.</li>
 * </ul>
 */
public class ValueToken implements Token {
  private TokenType tokenType;
  private int[] value;
  private int[] formattedValue;
  private int startLine;
  private int endLine;
  private int startColumn;
  private int endColumn;
  private final Set<Error> errors = new HashSet<>();

  /**
   * Instantiates a new <code>ValueToken</code> WITHOUT a formatted value.
   *
   * @param tokenType
   *          is the type of the token.
   * @param value
   *          is the raw value of the token.
   * @param startLine
   *          is the line of the first code point.
   * @param endLine
   *          is the line of the last code point.
   * @param startColumn
   *          is the column of the first code point.
   * @param endColumn
   *          the column of the last code point.
   * @param errors
   *          is a list of errors.
   */
  public ValueToken(final TokenType tokenType, final int[] value, //
      final int startLine, final int endLine, //
      final int startColumn, final int endColumn, //
      final List<Error> errors) {
    this(tokenType, value, EMPTY_INT_ARRAY, startLine, endLine, startColumn, endColumn, errors);
  }

  /**
   * Instantiates a new <code>ValueToken</code> WITH a formatted value.
   *
   * @param tokenType
   *          is the type of the token.
   * @param value
   *          is the raw value of the token.
   * @param formattedValue
   *          is the formatted value of the token.
   * @param startLine
   *          is the line of the first code point.
   * @param endLine
   *          is the line of the last code point.
   * @param startColumn
   *          is the column of the first code point.
   * @param endColumn
   *          the column of the last code point.
   * @param errors
   *          is a list of errors.
   */
  public ValueToken(final TokenType tokenType, final int[] value, final int[] formattedValue, //
      final int startLine, final int endLine, //
      final int startColumn, final int endColumn, //
      final List<Error> errors) {

    this.tokenType = tokenType;
    this.value = value;
    this.formattedValue = formattedValue;
    this.startLine = startLine;
    this.endLine = endLine;
    this.startColumn = startColumn;
    this.endColumn = endColumn;

    if (errors != null) {
      this.errors.addAll(errors);
    }
  }

  /*
   * {@inheritDoc}
   */
  @Override
  public TokenType type() {
    return this.tokenType;
  }

  /**
   * <p>
   * Returns the raw value of the token.
   * <ul>
   * <li>If the value exists we return it ;</li>
   * <li>else if the token type exists we return its value ;</li>
   * <li>else we return an empty int array.</li>
   * </ul>
   *
   * @return the raw value of the token.
   */
  @Override
  public int[] value() {
    if (this.value != null && this.value.length != 0) {
      return this.value;
    } else if (this.tokenType != null) {
      return this.tokenType.value();
    } else {
      return EMPTY_INT_ARRAY;
    }
  }

  /*
   * {@inheritDoc}
   */
  @Override
  public int[] formattedValue() {
    return this.formattedValue;
  }

  /*
   * {@inheritDoc}
   */
  @Override
  public int startLine() {
    return this.startLine;
  }

  /*
   * {@inheritDoc}
   */
  @Override
  public int endLine() {
    return this.endLine;
  }

  /*
   * {@inheritDoc}
   */
  @Override
  public int startColumn() {
    return this.startColumn;
  }

  /*
   * {@inheritDoc}
   */
  @Override
  public int endColumn() {
    return this.endColumn;
  }

  /*
   * {@inheritDoc}
   */
  @Override
  public Set<Error> errors() {
    return this.errors;
  }

  /*
   * {@inheritDoc}
   */
  @Override
  public void resetType(final TokenType tokenType) {
    this.tokenType = tokenType;
  }

  /*
   * {@inheritDoc}
   */
  @Override
  public void addBefore(final TokenType tokenType, final Token token) {
    this.value = ArraysUtil.concat(token.value(), this.value());
    this.formattedValue = ArraysUtil.concat(token.formattedValue(), this.formattedValue());
    this.startLine = token.startLine();
    this.startColumn = token.startColumn();
    this.errors.addAll(token.errors());
    this.tokenType = tokenType;

    token.clear();
  }

  /*
   * {@inheritDoc}
   */
  @Override
  public void addAfter(final TokenType tokenType, final Token token) {
    this.value = ArraysUtil.concat(this.value(), token.value());
    this.formattedValue = ArraysUtil.concat(this.formattedValue(), token.formattedValue());
    this.endLine = token.endLine();
    this.endColumn = token.endColumn();
    this.errors.addAll(token.errors());
    this.tokenType = tokenType;

    token.clear();
  }

  /*
   * {@inheritDoc}
   */
  @Override
  public void clear() {
    this.tokenType = null;
    this.value = EMPTY_INT_ARRAY;
    this.formattedValue = EMPTY_INT_ARRAY;
    this.startLine = 0;
    this.endLine = 0;
    this.startColumn = 0;
    this.endColumn = 0;
    this.errors().clear();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.endColumn;
    result = prime * result + this.endLine;
    result = prime * result + Arrays.hashCode(this.formattedValue);
    result = prime * result + this.startColumn;
    result = prime * result + this.startLine;
    result = prime * result + (this.tokenType == null ? 0 : this.tokenType.hashCode());
    result = prime * result + Arrays.hashCode(this.value);
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final ValueToken other = (ValueToken) obj;
    if (this.endColumn != other.endColumn) {
      return false;
    }
    if (this.endLine != other.endLine) {
      return false;
    }
    if (!Arrays.equals(this.formattedValue, other.formattedValue)) {
      return false;
    }
    if (this.startColumn != other.startColumn) {
      return false;
    }
    if (this.startLine != other.startLine) {
      return false;
    }
    if (this.tokenType != other.tokenType) {
      return false;
    }
    if (!Arrays.equals(this.value(), other.value())) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ValueToken [tokenType=" + this.tokenType + ", value=" + Arrays.toString(this.value()) + ", formattedValue="
        + Arrays.toString(this.formattedValue()) + ", startLine=" + this.startLine + ", endLine=" + this.endLine
        + ", startColumn=" + this.startColumn + ", endColumn=" + this.endColumn + ", errors=" + this.errors + "]";
  }
}
