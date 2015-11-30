package org.isk.pjba.tokenizer;

import org.isk.pjba.tokenizer.core.token.TokenType;

public interface PjbaTokenType {
  public static final TokenType PARAMETER_START = new TokenType("PARAMETER_START", '(');
  public static final TokenType PARAMETER_END = new TokenType("PARAMETER_END", ')');
  public static final TokenType IDENTIFIER_SEPARATOR = new TokenType("IDENTIFIER_SEPARATOR", '/');
  public static final TokenType STRING_DELIMITER = new TokenType("STRING_DELIMITER", '"');
  public static final TokenType BRACKET_START = new TokenType("BRACKET_START", '[');
  public static final TokenType SEMICOLON = new TokenType("SEMICOLON", ';');

  public static final TokenType OL_COMMENT = new TokenType("OL_COMMENT", true, true, false);
  public static final TokenType ML_COMMENT = new TokenType("ML_COMMENT", true, false, true);

  public static final TokenType DIR_CLASS_START = new TokenType("DIR_CLASS_START", ".class");
  public static final TokenType DIR_CLASS_END = new TokenType("DIR_CLASS_END", ".classend");
  public static final TokenType DIR_METHOD_START = new TokenType("DIR_CLASS_START", ".method");
  public static final TokenType DIR_METHOD_END = new TokenType("DIR_CLASS_END", ".methodend");

  public static final TokenType FINAL = new TokenType("FINAL", "final");
  public static final TokenType ABSTRACT = new TokenType("ABSTRACT", "abstract");
  public static final TokenType INTERFACE = new TokenType("INTERFACE", "interface");
  public static final TokenType SUPER = new TokenType("SUPER", "super");
  public static final TokenType PUBLIC = new TokenType("PUBLIC", "public");
  public static final TokenType PROTECTED = new TokenType("PROTECTED", "protected");
  public static final TokenType PRIVATE = new TokenType("PRIVATE", "private");
  public static final TokenType STATIC = new TokenType("STATIC", "static");
  public static final TokenType STRICTFP = new TokenType("STRICTFP", "strictfp");
  public static final TokenType SYNCHRONIZED = new TokenType("SYNCHRONIZED", "synchronized");
  public static final TokenType NATIVE = new TokenType("NATIVE", "native");

  public static final TokenType STRING = new TokenType("STRING");
  public static final TokenType INTEGER = new TokenType("INTEGER");
  public static final TokenType LONG = new TokenType("LONG");
  public static final TokenType FLOAT = new TokenType("FLOAT");
  public static final TokenType DOUBLE = new TokenType("DOUBLE");
  public static final TokenType IDENTIFIER = new TokenType("IDENTIFIER");

}
