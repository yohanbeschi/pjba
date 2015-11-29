package org.isk.pjba.tokenizer.core.token;

import java.util.List;
import java.util.Set;

import org.isk.pjba.tokenizer.core.error.Error;
import org.isk.pjba.tokenizer.core.error.ErrorType;

/**
 * A <code>Token</code> offers the ability to hold informations about a {@link Token} like:
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
public interface Token {
  public static int[] EMPTY_INT_ARRAY = new int[0];

  /**
   * Returns the type of the token.
   *
   * @return the type of the token.
   */
  TokenType type();

  /**
   * Returns the raw value of the token.
   *
   * @return the raw value of the token.
   */
  int[] value();

  /**
   * Returns the formatted value of the token.
   *
   * @return the formatted value of the token.
   */
  int[] formattedValue();

  /**
   * Returns the line of the first code point.
   *
   * @return the line of the first code point.
   */
  int startLine();

  /**
   * Returns the line of the last code point.
   *
   * @return the line of the last code point.
   */
  int endLine();

  /**
   * Returns the column of the first code point.
   *
   * @return the column of the first code point.
   */
  int startColumn();

  /**
   * Returns the column of the last code point.
   *
   * @return the column of the last code point.
   */
  int endColumn();

  /**
   * Returns the errors of the token.
   *
   * @return the errors of the token.
   */
  Set<Error> errors();

  /**
   * Changes the token of the token
   *
   * @param tokenType
   *          is the new type of the token.
   */
  void resetType(TokenType tokenType);

  /**
   * <p>
   * Concatenates the current <code>Token</code> with another one.
   * <p>
   * The informations of the token in parameter will be added BEFORE the ones of the current token.
   * <p>
   * All fields of the token added are reset afterward.
   *
   * @param tokenType
   *          is the type of the new token.
   * @param token
   *          is the token to be concatenated with.
   */
  void addBefore(final TokenType tokenType, final Token token);

  /**
   * <p>
   * Concatenates the current <code>Token</code> with another one.
   * <p>
   * The informations of the token in parameter will be added AFTER the ones of the current token.
   * <p>
   * All fields of the token added are reset afterward.
   *
   * @param tokenType
   *          is the type of the new token.
   * @param token
   *          is the token to be concatenated with.
   */
  void addAfter(final TokenType tokenType, final Token token);

  /**
   * Resets all fields of the token.
   */
  void clear();

  /**
   * Squashes a list of tokens into one.
   *
   * @param tokenType
   *          is the type of the new token.
   * @param tokens
   *          is the list of tokens to be concatenated.
   * @return the new token.
   */
  public static Token concat(final TokenType tokenType, final List<Token> tokens) {
    final Token first = tokens.get(0);

    for (int i = 1; i < tokens.size(); i++) {
      first.addAfter(tokenType, tokens.get(i));
    }

    return first;
  }

  /**
   * Does the token has errors ?
   *
   * @return <code>true</code> if the token has errors, <code>false</code> otherwise.
   */
  default boolean hasErrors() {
    return this.errors().size() > 0;
  }

  /**
   * Adds an error to the token at the beginning of the token.
   *
   * @param errorType
   *          is the type the error.
   */
  default void addError(final ErrorType errorType) {
    this.errors().add(new Error(errorType, this.startLine(), this.startColumn()));
  }
}
