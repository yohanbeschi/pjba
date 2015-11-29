package org.isk.pjba.tokenizer.core.token;

public interface CoreTokenType {
  public static final TokenType EOS = new TokenType("EOS");
  public static final TokenType UNDEFINED = new TokenType("UNDEFINED");
  public static final TokenType WHITESPACE = new TokenType("WHITESPACE", true, true, false);
  public static final TokenType NEW_LINE = new TokenType("NEW_LINE", true, false, true);
}
