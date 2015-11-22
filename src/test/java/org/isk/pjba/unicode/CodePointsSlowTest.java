package org.isk.pjba.unicode;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.isk.pjba.unicode.CodePoints.Charset;
import org.isk.pjba.unicode.UnicodeTestData.Line;
import org.junit.BeforeClass;
import org.junit.Test;

public class CodePointsSlowTest {
  private static List<Line> ALL_CODEPOINTS_AND_UTF = new ArrayList<>(CodePoints.NUMBER_OF_VALID_CODEPOINTS);
  private static int[] ALL_CODEPOINTS = new int[CodePoints.NUMBER_OF_VALID_CODEPOINTS];
  private static byte[] ALL_UTF8;
  private static byte[] ALL_UTF8BOM;
  private static byte[] ALL_UTF16BE;
  private static byte[] ALL_UTF16LE;
  private static byte[] ALL_UTF32BE;
  private static byte[] ALL_UTF32LE;

  /**
   * Create all valid Code Points - Remember that surrogates are not valid Code Points as a high or low surrogate can't
   * stand on its own without it's counterpart (respectively low and high), it's the reason we call them
   * "a surrogate pair". Moreover, surrogates have only been created (for the UTF-16 encoding to support Code Points
   * outside of the BMP) to avoid any collision between a real code point and a 16-bit code unit (where two 16-bit code
   * unit will constitute a real Code Point).
   *
   * @throws Exception
   *           we don't care
   */
  @BeforeClass
  public static void setUpClass() throws Exception {
    // final long start = System.nanoTime();

    final ByteArrayOutputStream _utf8 = CodePoints.initByteArray(Charset.UTF8);
    final ByteArrayOutputStream _utf8bom = CodePoints.initByteArray(Charset.UTF8BOM);
    final ByteArrayOutputStream _utf16be = CodePoints.initByteArray(Charset.UTF16BE);
    final ByteArrayOutputStream _utf16le = CodePoints.initByteArray(Charset.UTF16LE);
    final ByteArrayOutputStream _utf32be = CodePoints.initByteArray(Charset.UTF32BE);
    final ByteArrayOutputStream _utf32le = CodePoints.initByteArray(Charset.UTF32LE);

    final java.nio.charset.Charset charsetUTF32BE = java.nio.charset.Charset.forName("UTF-32BE");
    final java.nio.charset.Charset charsetUTF32LE = java.nio.charset.Charset.forName("UTF-32LE");

    int count = 0;
    int cpCounter = 0;
    for (int i = 0; i <= 0x10FFFF; i++) {
      if (i <= 0xD7FF || i >= 0xE000) {
        final String string = new String(new int[] { count }, 0, 1);
        final byte[] utf8 = string.getBytes(StandardCharsets.UTF_8);
        final byte[] utf16be = string.getBytes(StandardCharsets.UTF_16BE);
        final byte[] utf16le = string.getBytes(StandardCharsets.UTF_16LE);
        final byte[] utf32be = string.getBytes(charsetUTF32BE);
        final byte[] utf32le = string.getBytes(charsetUTF32LE);
        final Line l = new Line(count, utf8, utf16be, utf16le, utf32be, utf32le);

        CodePointsSlowTest.ALL_CODEPOINTS_AND_UTF.add(l);
        CodePointsSlowTest.ALL_CODEPOINTS[cpCounter++] = l.codePoint;

        _utf8.write(l.utf8);
        _utf8bom.write(l.utf8);
        _utf16be.write(l.utf16be);
        _utf16le.write(l.utf16le);
        _utf32be.write(l.utf32be);
        _utf32le.write(l.utf32le);
      }

      count++;
    }

    CodePointsSlowTest.ALL_UTF8 = _utf8.toByteArray();
    CodePointsSlowTest.ALL_UTF8BOM = _utf8bom.toByteArray();
    CodePointsSlowTest.ALL_UTF16BE = _utf16be.toByteArray();
    CodePointsSlowTest.ALL_UTF16LE = _utf16le.toByteArray();
    CodePointsSlowTest.ALL_UTF32BE = _utf32be.toByteArray();
    CodePointsSlowTest.ALL_UTF32LE = _utf32le.toByteArray();

    // System.out.println("Init time: " + (System.nanoTime() - start) / 1_000_000_000 + "s");
  }

  // -------------------------------------------------------------------------------------------------------------------
  // CodePoints.utf8ToCodePoint
  // -------------------------------------------------------------------------------------------------------------------

  @Test
  public void utf8ToCodePoint() {
    this.testUtfToCodePoint(e -> e.codePoint == CodePoints.utf8ToCodePoint(new UnicodeInputStream(e.utf8)));
  }

  @Test
  public void utf16beToCodePoint() {
    this.testUtfToCodePoint(e -> e.codePoint == CodePoints.utf16beToCodePoint(new UnicodeInputStream(e.utf16be)));
  }

  @Test
  public void utf16leToCodePoint() {
    this.testUtfToCodePoint(e -> e.codePoint == CodePoints.utf16leToCodePoint(new UnicodeInputStream(e.utf16le)));
  }

  @Test
  public void utf32beToCodePoint() {
    this.testUtfToCodePoint(e -> e.codePoint == CodePoints.utf32beToCodePoint(new UnicodeInputStream(e.utf32be)));
  }

  @Test
  public void utf32leToCodePoint() {
    this.testUtfToCodePoint(e -> e.codePoint == CodePoints.utf32leToCodePoint(new UnicodeInputStream(e.utf32le)));
  }

  // -------------------------------------------------------------------------------------------------------------------
  // CodePoints.codePointToUtfX
  // -------------------------------------------------------------------------------------------------------------------

  @Test
  public void codePointToUtf8() {
    this.testUtfToCodePoint(e -> {
      final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(4);
      CodePoints.codePointToUtf8(e.codePoint, outputStream);
      return Arrays.equals(e.utf8, outputStream.toByteArray());
    });
  }

  @Test
  public void codePointToUtf16be() {
    this.testUtfToCodePoint(e -> {
      final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(4);
      CodePoints.codePointToUtf16be(e.codePoint, outputStream);
      return Arrays.equals(e.utf16be, outputStream.toByteArray());
    });
  }

  @Test
  public void codePointToUtf16le() {
    this.testUtfToCodePoint(e -> {
      final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(4);
      CodePoints.codePointToUtf16le(e.codePoint, outputStream);
      return Arrays.equals(e.utf16le, outputStream.toByteArray());
    });
  }

  @Test
  public void codePointToUtf32be() {
    this.testUtfToCodePoint(e -> {
      final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(4);
      CodePoints.codePointToUtf32be(e.codePoint, outputStream);
      return Arrays.equals(e.utf32be, outputStream.toByteArray());
    });
  }

  @Test
  public void codePointToUtf32le() {
    this.testUtfToCodePoint(e -> {
      final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(4);
      CodePoints.codePointToUtf32le(e.codePoint, outputStream);
      return Arrays.equals(e.utf32le, outputStream.toByteArray());
    });
  }

  // -------------------------------------------------------------------------------------------------------------------
  // CodePoints.utfToUtf
  // -------------------------------------------------------------------------------------------------------------------
  // TODO:

  //-------------------------------------------------------------------------------------------------------------------
  // CodePoints.characterUtfToUtf
  // -------------------------------------------------------------------------------------------------------------------

  @Test
  public void utf8ToUtf16be() {
    this.testUtfToCodePoint(e -> {
      final byte[] utf16be = CodePoints.characterUtfToUtf(e.utf8, Charset.UTF8, Charset.UTF16BE);
      return Arrays.equals(e.utf16be, utf16be);
    });
  }

  @Test
  public void utf8ToUtf16le() {
    this.testUtfToCodePoint(e -> {
      final byte[] utf16le = CodePoints.characterUtfToUtf(e.utf8, Charset.UTF8, Charset.UTF16LE);
      return Arrays.equals(e.utf16le, utf16le);
    });
  }

  @Test
  public void utf8ToUtf32be() {
    this.testUtfToCodePoint(e -> {
      final byte[] utf32be = CodePoints.characterUtfToUtf(e.utf8, Charset.UTF8, Charset.UTF32BE);
      return Arrays.equals(e.utf32be, utf32be);
    });
  }

  @Test
  public void utf8ToUtf32le() {
    this.testUtfToCodePoint(e -> {
      final byte[] utf32le = CodePoints.characterUtfToUtf(e.utf8, Charset.UTF8, Charset.UTF32LE);
      return Arrays.equals(e.utf32le, utf32le);
    });
  }

  // -------------------------------------------------------------------------------------------------------------------
  // CodePoints.this.toUtf
  // -------------------------------------------------------------------------------------------------------------------

  @Test
  public void codePointsToUtf8() {
    final CodePoints cp = new CodePoints(CodePointsSlowTest.ALL_CODEPOINTS);
    final byte[] utf8 = cp.toUtf(Charset.UTF8);
    assertThat(utf8).isEqualTo(CodePointsSlowTest.ALL_UTF8);
  }

  @Test
  public void codePointsToUtf16be() {
    final CodePoints cp = new CodePoints(CodePointsSlowTest.ALL_CODEPOINTS);
    final byte[] utf16be = cp.toUtf(Charset.UTF16BE);
    assertThat(utf16be).isEqualTo(CodePointsSlowTest.ALL_UTF16BE);
  }

  @Test
  public void codePointsToUtf16le() {
    final CodePoints cp = new CodePoints(CodePointsSlowTest.ALL_CODEPOINTS);
    final byte[] utf16le = cp.toUtf(Charset.UTF16LE);
    assertThat(utf16le).isEqualTo(CodePointsSlowTest.ALL_UTF16LE);
  }

  @Test
  public void codePointsToUtf32be() {
    final CodePoints cp = new CodePoints(CodePointsSlowTest.ALL_CODEPOINTS);
    final byte[] utf32be = cp.toUtf(Charset.UTF32BE);
    assertThat(utf32be).isEqualTo(CodePointsSlowTest.ALL_UTF32BE);
  }

  @Test
  public void codePointsToUtf32le() {
    final CodePoints cp = new CodePoints(CodePointsSlowTest.ALL_CODEPOINTS);
    final byte[] utf32le = cp.toUtf(Charset.UTF32LE);
    assertThat(utf32le).isEqualTo(CodePointsSlowTest.ALL_UTF32LE);
  }

  // -------------------------------------------------------------------------------------------------------------------
  // CodePoints.toCodePoints
  // -------------------------------------------------------------------------------------------------------------------

  @Test
  public void utf8ToCodePoints() {
    final int[] codePoints = CodePoints.toCodePoints(Charset.UTF8, CodePointsSlowTest.ALL_UTF8);
    assertThat(codePoints).isEqualTo(CodePointsSlowTest.ALL_CODEPOINTS);
  }

  @Test
  public void utf8BomToCodePoints() {
    final int[] codePoints = CodePoints.toCodePoints(Charset.UTF8BOM, CodePointsSlowTest.ALL_UTF8BOM);
    assertThat(codePoints).isEqualTo(CodePointsSlowTest.ALL_CODEPOINTS);
  }

  @Test
  public void utf16beToCodePoints() {
    final int[] codePoints = CodePoints.toCodePoints(Charset.UTF16BE, CodePointsSlowTest.ALL_UTF16BE);
    assertThat(codePoints).isEqualTo(CodePointsSlowTest.ALL_CODEPOINTS);
  }

  @Test
  public void utf16leToCodePoints() {
    final int[] codePoints = CodePoints.toCodePoints(Charset.UTF16LE, CodePointsSlowTest.ALL_UTF16LE);
    assertThat(codePoints).isEqualTo(CodePointsSlowTest.ALL_CODEPOINTS);
  }

  @Test
  public void utf32beToCodePoints() {
    final int[] codePoints = CodePoints.toCodePoints(Charset.UTF32BE, CodePointsSlowTest.ALL_UTF32BE);
    assertThat(codePoints).isEqualTo(CodePointsSlowTest.ALL_CODEPOINTS);
  }

  @Test
  public void utf32leToCodePoints() {
    final int[] codePoints = CodePoints.toCodePoints(Charset.UTF32LE, CodePointsSlowTest.ALL_UTF32LE);
    assertThat(codePoints).isEqualTo(CodePointsSlowTest.ALL_CODEPOINTS);
  }

  // -------------------------------------------------------------------------------------------------------------------
  // CodePoints.utfToUtf
  // -------------------------------------------------------------------------------------------------------------------

  @Test
  public void utf8ToUtf8_array() {
    final byte[] utf8 = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF8, Charset.UTF8, Charset.UTF8);
    assertThat(utf8).isEqualTo(CodePointsSlowTest.ALL_UTF8);
  }

  @Test
  public void utf8ToUtf8bom_array() {
    final byte[] utf8bom = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF8, Charset.UTF8, Charset.UTF8BOM);
    assertThat(utf8bom).isEqualTo(CodePointsSlowTest.ALL_UTF8BOM);
  }

  @Test
  public void utf8ToUtf16be_array() {
    final byte[] utf16be = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF8, Charset.UTF8, Charset.UTF16BE);
    assertThat(utf16be).isEqualTo(CodePointsSlowTest.ALL_UTF16BE);
  }

  @Test
  public void utf8ToUtf16le_array() {
    final byte[] utf16le = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF8, Charset.UTF8, Charset.UTF16LE);
    assertThat(utf16le).isEqualTo(CodePointsSlowTest.ALL_UTF16LE);
  }

  @Test
  public void utf8ToUtf32be_array() {
    final byte[] utf32be = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF8, Charset.UTF8, Charset.UTF32BE);
    assertThat(utf32be).isEqualTo(CodePointsSlowTest.ALL_UTF32BE);
  }

  @Test
  public void utf8ToUtf32le_array() {
    final byte[] utf32le = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF8, Charset.UTF8, Charset.UTF32LE);
    assertThat(utf32le).isEqualTo(CodePointsSlowTest.ALL_UTF32LE);
  }

  @Test
  public void utf8bomToUtf8_array() {
    final byte[] utf8 = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF8BOM, Charset.UTF8BOM, Charset.UTF8);
    assertThat(utf8).isEqualTo(CodePointsSlowTest.ALL_UTF8);
  }

  @Test
  public void utf8bomToUtf8bom_array() {
    final byte[] utf8bom = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF8BOM, Charset.UTF8BOM, Charset.UTF8BOM);
    assertThat(utf8bom).isEqualTo(CodePointsSlowTest.ALL_UTF8BOM);
  }

  @Test
  public void utf8bomToUtf16be_array() {
    final byte[] utf16be = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF8BOM, Charset.UTF8BOM, Charset.UTF16BE);
    assertThat(utf16be).isEqualTo(CodePointsSlowTest.ALL_UTF16BE);
  }

  @Test
  public void utf8bomToUtf16le_array() {
    final byte[] utf16le = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF8BOM, Charset.UTF8BOM, Charset.UTF16LE);
    assertThat(utf16le).isEqualTo(CodePointsSlowTest.ALL_UTF16LE);
  }

  @Test
  public void utf8bomToUtf32be_array() {
    final byte[] utf32be = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF8BOM, Charset.UTF8BOM, Charset.UTF32BE);
    assertThat(utf32be).isEqualTo(CodePointsSlowTest.ALL_UTF32BE);
  }

  @Test
  public void utf8bomToUtf32le_array() {
    final byte[] utf32le = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF8BOM, Charset.UTF8BOM, Charset.UTF32LE);
    assertThat(utf32le).isEqualTo(CodePointsSlowTest.ALL_UTF32LE);
  }

  @Test
  public void utf16beToUtf16be_array() {
    final byte[] utf16be = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF16BE, Charset.UTF16BE, Charset.UTF16BE);
    assertThat(utf16be).isEqualTo(CodePointsSlowTest.ALL_UTF16BE);
  }

  @Test
  public void utf16beToUtf16le_array() {
    final byte[] utf16le = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF16BE, Charset.UTF16BE, Charset.UTF16LE);
    assertThat(utf16le).isEqualTo(CodePointsSlowTest.ALL_UTF16LE);
  }

  @Test
  public void utf16beToUtf8_array() {
    final byte[] utf8 = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF16BE, Charset.UTF16BE, Charset.UTF8);
    assertThat(utf8).isEqualTo(CodePointsSlowTest.ALL_UTF8);
  }

  @Test
  public void utf16beToUtf8bom_array() {
    final byte[] utf8bom = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF16BE, Charset.UTF16BE, Charset.UTF8BOM);
    assertThat(utf8bom).isEqualTo(CodePointsSlowTest.ALL_UTF8BOM);
  }

  @Test
  public void utf16beToUtf32be_array() {
    final byte[] utf32be = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF16BE, Charset.UTF16BE, Charset.UTF32BE);
    assertThat(utf32be).isEqualTo(CodePointsSlowTest.ALL_UTF32BE);
  }

  @Test
  public void utf16beToUtf32le_array() {
    final byte[] utf32le = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF16BE, Charset.UTF16BE, Charset.UTF32LE);
    assertThat(utf32le).isEqualTo(CodePointsSlowTest.ALL_UTF32LE);
  }

  @Test
  public void utf16leToUtf16le_array() {
    final byte[] utf16le = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF16LE, Charset.UTF16LE, Charset.UTF16LE);
    assertThat(utf16le).isEqualTo(CodePointsSlowTest.ALL_UTF16LE);
  }

  @Test
  public void utf16leToUtf16be_array() {
    final byte[] utf16be = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF16LE, Charset.UTF16LE, Charset.UTF16BE);
    assertThat(utf16be).isEqualTo(CodePointsSlowTest.ALL_UTF16BE);
  }

  @Test
  public void utf16leToUtf8_array() {
    final byte[] utf8 = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF16LE, Charset.UTF16LE, Charset.UTF8);
    assertThat(utf8).isEqualTo(CodePointsSlowTest.ALL_UTF8);
  }

  @Test
  public void utf16leToUtf8bom_array() {
    final byte[] utf8bom = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF16LE, Charset.UTF16LE, Charset.UTF8BOM);
    assertThat(utf8bom).isEqualTo(CodePointsSlowTest.ALL_UTF8BOM);
  }

  @Test
  public void utf16leToUtf32be_array() {
    final byte[] utf32be = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF16LE, Charset.UTF16LE, Charset.UTF32BE);
    assertThat(utf32be).isEqualTo(CodePointsSlowTest.ALL_UTF32BE);
  }

  @Test
  public void utf16leToUtf32le_array() {
    final byte[] utf32le = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF16LE, Charset.UTF16LE, Charset.UTF32LE);
    assertThat(utf32le).isEqualTo(CodePointsSlowTest.ALL_UTF32LE);
  }

  @Test
  public void utf32beToUtf32be_array() {
    final byte[] utf32be = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF32BE, Charset.UTF32BE, Charset.UTF32BE);
    assertThat(utf32be).isEqualTo(CodePointsSlowTest.ALL_UTF32BE);
  }

  @Test
  public void utf32beToUtf32le_array() {
    final byte[] utf32le = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF32BE, Charset.UTF32BE, Charset.UTF32LE);
    assertThat(utf32le).isEqualTo(CodePointsSlowTest.ALL_UTF32LE);
  }

  @Test
  public void utf32beToUtf8_array() {
    final byte[] utf8 = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF32BE, Charset.UTF32BE, Charset.UTF8);
    assertThat(utf8).isEqualTo(CodePointsSlowTest.ALL_UTF8);
  }

  @Test
  public void utf32beToUtf8bom_array() {
    final byte[] utf8bom = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF32BE, Charset.UTF32BE, Charset.UTF8BOM);
    assertThat(utf8bom).isEqualTo(CodePointsSlowTest.ALL_UTF8BOM);
  }

  @Test
  public void utf32beToUtf16be_array() {
    final byte[] utf16be = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF32BE, Charset.UTF32BE, Charset.UTF16BE);
    assertThat(utf16be).isEqualTo(CodePointsSlowTest.ALL_UTF16BE);
  }

  @Test
  public void utf32beToUtf16le_array() {
    final byte[] utf16le = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF32BE, Charset.UTF32BE, Charset.UTF16LE);
    assertThat(utf16le).isEqualTo(CodePointsSlowTest.ALL_UTF16LE);
  }

  @Test
  public void utf32leToUtf32le_array() {
    final byte[] utf32le = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF32LE, Charset.UTF32LE, Charset.UTF32LE);
    assertThat(utf32le).isEqualTo(CodePointsSlowTest.ALL_UTF32LE);
  }

  @Test
  public void utf32leToUtf32be_array() {
    final byte[] utf32be = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF32LE, Charset.UTF32LE, Charset.UTF32BE);
    assertThat(utf32be).isEqualTo(CodePointsSlowTest.ALL_UTF32BE);
  }

  @Test
  public void utf32leToUtf8_array() {
    final byte[] utf8 = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF32LE, Charset.UTF32LE, Charset.UTF8);
    assertThat(utf8).isEqualTo(CodePointsSlowTest.ALL_UTF8);
  }

  @Test
  public void utf32leToUtf8bom_array() {
    final byte[] utf8bom = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF32LE, Charset.UTF32LE, Charset.UTF8BOM);
    assertThat(utf8bom).isEqualTo(CodePointsSlowTest.ALL_UTF8BOM);
  }

  @Test
  public void utf32leToUtf16be_array() {
    final byte[] utf16be = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF32LE, Charset.UTF32LE, Charset.UTF16BE);
    assertThat(utf16be).isEqualTo(CodePointsSlowTest.ALL_UTF16BE);
  }

  @Test
  public void utf32leToUtf16le_array() {
    final byte[] utf16le = CodePoints.utfToUtf(CodePointsSlowTest.ALL_UTF32LE, Charset.UTF32LE, Charset.UTF16LE);
    assertThat(utf16le).isEqualTo(CodePointsSlowTest.ALL_UTF16LE);
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Helpers
  // -------------------------------------------------------------------------------------------------------------------

  // Test all code points
  public void testUtfToCodePoint(final Predicate<Line> predicate) {
    final long count = CodePointsSlowTest.ALL_CODEPOINTS_AND_UTF.stream().parallel().filter(predicate).count();
    assertThat(count).isEqualTo(CodePoints.NUMBER_OF_VALID_CODEPOINTS);
  }
}
