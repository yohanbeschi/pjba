package org.isk.pjba.unicode;

import java.io.Serializable;

public class UnicodeTestData {
  final public static byte[] UTF8_NOBOM_BYTEARRAY = { //
      0x61, // U+0061
      (byte) 0xE0, (byte) 0xA4, (byte) 0xA8, // U+0928
      (byte) 0xE0, (byte) 0xA4, (byte) 0xBF, // U+093F
      (byte) 0xE4, (byte) 0xBA, (byte) 0x9C, // U+4E9C
      (byte) 0xF0, (byte) 0x90, (byte) 0x82, (byte) 0x83 // U+10083
  };

  final public static byte[] UTF8_BOM_BYTEARRAY = { //
      (byte) 0xEF, (byte) 0xBB, (byte) 0xBF, // BOM
      0x61, // U+0061
      (byte) 0xE0, (byte) 0xA4, (byte) 0xA8, // U+0928
      (byte) 0xE0, (byte) 0xA4, (byte) 0xBF, // U+093F
      (byte) 0xE4, (byte) 0xBA, (byte) 0x9C, // U+4E9C
      (byte) 0xF0, (byte) 0x90, (byte) 0x82, (byte) 0x83 // U+10083
  };

  final public static byte[] UTF16_BE_NOBOM_BYTEARRAY = { //
      00, 0x61, // U+0061
      0x09, 0x28, // U+0928
      0x09, 0x3F, // U+093F
      0x4E, (byte) 0x9C, // U+4E9C
      (byte) 0xD8, 00, (byte) 0xDC, (byte) 0x83 // U+10083
  };

  final public static byte[] UTF16_BE_BOM_BYTEARRAY = { //
      (byte) 0xFE, (byte) 0xFF, // BOM
      00, 0x61, // U+0061
      0x09, 0x28, // U+0928
      0x09, 0x3F, // U+093F
      0x4E, (byte) 0x9C, // U+4E9C
      (byte) 0xD8, 00, (byte) 0xDC, (byte) 0x83 // U+10083
  };

  final public static byte[] UTF16_LE_BOM_BYTEARRAY = { //
      (byte) 0xFF, (byte) 0xFE, // BOM
      0x61, 00, // U+0061
      0x28, 0x09, // U+0928
      0x3F, 0x09, // U+093F
      (byte) 0x9C, 0x4E, // U+4E9C
      00, (byte) 0xD8, (byte) 0x83, (byte) 0xDC // U+10083
  };

  final public static byte[] UTF32_BE_NOBOM_BYTEARRAY = { //
      00, 00, 00, 0x61, // U+0061
      00, 00, 0x09, 0x28, // U+0928
      00, 00, 0x09, 0x3F, // U+093F
      00, 00, 0x4E, (byte) 0x9C, // U+4E9C
      00, 0x01, 00, (byte) 0x83 // U+10083
  };

  final public static byte[] UTF32_BE_BOM_BYTEARRAY = { //
      00, 00, (byte) 0xFE, (byte) 0xFF, // BOM
      00, 00, 00, 0x61, // U+0061
      00, 00, 0x09, 0x28, // U+0928
      00, 00, 0x09, 0x3F, // U+093F
      00, 00, 0x4E, (byte) 0x9C, // U+4E9C
      00, 0x01, 00, (byte) 0x83 // U+10083
  };

  final public static byte[] UTF32_LE_BOM_BYTEARRAY = { //
      (byte) 0xFF, (byte) 0xFE, 00, 00, // BOM
      0x61, 00, 00, 00, // U+0061
      0x28, 0x09, 00, 00, // U+0928
      0x3F, 0x09, 00, 00, // U+093F
      (byte) 0x9C, 0x4E, 00, 00, // U+4E9C
      (byte) 0x83, 00, 0x01, 0 // U+10083
  };

  final public static int[] CODEPOINTS = { 0x0061, 0x0928, 0x093F, 0x4E9C, 0x10083 };

  final public static String CODEPOINTS_AS_STRING = new String(UnicodeTestData.CODEPOINTS, 0, 5);

  final public static String THREE_LINES = //
  "line number 1\n" //
      + "line number 2\n" //
      + "line number 3\n";

  final public static String FIVE_LINES = //
  "line number 1\n" //
      + "line number 2\n" //
      + "line number 3\n" //
      + "line number 4\n" //
      + "line number 5\n";

  final public static String SEVEN_LINES = //
  "line number 1\n" //
      + "line number 2\n" //
      + "line number 3\n" //
      + "line number 4\n" //
      + "line number 5\n" //
      + "line number 6\n" //
      + "line number 7\n";

  public static class Line implements Serializable {
    private static final long serialVersionUID = 1L;
    final int codePoint;
    final byte[] utf8;
    final byte[] utf16be;
    final byte[] utf16le;
    final byte[] utf32be;
    final byte[] utf32le;

    public Line(final int codePoint, final byte[] utf8, //
        final byte[] utf16be, final byte[] utf16le, //
        final byte[] utf32be, final byte[] utf32le) {
      super();
      this.codePoint = codePoint;
      this.utf8 = utf8;
      this.utf16be = utf16be;
      this.utf16le = utf16le;
      this.utf32be = utf32be;
      this.utf32le = utf32le;
    }
  }
}
