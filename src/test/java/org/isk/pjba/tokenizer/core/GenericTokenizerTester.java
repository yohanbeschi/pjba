package org.isk.pjba.tokenizer.core;

import org.isk.pjba.tokenizer.core.token.GenericTokenTester;

public abstract class GenericTokenizerTester<T extends Tokenizer, R extends GenericTokenizerTester<T, R>>
    extends GenericTokenTester<R> {
  protected final T tokenizer;

  public GenericTokenizerTester(final T tokenizer) {
    this.tokenizer = tokenizer;
  }

  public R tokenizeInsivibleChars() {
    this.actualToken = this.tokenizer.tokenizeInsivibleChars(1, 1, this.tokenizer.reader.read());
    return this.getThis();
  }

  public R getNextToken() {
    this.actualToken = this.tokenizer.getNextToken();
    return this.getThis();
  }
}
