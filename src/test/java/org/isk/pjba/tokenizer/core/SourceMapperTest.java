package org.isk.pjba.tokenizer.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class SourceMapperTest {
  @Test
  public void addChar() {
    final SourceMapper sourceMapper = new SourceMapper();
    this.test(sourceMapper, 1, 1);
    assertThat(sourceMapper.addCodePoint('a')).isFalse();
    this.test(sourceMapper, 1, 2);
    assertThat(sourceMapper.addCodePoint('\n')).isTrue();
    this.test(sourceMapper, 2, 1);
    assertThat(sourceMapper.addCodePoint('b')).isFalse();
    this.test(sourceMapper, 2, 2);
    assertThat(sourceMapper.addCodePoint('\r')).isTrue();
    this.test(sourceMapper, 3, 1);
    assertThat(sourceMapper.addCodePoint('\n')).isTrue();
    this.test(sourceMapper, 3, 1);
    assertThat(sourceMapper.addCodePoint(' ')).isFalse();
    this.test(sourceMapper, 3, 2);
    assertThat(sourceMapper.addCodePoint('\t')).isFalse();
    this.test(sourceMapper, 3, 4);
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Helper
  // -------------------------------------------------------------------------------------------------------------------

  private void test(final SourceMapper sourceMapper, final int expectedLine, final int expectedColumn) {
    assertThat(sourceMapper.currentLine).isEqualTo(expectedLine);
    assertThat(sourceMapper.currentColumn).isEqualTo(expectedColumn);
  }
}
