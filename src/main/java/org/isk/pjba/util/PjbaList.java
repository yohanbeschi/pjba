package org.isk.pjba.util;

import java.util.ArrayList;

import org.isk.pjba.visitor.Visitable;
import org.isk.pjba.visitor.Visitor;

public class PjbaList<E extends Visitable> extends ArrayList<E>implements Visitable {
  private static final long serialVersionUID = 1L;

  @Override
  public void accept(final Visitor visitor) {
    for (final Visitable v : this) {
      if (v != null) {
        v.accept(visitor);
      }
    }
  }
}