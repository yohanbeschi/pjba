package org.isk.pjba.structure.attribute;

import org.isk.pjba.visitor.Visitable;
import org.isk.pjba.visitor.Visitor;

public abstract class Attribute implements Visitable {
  final private int nameIndex;

  public Attribute(final int nameIndex) {
    this.nameIndex = nameIndex;
  }

  @Override
  public void accept(final Visitor visitor) {
    visitor.visitAttributeNameIndex(this.nameIndex);
  }
}