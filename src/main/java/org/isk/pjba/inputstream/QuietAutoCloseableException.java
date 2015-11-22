package org.isk.pjba.inputstream;

public class QuietAutoCloseableException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public QuietAutoCloseableException(final String message) {
    super(message);
  }

  public QuietAutoCloseableException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
