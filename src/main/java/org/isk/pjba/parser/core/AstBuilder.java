package org.isk.pjba.parser.core;

public interface AstBuilder {
  void endStream();

  <R> R getBuiltObject();
}
