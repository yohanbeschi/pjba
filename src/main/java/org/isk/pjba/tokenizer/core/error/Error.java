package org.isk.pjba.tokenizer.core.error;

import org.isk.pjba.tokenizer.core.token.Token;

/**
 * An <code>Error</code> offers the ability to add an error to a {@link Token}.
 */
public class Error {
  /**
   * Type of error.
   */
  private final ErrorType errorType;

  /**
   * Line of the error in a virtual file.
   */
  private final int line;

  /**
   * Column of the error in a virtual file.
   */
  private final int column;

  /**
   * Instantiates a new error.
   *
   * @param errorType
   *          is the type of error.
   * @param line
   *          is the line of the error in a virtual file.
   * @param column
   *          is the column of the error in a virtual file.
   */
  public Error(final ErrorType errorType, final int line, final int column) {
    super();
    this.errorType = errorType;
    this.line = line;
    this.column = column;
  }

  /**
   * Return the type of error.
   *
   * @return the type of error.
   */
  public ErrorType errorType() {
    return this.errorType;
  }

  /**
   * Returns the line of the error in a virtual file.
   *
   * @return the line of the error in a virtual file.
   */
  public int line() {
    return this.line;
  }

  /**
   * Returns the column of the error in a virtual file.
   *
   * @return the column of the error in a virtual file.
   */
  public int column() {
    return this.column;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.column;
    result = prime * result + (this.errorType == null ? 0 : this.errorType.hashCode());
    result = prime * result + this.line;
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
    final Error other = (Error) obj;
    if (this.column != other.column) {
      return false;
    }
    if (this.errorType != other.errorType) {
      return false;
    }
    if (this.line != other.line) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Error [errorType=" + this.errorType + ", line=" + this.line + ", column=" + this.column + "]";
  }
}
