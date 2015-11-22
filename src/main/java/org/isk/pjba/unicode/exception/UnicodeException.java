package org.isk.pjba.unicode.exception;

public class UnicodeException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public UnicodeException(final String message) {
    super(message);
  }

  public UnicodeException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
