package org.isk.pjba.util;

public interface BytecodeUtils {
  public static int unsign(final byte b) {
    return b & 0xFF;
  }

  public static int unsign(final short s) {
    return s & 0xFFFF;
  }

  public static long unsign(final int i) {
    return i & 0xFFFFFFFF;
  }
}
