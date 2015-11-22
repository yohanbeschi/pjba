package org.isk.pjba.unicode;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;

import org.isk.pjba.unicode.CodePoints.Charset;
import org.isk.pjba.unicode.CodePoints.Reader;
import org.isk.pjba.unicode.exception.UnicodeException;
import org.junit.Assert;
import org.junit.Test;

public class CodePointsTest {
  // -------------------------------------------------------------------------------------------------------------------
  // Code Points manipulations
  // -------------------------------------------------------------------------------------------------------------------

  @Test
  public void constructor_empty() {
    final CodePoints cp = new CodePoints();
    cp.add('a');
    assertThat(cp.toString()).isEqualTo("a");
  }

  @Test
  public void constructor_initialSize() {
    final CodePoints cp = new CodePoints(5);
    cp.add('a');
    cp.add('a');
    cp.add('a');
    cp.add('a');
    cp.add('a');
    cp.add("bbbbb");
    cp.add('c');
    cp.add('c');
    cp.add('c');
    cp.add('c');
    cp.add('c');
    assertThat(cp.toString()).isEqualTo("aaaaabbbbbccccc");
  }

  @Test
  public void constructor_initialCapacity_negative() {
    try {
      new CodePoints(-1);
      Assert.fail();
    } catch (final UnicodeException e) {
      assertThat(e.getMessage()).isEqualTo("The size of the buffer can't be 0 or less.");
    }
  }

  @Test
  public void constructor_initialCapacity_zero() {
    try {
      new CodePoints(0);
      Assert.fail();
    } catch (final UnicodeException e) {
      assertThat(e.getMessage()).isEqualTo("The size of the buffer can't be 0 or less.");
    }
  }

  @Test
  public void constructor_codepoints() {
    final CodePoints cp = new CodePoints(UnicodeTestData.CODEPOINTS);
    assertThat(cp.toArray()).isEqualTo(UnicodeTestData.CODEPOINTS);
  }

  @Test
  public void isEmpty() {
    final CodePoints cp = new CodePoints(2);
    assertThat(cp.isEmpty()).isTrue();
    cp.add('a');
    assertThat(cp.isEmpty()).isFalse();
  }

  @Test
  public void length() {
    final CodePoints cp = this.simpleAsciiString();
    assertThat(cp.length()).isEqualTo(5);
    assertThat(cp.toString()).isEqualTo("abcde");
    assertThat(cp.toString()).isEqualTo("abcde");
    assertThat(cp.length()).isEqualTo(5);
  }

  @Test
  public void length_empty() {
    final CodePoints cp = new CodePoints(10);
    assertThat(cp.length()).isEqualTo(0);
  }

  @Test
  public void setLength() {
    final CodePoints cp = this.simpleAsciiString();
    assertThat(cp.length()).isEqualTo(5);
    cp.setLength(4);
    assertThat(cp.length()).isEqualTo(4);
    assertThat(cp.toString()).isEqualTo("abcd");
  }

  @Test
  public void setLength_sameSize() {
    final CodePoints cp = this.simpleAsciiString();
    assertThat(cp.length()).isEqualTo(5);
    cp.setLength(5);
    assertThat(cp.length()).isEqualTo(5);
    assertThat(cp.toString()).isEqualTo("abcde");
  }

  @Test
  public void setLength_tooSmall() {
    final CodePoints cp = this.simpleAsciiString();
    try {
      cp.setLength(-1);
      Assert.fail();
    } catch (final UnicodeException e) {
      Assert.assertEquals(
          "The new length is outside of range (-1). It should be greater than or equal to 0 and less than or equal to the current length.",
          e.getMessage());
    }
  }

  @Test
  public void setLength_tooBig() {
    final CodePoints cp = this.simpleAsciiString();
    try {
      cp.setLength(6);
      Assert.fail();
    } catch (final UnicodeException e) {
      Assert.assertEquals(
          "The new length is outside of range (6). It should be greater than or equal to 0 and less than or equal to the current length.",
          e.getMessage());
    }
  }

  @Test
  public void remove() {
    final CodePoints cp = this.simpleAsciiString();
    cp.remove();
    assertThat(cp.length()).isEqualTo(4);
    assertThat(cp.toString()).isEqualTo("abcd");
  }

  @Test
  public void remove_empty() {
    final CodePoints cp = new CodePoints();
    cp.remove();
    assertThat(cp.length()).isEqualTo(0);
    assertThat(cp.toString()).isEqualTo("");
  }

  @Test
  public void getLastAndRemove() {
    final CodePoints cp = this.simpleAsciiString();
    final int last = cp.getLastAndRemove();
    assertThat(last).isEqualTo('e');
    assertThat(cp.length()).isEqualTo(4);
    assertThat(cp.toString()).isEqualTo("abcd");
  }

  @Test
  public void getLastAndRemove_empty() {
    final CodePoints cp = new CodePoints();
    final int last = cp.getLastAndRemove();
    assertThat(last).isEqualTo(-1);
    assertThat(cp.length()).isEqualTo(0);
    assertThat(cp.toString()).isEqualTo("");
  }

  @Test
  public void clear() {
    final CodePoints cp = this.simpleAsciiString();
    cp.clear();
    assertThat(cp.length()).isEqualTo(0);
    assertThat(cp.toString()).isEqualTo("");
  }

  @Test
  public void setLengthAndAdd() {
    final CodePoints cp = this.simpleAsciiString();
    cp.setLength(4);
    cp.add('z');
    assertThat(cp.length()).isEqualTo(5);
    assertThat(cp.toString()).isEqualTo("abcdz");
  }

  @Test
  public void addString() {
    final CodePoints cp = new CodePoints();
    cp.add("Привет мир по-русски");
    assertThat(cp.toString()).isEqualTo("Привет мир по-русски");
  }

  @Test
  public void addString_multiplanes_unicodeChars() {
    final CodePoints cp = new CodePoints();
    cp.add(UnicodeTestData.CODEPOINTS_AS_STRING);
    assertThat(cp.toString()).isEqualTo(UnicodeTestData.CODEPOINTS_AS_STRING);
  }

  @Test
  public void addString_multiplanes_realChars_1() {
    final CodePoints cp = new CodePoints();
    cp.add("aनि亜𐂃");
    assertThat(cp.toString()).isEqualTo("aनि亜𐂃");
  }

  @Test
  public void addString_multiplanes_realChars_2() {
    final CodePoints cp = new CodePoints();
    cp.add("aनि亜𐂃");
    assertThat(cp.toString()).isEqualTo(UnicodeTestData.CODEPOINTS_AS_STRING);
  }

  @Test
  public void addString_multiplanes_realChars_3() {
    final CodePoints cp = new CodePoints();
    cp.add(UnicodeTestData.CODEPOINTS_AS_STRING);
    assertThat(cp.toString()).isEqualTo("aनि亜𐂃");
  }

  @Test
  public void addCodePoint_outsideBMP() {
    final CodePoints cp = new CodePoints();
    for (final int codePoint : UnicodeTestData.CODEPOINTS) {
      cp.add(codePoint);
    }

    final byte[] stringAsByteArray = cp.toString().getBytes(java.nio.charset.Charset.forName("UTF-16BE"));
    assertThat(stringAsByteArray).isEqualTo(UnicodeTestData.UTF16_BE_NOBOM_BYTEARRAY);
  }

  @Test
  public void addCodePoints_outsideBMP() {
    final CodePoints cp = new CodePoints();
    cp.add(UnicodeTestData.CODEPOINTS);
    assertThat(cp.toString()).isEqualTo(UnicodeTestData.CODEPOINTS_AS_STRING);
  }

  @Test
  public void at() {
    final CodePoints cp = this.simpleAsciiString();
    assertThat(cp.at(2)).isEqualTo('c');
  }

  @Test
  public void at_begining() {
    final CodePoints cp = this.simpleAsciiString();
    assertThat(cp.at(0)).isEqualTo('a');
  }

  @Test
  public void at_end() {
    final CodePoints cp = this.simpleAsciiString();
    assertThat(cp.at(4)).isEqualTo('e');
  }

  @Test
  public void at_tooSmall() {
    final CodePoints cp = this.simpleAsciiString();
    try {
      cp.at(-1);
      Assert.fail();
    } catch (final UnicodeException e) {
      Assert.assertEquals(
          "Index outside of range (-1). It should be greater than or equal to 0 and less than the current length.",
          e.getMessage());
    }
  }

  @Test
  public void at_tooBig() {
    final CodePoints cp = this.simpleAsciiString();
    try {
      cp.at(5);
      Assert.fail();
    } catch (final UnicodeException e) {
      Assert.assertEquals(
          "Index outside of range (5). It should be greater than or equal to 0 and less than the current length.",
          e.getMessage());
    }
  }

  @Test
  public void codepoints() {
    final CodePoints cp = new CodePoints(5);
    cp.add(UnicodeTestData.CODEPOINTS_AS_STRING);
    assertThat(cp.toArray()).isEqualTo(UnicodeTestData.CODEPOINTS);
  }

  @Test
  public void toString_nobom() {
    final CodePoints cp = this.simpleAsciiString();
    assertThat(cp.toString()).isEqualTo("abcde");
  }

  @Test
  public void toString_bom() {
    final CodePoints cp = new CodePoints();
    cp.add(CodePoints.BOM_CODEPOINT);
    cp.add('a');
    cp.add('z');
    assertThat(cp.toString()).isEqualTo("az");
  }

  @Test
  public void reset() {
    final CodePoints cp = this.simpleAsciiString();
    assertThat(cp.toStringAndReset()).isEqualTo("abcde");
    assertThat(cp.length()).isEqualTo(0);
    assertThat(cp.toString()).isEqualTo("");
  }

  @Test
  public void resetAndAdd() {
    final CodePoints cp = this.simpleAsciiString();
    assertThat(cp.toStringAndReset()).isEqualTo("abcde");
    cp.add('z');
    assertThat(cp.length()).isEqualTo(1);
    assertThat(cp.toString()).isEqualTo("z");
  }

  private CodePoints simpleAsciiString() {
    final CodePoints cp = new CodePoints();
    cp.add('a');
    cp.add('b');
    cp.add('c');
    cp.add('d');
    cp.add('e');
    return cp;
  }

  // -------------------------------------------------------------------------------------------------------------------
  // this.toUtf()
  // -------------------------------------------------------------------------------------------------------------------

  @Test
  public void toUtf_nobom() {
    final CodePoints cp = new CodePoints();
    cp.add('a');
    cp.add('z');
    assertThat(cp.toUtf(Charset.UTF8)).isEqualTo(new byte[] { 0x61, 0x7A });
  }

  @Test
  public void toUtf_bom() {
    final CodePoints cp = new CodePoints();
    cp.add(CodePoints.BOM_CODEPOINT);
    cp.add('a');
    cp.add('z');
    assertThat(cp.toUtf(Charset.UTF8)).isEqualTo(new byte[] { 0x61, 0x7A });
  }

  // -------------------------------------------------------------------------------------------------------------------
  // CodePoints.toCodePoints
  // -------------------------------------------------------------------------------------------------------------------

  @Test
  public void utf8ToCodePoints_noBOM() {
    final int[] codePoints = CodePoints.toCodePoints(Charset.UTF8, UnicodeTestData.UTF8_NOBOM_BYTEARRAY);
    assertThat(codePoints).isEqualTo(UnicodeTestData.CODEPOINTS);
  }

  @Test
  public void utf8ToCodePoints_wBOM() {
    final int[] codePoints = CodePoints.toCodePoints(Charset.UTF8BOM, UnicodeTestData.UTF8_BOM_BYTEARRAY);
    assertThat(codePoints).isEqualTo(UnicodeTestData.CODEPOINTS);
  }

  @Test
  public void utf16BEToCodePoints_noBOM() {
    try {
      CodePoints.toCodePoints(Charset.UTF16BE, UnicodeTestData.UTF16_BE_NOBOM_BYTEARRAY);
      Assert.fail();
    } catch (final UnicodeException e) {
      assertThat(e.getMessage()).isEqualTo("Wrong UTF-16-BE BOM. Expected 0xFE (byte 0).");
    }
  }

  @Test
  public void utf16BEToCodePoints() {
    final int[] codePoints = CodePoints.toCodePoints(Charset.UTF16BE, UnicodeTestData.UTF16_BE_BOM_BYTEARRAY);
    assertThat(codePoints).isEqualTo(UnicodeTestData.CODEPOINTS);
  }

  @Test
  public void utf16LEToCodePoints() {
    final int[] codePoints = CodePoints.toCodePoints(Charset.UTF16LE, UnicodeTestData.UTF16_LE_BOM_BYTEARRAY);
    assertThat(codePoints).isEqualTo(UnicodeTestData.CODEPOINTS);
  }

  @Test
  public void utf32BEToCodePoints_noBOM() {
    try {
      CodePoints.toCodePoints(Charset.UTF32BE, UnicodeTestData.UTF32_BE_NOBOM_BYTEARRAY);
      Assert.fail();
    } catch (final UnicodeException e) {
      assertThat(e.getMessage()).isEqualTo("Wrong UTF-32-BE BOM. Expected 0xFE (byte 2).");
    }
  }

  @Test
  public void utf32BEToCodePoints() {
    final int[] codePoints = CodePoints.toCodePoints(Charset.UTF32BE, UnicodeTestData.UTF32_BE_BOM_BYTEARRAY);
    assertThat(codePoints).isEqualTo(UnicodeTestData.CODEPOINTS);
  }

  @Test
  public void utf32LEToCodePoints() {
    final int[] codePoints = CodePoints.toCodePoints(Charset.UTF32LE, UnicodeTestData.UTF32_LE_BOM_BYTEARRAY);
    assertThat(codePoints).isEqualTo(UnicodeTestData.CODEPOINTS);
  }

  // -------------------------------------------------------------------------------------------------------------------
  // CodePoints.toUtf
  // -------------------------------------------------------------------------------------------------------------------

  @Test
  public void codePointsToUtf8_noBOM() {
    final byte[] utf8 = CodePoints.toUtf(UnicodeTestData.CODEPOINTS, Charset.UTF8);
    assertThat(utf8).isEqualTo(UnicodeTestData.UTF8_NOBOM_BYTEARRAY);
  }

  @Test
  public void codePointsToUtf8_wBOM() {
    final byte[] utf8 = CodePoints.toUtf(UnicodeTestData.CODEPOINTS, Charset.UTF8BOM);
    assertThat(utf8).isEqualTo(UnicodeTestData.UTF8_BOM_BYTEARRAY);
  }

  @Test
  public void codePointsToUtf16BE() {
    final byte[] utf16 = CodePoints.toUtf(UnicodeTestData.CODEPOINTS, Charset.UTF16BE);
    assertThat(utf16).isEqualTo(UnicodeTestData.UTF16_BE_BOM_BYTEARRAY);
  }

  @Test
  public void codePointsToUtf16LE() {
    final byte[] utf16 = CodePoints.toUtf(UnicodeTestData.CODEPOINTS, Charset.UTF16LE);
    assertThat(utf16).isEqualTo(UnicodeTestData.UTF16_LE_BOM_BYTEARRAY);
  }

  @Test
  public void codePointsToUtf32BE() {
    final byte[] utf32 = CodePoints.toUtf(UnicodeTestData.CODEPOINTS, Charset.UTF32BE);
    assertThat(utf32).isEqualTo(UnicodeTestData.UTF32_BE_BOM_BYTEARRAY);
  }

  @Test
  public void codePointsToUtf32LE() {
    final byte[] utf32 = CodePoints.toUtf(UnicodeTestData.CODEPOINTS, Charset.UTF32LE);
    assertThat(utf32).isEqualTo(UnicodeTestData.UTF32_LE_BOM_BYTEARRAY);
  }

  // -------------------------------------------------------------------------------------------------------------------
  // CodePoints.readBom
  // -------------------------------------------------------------------------------------------------------------------

  @Test
  public void readBom_utf8() {
    CodePoints.readBom(Charset.UTF8, new UnicodeInputStream(new byte[] { 0x61 }));
  }

  @Test
  public void readBom_utf8bom() {
    CodePoints.readBom(Charset.UTF8BOM, new UnicodeInputStream(CodePoints.UTF8_BOM));
  }

  @Test
  public void readBom_utf16be() {
    CodePoints.readBom(Charset.UTF16BE, new UnicodeInputStream(CodePoints.UTF16BE_BOM));
  }

  @Test
  public void readBom_utf16le() {
    CodePoints.readBom(Charset.UTF16LE, new UnicodeInputStream(CodePoints.UTF16LE_BOM));
  }

  @Test
  public void readBom_utf32be() {
    CodePoints.readBom(Charset.UTF32BE, new UnicodeInputStream(CodePoints.UTF32BE_BOM));
  }

  @Test
  public void readBom_utf32le() {
    CodePoints.readBom(Charset.UTF32LE, new UnicodeInputStream(CodePoints.UTF32LE_BOM));
  }

  // -------------------------------------------------------------------------------------------------------------------
  // CodePoints.initByteArray
  // -------------------------------------------------------------------------------------------------------------------

  @Test
  public void initByteArray_utf8() {

  }

  @Test
  public void initByteArray_utf8bom() {
    final ByteArrayOutputStream out = CodePoints.initByteArray(Charset.UTF8BOM);
    assertThat(out.toByteArray()).isEqualTo(CodePoints.UTF8_BOM);
  }

  @Test
  public void initByteArray_utf16be() {
    final ByteArrayOutputStream out = CodePoints.initByteArray(Charset.UTF16BE);
    assertThat(out.toByteArray()).isEqualTo(CodePoints.UTF16BE_BOM);
  }

  @Test
  public void initByteArray_utf16le() {
    final ByteArrayOutputStream out = CodePoints.initByteArray(Charset.UTF16LE);
    assertThat(out.toByteArray()).isEqualTo(CodePoints.UTF16LE_BOM);
  }

  @Test
  public void initByteArray_utf32be() {
    final ByteArrayOutputStream out = CodePoints.initByteArray(Charset.UTF32BE);
    assertThat(out.toByteArray()).isEqualTo(CodePoints.UTF32BE_BOM);
  }

  @Test
  public void initByteArray_utf32le() {
    final ByteArrayOutputStream out = CodePoints.initByteArray(Charset.UTF32LE);
    assertThat(out.toByteArray()).isEqualTo(CodePoints.UTF32LE_BOM);
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Surrogates
  // -------------------------------------------------------------------------------------------------------------------

  @Test
  public void surrogate_beforeFirstHigh() {
    assertThat(CodePoints.isSurrogate(0xD7FF)).isFalse();
  }

  @Test
  public void surrogate_firstHigh() {
    assertThat(CodePoints.isSurrogate(0xD800)).isTrue();
  }

  @Test
  public void highSurrogate_firstHigh() {
    assertThat(CodePoints.isHighSurrogate(0xD800)).isTrue();
  }

  @Test
  public void surrogate_lastHigh() {
    assertThat(CodePoints.isSurrogate(0xDBFF)).isTrue();
  }

  @Test
  public void highSurrogate_lastHigh() {
    assertThat(CodePoints.isHighSurrogate(0xDBFF)).isTrue();
  }

  @Test
  public void surrogate_firstLow() {
    assertThat(CodePoints.isSurrogate(0xDC00)).isTrue();
  }

  @Test
  public void lowSurrogate_firstLow() {
    assertThat(CodePoints.isLowSurrogate(0xDC00)).isTrue();
  }

  @Test
  public void surrogate_lastLow() {
    assertThat(CodePoints.isSurrogate(0xDFFF)).isTrue();
  }

  @Test
  public void lowSurrogate_lastLow() {
    assertThat(CodePoints.isLowSurrogate(0xDFFF)).isTrue();
  }

  @Test
  public void surrogate_afterLastLow() {
    assertThat(CodePoints.isSurrogate(0xE000)).isFalse();
  }

  // -------------------------------------------------------------------------------------------------------------------
  // CodePoints.isNoncharacter
  // -------------------------------------------------------------------------------------------------------------------

  @Test
  public void isNoncharacter() {
    int i = 0;
    int count = 0;
    for (i = 0; i < CodePoints.UNICODE_CODESPACE_SIZE; i++) {
      if (CodePoints.isNoncharacter(i)) {
        count++;
      }
    }

    assertThat(count).isEqualTo(CodePoints.NUMBER_OF_NONCHARACTERS);
  }

  // -------------------------------------------------------------------------------------------------------------------
  // CodePoints.isInPrivateUseArea
  // -------------------------------------------------------------------------------------------------------------------

  @Test
  public void isInPrivateUseArea() {
    int i = 0;
    int count = 0;
    for (i = 0; i < 0xFFFFFF; i++) {
      if (CodePoints.isInPrivateUseArea(i)) {
        count++;
      }
    }

    assertThat(count).isEqualTo(CodePoints.NUMBER_OF_CODEPOINTS_IN_PRIVATE_USE_AREA);
  }

  // -------------------------------------------------------------------------------------------------------------------
  // CodePoints.isCodePoint
  // -------------------------------------------------------------------------------------------------------------------

  @Test
  public void isCodePoint() {
    int i = 0;
    int count = 0;
    for (i = 0; i < 0xFFFFFF; i++) {
      if (CodePoints.isCodePoint(i)) {
        count++;
      }
    }

    assertThat(count).isEqualTo(CodePoints.NUMBER_OF_VALID_CODEPOINTS);
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Reader
  // -------------------------------------------------------------------------------------------------------------------

  @Test
  public void read() {
    try (final Reader reader = new Reader(Charset.UTF8, new UnicodeInputStream(UnicodeTestData.UTF8_NOBOM_BYTEARRAY))) {
      assertThat(reader.hasNext()).isTrue();
      assertThat(reader.read()).isEqualTo(UnicodeTestData.CODEPOINTS[0]);
      assertThat(reader.read()).isEqualTo(UnicodeTestData.CODEPOINTS[1]);
      assertThat(reader.read()).isEqualTo(UnicodeTestData.CODEPOINTS[2]);
      assertThat(reader.read()).isEqualTo(UnicodeTestData.CODEPOINTS[3]);
      assertThat(reader.read()).isEqualTo(UnicodeTestData.CODEPOINTS[4]);
      assertThat(reader.hasNext()).isFalse();
    }
  }

  @Test
  public void unread() {
    try (final Reader reader = new Reader(Charset.UTF8, new UnicodeInputStream(UnicodeTestData.UTF8_NOBOM_BYTEARRAY))) {
      assertThat(reader.hasNext()).isTrue();
      final int char0 = reader.read();
      assertThat(char0).isEqualTo(UnicodeTestData.CODEPOINTS[0]);
      reader.unread(char0);
      assertThat(reader.read()).isEqualTo(UnicodeTestData.CODEPOINTS[0]);
      final int char1 = reader.read();
      assertThat(char1).isEqualTo(UnicodeTestData.CODEPOINTS[1]);
      final int char2 = reader.read();
      assertThat(char2).isEqualTo(UnicodeTestData.CODEPOINTS[2]);
      final int char3 = reader.read();
      assertThat(char3).isEqualTo(UnicodeTestData.CODEPOINTS[3]);
      final int char4 = reader.read();
      assertThat(char4).isEqualTo(UnicodeTestData.CODEPOINTS[4]);
      reader.unread(char4);
      reader.unread(char3);
      reader.unread(char2);
      reader.unread(char1);
      reader.unread(char0);
      assertThat(reader.read()).isEqualTo(UnicodeTestData.CODEPOINTS[0]);
      assertThat(reader.read()).isEqualTo(UnicodeTestData.CODEPOINTS[1]);
      assertThat(reader.read()).isEqualTo(UnicodeTestData.CODEPOINTS[2]);
      assertThat(reader.read()).isEqualTo(UnicodeTestData.CODEPOINTS[3]);
      assertThat(reader.read()).isEqualTo(UnicodeTestData.CODEPOINTS[4]);
      assertThat(reader.hasNext()).isFalse();
    }
  }

  @Test
  public void peek() {
    try (final Reader reader = new Reader(Charset.UTF8, new UnicodeInputStream(UnicodeTestData.UTF8_NOBOM_BYTEARRAY))) {
      final int char0 = reader.peek();
      assertThat(char0).isEqualTo(UnicodeTestData.CODEPOINTS[0]);
      assertThat(reader.peek()).isEqualTo(char0);
      assertThat(reader.read()).isEqualTo(char0);
      assertThat(reader.read()).isEqualTo(UnicodeTestData.CODEPOINTS[1]);
    }
  }

  @Test
  public void read_afterEnd() {
    try (final Reader reader = new Reader(Charset.UTF8, new UnicodeInputStream(UnicodeTestData.UTF8_NOBOM_BYTEARRAY))) {
      reader.read();
      reader.read();
      reader.read();
      reader.read();
      reader.read();
      reader.read();
    }
  }

  @Test
  public void unread_beforeStart() {
    try (final Reader reader = new Reader(Charset.UTF8, new UnicodeInputStream(UnicodeTestData.UTF8_NOBOM_BYTEARRAY))) {
      reader.unread('A');
      assertThat(reader.read()).isEqualTo('A');
    }
  }
}