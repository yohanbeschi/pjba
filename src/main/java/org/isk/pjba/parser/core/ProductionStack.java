package org.isk.pjba.parser.core;

import java.util.ArrayDeque;
import java.util.Deque;

import org.isk.pjba.tokenizer.core.reader.TokenReader;

public class ProductionStack<T extends TokenReader, P extends ProductionStack<T, P, B>, B extends AstBuilder> {
  private final Deque<Symbol<T, P, B>> productionStack = new ArrayDeque<>();

  public ProductionStack(final Symbol<T, P, B> startSymbol) {
    this.productionStack.push(startSymbol);
  }

  public void push(final Symbol<T, P, B> symbol) {
    this.productionStack.push(symbol);
  }

  public Symbol<T, P, B> pop() {
    return this.productionStack.pop();
  }

  public Symbol<T, P, B> peek() {
    return this.productionStack.peek();
  }

  public boolean isEmpty() {
    return this.productionStack.isEmpty();
  }

  public void skipToBefore(final Symbol<T, P, B> symbol) {
    while (!this.isEmpty() && this.peek() != symbol) {
      this.pop();
    }
  }

  public void skipTo(final Symbol<T, P, B> symbol) {
    while (!this.isEmpty() && this.pop() != symbol) {

    }
  }
}
