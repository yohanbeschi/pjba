package org.isk.pjba.tokenizer.core.token;

import java.util.Collections;
import java.util.Set;

import org.isk.pjba.tokenizer.core.error.Error;

public enum EndOfStreamToken implements Token {
  EOS;

  @Override
  public TokenType type() {
    return CoreTokenType.EOS;
  }

  @Override
  public int[] value() {
    return null;
  }

  @Override
  public int[] formattedValue() {
    return null;
  }

  @Override
  public int startLine() {
    return -1;
  }

  @Override
  public int endLine() {
    return -1;
  }

  @Override
  public int startColumn() {
    return -1;
  }

  @Override
  public int endColumn() {
    return -1;
  }

  @Override
  public Set<Error> errors() {
    return Collections.emptySet();
  }

  @Override
  public void resetType(final TokenType tokenType) {
    // Do nothing
  }

  @Override
  public void addBefore(final TokenType tokenType, final Token token) {
    // Do nothing
  }

  @Override
  public void addAfter(final TokenType tokenType, final Token token) {
    // Do nothing
  }

  @Override
  public void clear() {
    // Do nothing
  }

  @Override
  public String toString() {
    return "EOS";
  }
}
