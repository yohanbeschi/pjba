package org.isk.pjba.visitor;

public interface Visitable {
  void accept(Visitor visitor);
}
