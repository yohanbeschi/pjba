package org.isk.pjba.tokenizer.core;

import java.util.ArrayList;
import java.util.List;

import org.isk.pjba.tokenizer.core.error.Error;
import org.isk.pjba.tokenizer.core.error.ErrorType;
import org.isk.pjba.unicode.CodePoints;

/**
 * <p>
 * A <code>SourceMapper</code> offers the ability to:
 * <ul>
 * <li>track the position (line/column) of a code point as it will be represented in a text file;
 * <li>maintain a raw value of the current token being processed ;
 * <li>maintain a formatted value of the current token being processed (ie. the real value of an Unicode character
 * -\\uxxxx) ;
 * <li>maintain a list of errors that could happened during the parsing.
 * </ul>
 * <p>
 * Note: A <code>SourceMapper</code> should never hold a complete source file but only the current token being
 * processed.
 */
public class SourceMapper {
  /**
   * Line Feed.
   */
  final public static int LF = 0x0A;

  /**
   * Carriage Return.
   */
  final public static int CR = 0x0D;

  /**
   * Tabulation.
   */
  final public static int TAB = 0x09;

  /**
   * SPACE.
   */
  final public static int SPACE = 0x20;

  /**
   * Raw value.
   */
  private final CodePoints value = new CodePoints();

  /**
   * Processed value (pre-compiled for example)
   */
  private final CodePoints formattedValue = new CodePoints();

  /**
   * Holds errors.
   */
  /* private testing */ private List<Error> errors = new ArrayList<>();

  /* private testing */ int currentColumn = 1;
  /* private testing */ int currentLine = 1;

  private int previousCodePoint = -1;

  /**
   * Adds a code point to the raw value.
   *
   * @param codePoint
   *          is the code point to add.
   * @return <code>true</code> if the code point represent a CR (Carriage Return) or a LF (Line Feed) not being part of
   *         a CRLF, otherwise returns <code>false</code>.
   */
  public boolean addCodePoint(final int codePoint) {
    boolean newLine = false;

    switch (codePoint) {
    case LF:
    case CR:
      // Not CRLF
      if (this.previousCodePoint != CR || codePoint != LF) {
        this.currentColumn = 1;
        this.currentLine++;
      }

      newLine = true;
      break;
    case TAB:
      this.currentColumn++; // 2 spaces for 1 tab
    default:
      this.currentColumn++;
    }

    this.value.add(codePoint);
    this.previousCodePoint = codePoint;

    return newLine;
  }

  /**
   * Returns the current column.
   *
   * @return the current column.
   */
  public int currentColumn() {
    return this.currentColumn;
  }

  /**
   * Returns the current line.
   *
   * @return the current line.
   */
  public int currentLine() {
    return this.currentLine;
  }

  /**
   * Increments the current column.
   *
   * @return the current column incremented.
   */
  public int incCurrentColumn() {
    return this.currentColumn++;
  }

  /**
   * Adds a {@link CodePoints} to the raw value.
   *
   * @param codePoints
   *          is the {@link CodePoints} to add.
   */
  public void addCodePoints(final CodePoints codePoints) {
    codePoints.stream().forEach(codePoint -> this.addCodePoint(codePoint));
  }

  /**
   * Adds a code point to the formatted value.
   *
   * @param codePoint
   *          is the code point to add.
   */
  public void addFormattedCodePoint(final int codePoint) {
    this.formattedValue.add(codePoint);
  }

  /**
   * Adds a {@link CodePoints} to the formatted value.
   *
   * @param codePoints
   *          is the {@link CodePoints} to add.
   */
  public void addFormattedCodePoints(final CodePoints codePoints) {
    this.formattedValue.add(codePoints.toArray());
  }

  /**
   * Add an error to the list of errors.
   *
   * @param errorType
   *          is the type of error.
   * @param line
   *          is the line of the error.
   * @param column
   *          is the column of the error.
   */
  public void addError(final ErrorType errorType, final int line, final int column) {
    this.errors.add(new Error(errorType, line, column));
  }

  /**
   * Add an error at the current position (line/column)
   *
   * @param errorType
   *          is the type of the error.
   */
  public void addError(final ErrorType errorType) {
    this.addError(errorType, this.currentLine, this.currentColumn);
  }

  /**
   * Is there any error ?
   *
   * @return <code>true</code> if there is at least an error, otherwise false.
   */
  public boolean hasErrors() {
    return !this.errors.isEmpty();
  }

  /**
   * Returns the list of errors.
   *
   * @return the list of errors.
   */
  public List<Error> errors() {
    return this.errors;
  }

  /**
   * Returns the list of errors and clear it.
   *
   * @return the list of errors.
   */
  public List<Error> errorsAndReset() {
    final List<Error> tmp = this.errors;
    this.errors = new ArrayList<>();
    return tmp;
  }

  /**
   * Returns the raw value as an array of code points.
   *
   * @return the raw value.
   */
  public int[] value() {
    return this.value.toArray();
  }

  /**
   * Returns the raw value as an array of code points and resets it.
   *
   * @return the raw value.
   */
  public int[] valueAndReset() {
    return this.value.toArrayAndReset();
  }

  /**
   * Returns the raw value as a String.
   *
   * @return the raw value.
   */
  public String valueAsString() {
    return this.value.toString();
  }

  /**
   * Resets the raw value.
   */
  public void clearValue() {
    this.value.clear();
  }

  /**
   * Returns the raw value as an array of code points.
   *
   * @return the formatted value.
   */
  public int[] formattedValue() {
    return this.formattedValue.toArray();
  }

  /**
   * Returns the raw value as an array of code points and resets it.
   *
   * @return the formatted value.
   */
  public int[] formattedValueAndReset() {
    return this.formattedValue.toArrayAndReset();
  }

  /**
   * Resets the formatted value.
   */
  public void clearFormattedValue() {
    this.formattedValue.clear();
  }
}
