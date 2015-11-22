package org.isk.pjba.unicode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.IntStream;

import org.isk.pjba.unicode.exception.UnicodeException;
import org.junit.Test;

public class UnicodeInputStreamTest {

  @Test
  public void read_constructorBytes() {
    final UnicodeInputStream stream = new UnicodeInputStream(UnicodeTestData.UTF16_BE_BOM_BYTEARRAY);
    this.assertUtf16beNoBom(stream);
  }

  @Test
  public void constructorBytes_null() {
    final byte[] bytes = null;
    assertThatThrownBy(() -> new UnicodeInputStream(bytes)).isInstanceOf(UnicodeException.class) //
        .hasMessageContaining("Impossible to instantiate an UnicodeInputStream, there is no bytes to read.");
  }

  @Test
  public void constructorBytes_empty() {
    final byte[] bytes = new byte[0];
    assertThatThrownBy(() -> new UnicodeInputStream(bytes)).isInstanceOf(UnicodeException.class) //
        .hasMessageContaining("Impossible to instantiate an UnicodeInputStream, there is no bytes to read.");
  }

  @Test
  public void read_constructorInputStream() {
    final ByteArrayInputStream inputStream = new ByteArrayInputStream(UnicodeTestData.UTF16_BE_BOM_BYTEARRAY);
    final UnicodeInputStream stream = new UnicodeInputStream(inputStream);
    this.assertUtf16beNoBom(stream);
  }

  @Test
  public void constructorInputStream_null() {
    final InputStream inputStream = null;
    assertThatThrownBy(() -> new UnicodeInputStream(inputStream)).isInstanceOf(UnicodeException.class) //
        .hasMessageContaining("Impossible to instantiate an UnicodeInputStream, the InputStream is null.");
  }

  @Test(expected = NullPointerException.class)
  public void constructorInputStream_NullByteArray() {
    try (final UnicodeInputStream inputStream = new UnicodeInputStream(new ByteArrayInputStream(null))) {

    }
  }

  @Test
  public void constructorInputStream_EmptyByteArray() {
    final byte[] bytes = new byte[0];
    final UnicodeInputStream stream = new UnicodeInputStream(new ByteArrayInputStream(bytes));
    assertThat(stream).isNotNull();
  }

  @Test
  public void hasNext() {
    final byte[] bytes = { 'a', 'b' };
    try (final UnicodeInputStream stream = new UnicodeInputStream(bytes)) {
      assertThat(stream.hasNext()).isTrue();
      stream.read();
      assertThat(stream.hasNext()).isTrue();
      stream.read();
      assertThat(stream.hasNext()).isFalse();
      assertThat(stream.hasNext()).isFalse();
    }
  }

  @Test
  public void read_TooFar() {
    final byte[] bytes = { 'a', 'b' };

    try (final UnicodeInputStream stream = new UnicodeInputStream(bytes);) {
      stream.read();
      stream.read();

      assertThatThrownBy(() -> stream.read()).isInstanceOf(UnicodeException.class) //
          .hasMessageContaining("This UnicodeInputStream has been read completely!");
    }
  }

  @Test
  public void unread() {
    final byte[] bytes = { 'a', 'b' };

    try (final UnicodeInputStream stream = new UnicodeInputStream(bytes)) {
      int b = stream.read();
      stream.unread(b);
      b = stream.read();
      assertThat(b).isEqualTo('a');

      b = stream.read();
      stream.unread(b);
      b = stream.read();
      assertThat(b).isEqualTo('b');
    }
  }

  @Test
  public void unread_pushBackBufferFull() {
    final byte[] bytes = new byte[128];
    Arrays.fill(bytes, (byte) 'a');

    try (final UnicodeInputStream stream = new UnicodeInputStream(bytes)) {
      IntStream.range(1, 129).forEach(e -> stream.unread('a'));

      assertThatThrownBy(() -> stream.unread('a')).isInstanceOf(UnicodeException.class) //
          .hasMessageContaining("Something went wrong while unreading this UnicodeInputStream!");
    }
  }

  private void assertUtf16beNoBom(final UnicodeInputStream stream) {
    assertThat(stream.read()).isEqualTo(0xFE);
    assertThat(stream.read()).isEqualTo(0xFF);
    assertThat(stream.read()).isEqualTo(0x00);
    assertThat(stream.read()).isEqualTo(0x61);
    assertThat(stream.read()).isEqualTo(0x09);
    assertThat(stream.read()).isEqualTo(0x28);
    assertThat(stream.read()).isEqualTo(0x09);
    assertThat(stream.read()).isEqualTo(0x3F);
    assertThat(stream.read()).isEqualTo(0x4E);
    assertThat(stream.read()).isEqualTo(0x9C);
    assertThat(stream.read()).isEqualTo(0xD8);
    assertThat(stream.read()).isEqualTo(0x00);
    assertThat(stream.read()).isEqualTo(0xDC);
    assertThat(stream.read()).isEqualTo(0x83);
  }
}
