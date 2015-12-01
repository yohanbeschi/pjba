package org.isk.pjba.parser.core;

import org.isk.pjba.tokenizer.core.reader.TokenReader;

@FunctionalInterface
public interface Production<T extends TokenReader, P extends ProductionStack<T, P, B>, B extends AstBuilder> {
  void produce(T reader, P productionStack, B builder);
}
