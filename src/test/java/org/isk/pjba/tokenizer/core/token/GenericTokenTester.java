package org.isk.pjba.tokenizer.core.token;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import org.isk.pjba.tokenizer.core.error.ErrorType;

public abstract class GenericTokenTester<R> {
  protected Token actualToken;

  public R assertTokenType(final TokenType tokenType) {
    assertThat(this.actualToken.type()).isEqualTo(tokenType);
    return this.getThis();
  }

  public R value(final String value) {
    assertThat(this.actualToken.value()).as("Value").isEqualTo(value.codePoints().toArray());
    assertThat(this.actualToken.formattedValue()).as("Formatted Value").isEqualTo(Token.EMPTY_INT_ARRAY);
    return this.getThis();
  }

  public R values(final String value, final String formattedValue) {
    assertThat(this.actualToken.value()).as("Value").isEqualTo(value.codePoints().toArray());
    assertThat(this.actualToken.formattedValue()).as("Formatted Value")
        .isEqualTo(formattedValue.codePoints().toArray());
    return this.getThis();
  }

  public R start(final int startLine, final int startColumn) {
    assertThat(this.actualToken.startLine()).as(this.actualToken.toString()).isEqualTo(startLine);
    assertThat(this.actualToken.startColumn()).as(this.actualToken.toString()).isEqualTo(startColumn);
    return this.getThis();
  }

  public R end(final int endLine, final int endColumn) {
    assertThat(this.actualToken.endLine()).as(this.actualToken.toString()).isEqualTo(endLine);
    assertThat(this.actualToken.endColumn()).as(this.actualToken.toString()).isEqualTo(endColumn);
    return this.getThis();
  }

  public R eos() {
    assertThat(this.actualToken).isEqualTo(EndOfStreamToken.EOS);
    return this.getThis();
  }

  public R assertNoErrors() {
    assertThat(this.actualToken.errors()).isEmpty();
    return this.getThis();
  }

  public R assertErrors(final int expectedSize) {
    assertThat(this.actualToken.errors()).hasSize(expectedSize);
    return this.getThis();
  }

  public R assertError(final ErrorType errorType, final int line, final int column) {
    assertThat(this.actualToken.errors()) //
        .extracting(e -> e.errorType(), e -> e.line(), e -> e.column()) //
        .contains(tuple(errorType, line, column));
    return this.getThis();
  }

  public abstract R getThis();
}
