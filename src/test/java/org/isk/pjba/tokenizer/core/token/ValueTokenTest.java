package org.isk.pjba.tokenizer.core.token;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.isk.pjba.tokenizer.core.error.CoreErrorType;
import org.isk.pjba.tokenizer.core.error.Error;
import org.isk.pjba.util.ArraysUtil;
import org.junit.Test;

public class ValueTokenTest {

  private static final TokenType CONSTANT_TT = new TokenType("CONSTANT_TT", "constant");

  @Test
  public void value_null() {
    final ValueToken token = new ValueToken(CONSTANT_TT, null, 1, 1, 1, 9, Collections.emptyList());
    assertThat(token.value()).isEqualTo("constant".codePoints().toArray());
  }

  @Test
  public void value_empty() {
    final ValueToken token = new ValueToken(CONSTANT_TT, Token.EMPTY_INT_ARRAY, 1, 1, 1, 9, Collections.emptyList());
    assertThat(token.value()).isEqualTo("constant".codePoints().toArray());
  }

  @Test
  public void value() {
    final int[] value = "other".codePoints().toArray();
    final ValueToken token = new ValueToken(CONSTANT_TT, value, 1, 1, 1, 9, Collections.emptyList());
    assertThat(token.value()).isEqualTo(value);
  }

  @Test
  public void formattedValue_null() {
    final ValueToken token = new ValueToken(CONSTANT_TT, null, null, 1, 1, 1, 9, Collections.emptyList());
    assertThat(token.formattedValue()).isNull();
    ;
  }

  @Test
  public void formattedValue_empty() {
    final ValueToken token = new ValueToken(CONSTANT_TT, null, Token.EMPTY_INT_ARRAY, 1, 1, 1, 9,
        Collections.emptyList());
    assertThat(token.formattedValue()).isEmpty();
  }

  @Test
  public void formattedValue() {
    final int[] formattedValue = "other".codePoints().toArray();
    final ValueToken token = new ValueToken(CONSTANT_TT, null, formattedValue, 1, 1, 1, 9, Collections.emptyList());
    assertThat(token.formattedValue()).isEqualTo(formattedValue);
  }

  @Test
  public void clear() {
    final ValueToken token = new ValueToken(CONSTANT_TT, null, 1, 1, 1, 9, Collections.emptyList());
    token.clear();
    this.testClearedToken(token);
  }

  @Test
  public void addBefore() {
    final int[] value1 = "one".codePoints().toArray();
    final int[] formattedValue1 = "formattedOne".codePoints().toArray();
    final List<Error> errors1 = Arrays.asList(new Error(CoreErrorType.INVALID_TOKEN, 1, 1));
    final int[] value2 = "two".codePoints().toArray();
    final int[] formattedValue2 = "formattedTwo".codePoints().toArray();
    final List<Error> errors2 = Arrays.asList(new Error(CoreErrorType.UNDEFINED_TOKEN, 2, 2));

    final ValueToken token1 = new ValueToken(CONSTANT_TT, value1, formattedValue1, 1, 1, 1, 3, errors1);
    final ValueToken token2 = new ValueToken(CONSTANT_TT, value2, formattedValue2, 2, 2, 1, 9, errors2);
    token1.addBefore(CoreTokenType.EOS, token2);

    assertThat(token1.type()).isEqualTo(CoreTokenType.EOS);
    assertThat(token1.value()).isEqualTo(ArraysUtil.concat(value2, value1));
    assertThat(token1.formattedValue()).isEqualTo(ArraysUtil.concat(formattedValue2, formattedValue1));
    assertThat(token1.startLine()).isEqualTo(2);
    assertThat(token1.endLine()).isEqualTo(1);
    assertThat(token1.startColumn()).isEqualTo(1);
    assertThat(token1.endColumn()).isEqualTo(3);
    assertThat(token1.errors()).hasSize(2);
    assertThat(token1.errors()).extracting(e -> e.errorType()).containsOnly(CoreErrorType.INVALID_TOKEN,
        CoreErrorType.UNDEFINED_TOKEN);

    this.testClearedToken(token2);
  }

  @Test
  public void addAfter() {
    final int[] value1 = "one".codePoints().toArray();
    final int[] formattedValue1 = "formattedOne".codePoints().toArray();
    final List<Error> errors1 = Arrays.asList(new Error(CoreErrorType.INVALID_TOKEN, 1, 1));
    final int[] value2 = "two".codePoints().toArray();
    final int[] formattedValue2 = "formattedTwo".codePoints().toArray();
    final List<Error> errors2 = Arrays.asList(new Error(CoreErrorType.UNDEFINED_TOKEN, 2, 2));

    final ValueToken token1 = new ValueToken(CONSTANT_TT, value1, formattedValue1, 1, 1, 1, 3, errors1);
    final ValueToken token2 = new ValueToken(CONSTANT_TT, value2, formattedValue2, 2, 2, 1, 9, errors2);
    token1.addAfter(CoreTokenType.EOS, token2);

    assertThat(token1.type()).isEqualTo(CoreTokenType.EOS);
    assertThat(token1.value()).isEqualTo(ArraysUtil.concat(value1, value2));
    assertThat(token1.formattedValue()).isEqualTo(ArraysUtil.concat(formattedValue1, formattedValue2));
    assertThat(token1.startLine()).isEqualTo(1);
    assertThat(token1.endLine()).isEqualTo(2);
    assertThat(token1.startColumn()).isEqualTo(1);
    assertThat(token1.endColumn()).isEqualTo(9);
    assertThat(token1.errors()).hasSize(2);
    assertThat(token1.errors()).extracting(e -> e.errorType()).containsOnly(CoreErrorType.INVALID_TOKEN,
        CoreErrorType.UNDEFINED_TOKEN);

    this.testClearedToken(token2);
  }

  @Test
  public void hasErrors() {
    final int[] value = "one".codePoints().toArray();
    final List<Error> errors = Arrays.asList(new Error(CoreErrorType.INVALID_TOKEN, 1, 1));
    final ValueToken token = new ValueToken(CONSTANT_TT, value, null, 1, 1, 1, 3, errors);
    assertThat(token.hasErrors()).isTrue();
  }

  @Test
  public void addError() {
    final int[] value = "other".codePoints().toArray();
    final ValueToken token = new ValueToken(CONSTANT_TT, value, 1, 1, 1, 9, Collections.emptyList());
    assertThat(token.hasErrors()).isFalse();
    token.addError(CoreErrorType.UNDEFINED_TOKEN);
    assertThat(token.hasErrors()).isTrue();
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Helper
  // -------------------------------------------------------------------------------------------------------------------

  private void testClearedToken(final ValueToken token) {
    assertThat(token.type()).isNull();
    assertThat(token.value()).isEmpty();
    assertThat(token.formattedValue()).isEmpty();
    assertThat(token.startLine()).isEqualTo(0);
    assertThat(token.endLine()).isEqualTo(0);
    assertThat(token.startColumn()).isEqualTo(0);
    assertThat(token.endColumn()).isEqualTo(0);
    assertThat(token.errors()).isEmpty();
  }
}
