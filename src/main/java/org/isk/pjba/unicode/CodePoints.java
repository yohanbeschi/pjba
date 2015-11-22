package org.isk.pjba.unicode;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.stream.IntStream;

import org.isk.pjba.inputstream.QuietAutoCloseable;
import org.isk.pjba.unicode.exception.UnicodeException;

/**
 * <p>
 * A CodePoints offers the ability to easily manipulate code points and code units.
 * <p>
 * A CodePoints can act as a {@link StringBuilder} but uses an <code>int</code> array buffer internally instead of a
 * <code>char</code> array.
 * <p>
 * <u>Important note</u>: There is no guessing in the character set to use:
 * <ul>
 * <li>Except for UTF-8 ({@link Charset#UTF8}), all input UTF code units arrays (byte arrays) MUST start with a BOM.
 * </li>
 * <li>Except for UTF-8 ({@link Charset#UTF8}), all output UTF code units arrays (byte arrays) WILL always start with a
 * BOM.</li>
 * </ul>
 * <p>
 * To avoid any guessing, there is no <code>Charset.UTF16</code> or <code>Charset.UTF32</code>.
 * <p>
 * Internally a CodePoints WON'T contain any information about BOM.
 * <h2>Principles and terminology</h2>
 * <p>
 * A <em>binary character set encoding</em> is the relation between an integer (a <em>code point</em>) and a character.
 * For example, the number 0100 0001 in base 2, or 65 in base 10 matches the letter 'A' (LATIN CAPITAL LETTER A) in most
 * Charsets.
 * <p>
 * A <em>character set</em> (or Charset) is a finite set of characters for which each character is encoded on a number
 * of bits defined by the encoding.
 * <p>
 * We use the word "binary" in the expression "charset encoding" as it is possible to encode characters in another form
 * like Braille or Morse code.
 * <h2>Unicode</h2>
 * <p>
 * Contrary to the popular belief the Unicode <em>Codespace</em> (number of characters that can be represented) is not
 * limited to 16-bit, but it was the case in the past when Joe Becker (one of Unicode creator with Lee Collins and Mark
 * Davis) thought that 65,535 code points will be more than enough.
 * <p>
 * Unfortunately, not long after the release of Java 1, Unicode 2 expanded its codespace to 32-bit. And this is why one
 * Java <code>char</code> can't always match one character, but we will come back to this later.
 * <p>
 * As of Unicode 7.0, the standard contained more the 100,000 characters for more than 100 scripts.
 * <p>
 * The standard is maintained by the <a href="http://unicode.org/">Unicode Consortium</a>.
 * <h2>Concepts</h2>
 * <p>
 * Unicode allows us to define 1,114,112 code points from 0x0000 to 0x10FFFF. Usually a code point is written with the
 * following notation: U+xxxx, where xxxx is a hexadecimal number and have 4 to 6 digits/letters. For example, U+0041
 * corresponds to the letter <b>A</b>.
 * <p>
 * The Unicode codespace is divided in 17 planes number from 0 to 16. The first plane (plane 0) is named <em>Basic
 * Multilingual Plane</em> (BMP) and contains code points from U+0000 to U+FFFF matching the most modern writing
 * systems. Other planes are named <em>Supplementary Multilingual Planes</em> (SMP).
 * <p>
 * Within each plane, code points are allocated within named blocks of related characters, for example
 * <a href="http://www.unicode.org/charts/PDF/U0000.pdf">Basic Latin</a>,
 * <a href="http://www.unicode.org/charts/PDF/U0080.pdf">Latin-1 Supplement</a>, etc. Although blocks are an arbitrary
 * size, they are always a multiple of 16 and often a multiple of 128. Characters required for a given script may be
 * spread out over several different blocks.
 * <p>
 * Let's take for example the string "Bonjour" (Hello in French). It is encoded in Unicode code points as follow:
 * <p>
 * <code>U+0042 U+006F U+006E U+006A U+006F U+0075 U+0072</code>
 * <p>
 * But be aware, that Unicode defines a set of characters, but not their appearance (font), nor how code points are
 * stored in memory (encoding). Let's note that the 256 first code points are identical of the ISO-8859-1 character set
 * making almost trivial the conversion of texts using occidental characters.
 * <h2>Indirection</h2>
 * <p>
 * Code points between U+D800 and U+DBFF (1024 code points) are known as High Surrogates.
 * <p>
 * Code points between U+DC00 and U+DFFF (1024 code points) are known as Low Surrogates.
 * <p>
 * A high surrogate followed by a low surrogate constitute a Surrogate Pair used to represent in UTF-16 1,048,576 code
 * points outside of the BMP (plane 0).
 * <p>
 * High and low surrogates are not valid on their own. As a result, from the Unicode codespace (1,114,112 code points)
 * only 1,112,064 are valid (1,114,112 - 2,048), ranging from U+0000 to U+D7FF and from U+E000 to U+10FFFF.
 * <h2>Noncharacters</h2>
 * <p>
 * A noncharacter is a code point reserved permanently by the Unicode standard for internal usage.
 * <p>
 * There are 66 noncharacters:
 * <ul>
 * <li>a contiguous range of 32 noncharacters: U+FDD0..U+FDEF in the BMP ;
 * <li>all code points ending by FFFE or FFFF (the last 2 code points of each plane), like U+FFFE, U+FFFF, U+1FFFE,
 * U+1FFFF, U+2FFFE, U+2FFFF, ..., U+10FFFE, U+10FFFF (34 noncharacters).
 * </ul>
 * <p>
 * The set of noncharacters is formally immutable (no noncharacter will be added or removed). This is guaranteed by a
 * <a href="http://www.unicode.org/policies/stability_policy.html#Property_Value">Unicode Stability Policy</a>.
 * <h2>Private Use Areas</h2>
 * <p>
 * Private-use characters are code points whose interpretation is not specified by a character encoding standard and
 * whose use and interpretation may be determined by private agreement among cooperating users. Private-use characters
 * are sometimes also referred to as user-defined characters (UDC) or vendor-defined characters (VDC). [
 * <a href="http://www.unicode.org/faq/private_use.html#pua1">Private-Use Characters, Noncharacters &amp; Sentinels
 * FAQ</a>]
 * <p>
 * There are three Private Use Areas:
 * <ul>
 * <li>Private Use Area: U+E000..U+F8FF (6,400 characters)
 * <li>Supplementary Private Use Area-A: U+F0000..U+FFFFD (65,534 characters)
 * <li>Supplementary Private Use Area-B: U+100000..U+10FFFD (65,534 characters)
 * </ul>
 * <h2>Reserved code points</h2>
 * <p>
 * Reserved code points are code points currently unassigned but reserved for future standardization. (
 * <a href="http://www.unicode.org/roadmaps/">Roadmaps to Unicode</a>)
 * <h2>Encoding</h2>
 * <p>
 * The Unicode defines two character encoding methods: UTF (Unicode Transformation Format) and UCS (Universal Character
 * Set). The number following the acronym corresponding for UTF to the number of bits per code unit and for the UCS to
 * the number of bytes per code unit.
 * <p>
 * A <em>Code Unit</em> is the minimal combination of bits that can represent a unit of text coded for treatment or
 * exchange. The Unicode standard uses 8-bit code units for UTF-8, 16 for UTF-16 and 32 for UTF-32.
 * <p>
 * UTF-8 being probably the most popular character encoding used today. UCS-2 is a subset of UTF-16 today obsolete, and
 * UCS-4 and UTF-32 are functionally equivalent.
 * <h3>UTF-32</h3>
 * <p>
 * In UTF-32 (and UCS-4), a 32-bit code unit is used to represent without transformation any code point (although the
 * endianness, which varies according to the different platforms, affects the byte order in a sequence of bytes). In
 * other encodings, each code point can be represented by a variable number of code units. UTF-32 is mainly used as an
 * internal representation of text in programs (as opposed to text stored or transmitted), including text editors.
 * <h3>UTF-16</h3>
 * <p>
 * In UTF-16 (and UCS-2) code points of the BMP are 2-byte and encoded without any transformation.
 * <p>
 * However, for supplementary planes UCS-2 is not suitable. UTF-16, meanwhile, encode code points in pairs of 16-bit
 * code units called surrogate pairs encoded in 4-byte. The encoding is performed as follows:
 * <ol>
 * <li>We subtract 0x10000 to the code point. The result is a 20-bit number between 0 and 0xFFFFF (result of the
 * subtraction 0x10FFFF - 0x10000)
 * <li>We add 0xD800 to the ten higher bits (a number between 0 and 0x3FF). The result is the first code unit (between
 * 0xD800 and 0xDBFF), the high surrogate.
 * <li>We add 0xDC00 to the ten lower bits (a number between 0 and 0x3FF). The result is the second code unit (between
 * 0xDC00 and 0xDFFF), the low surrogate.
 * </ol>
 * <h3>UTF-8</h3>
 * <p>
 * In UTF-8, a code point is encoded to one, two, three or four bytes.
 * <p>
 * The encoding is performed as follow:
 * <table border="1" style="border-collapse:collapsed;">
 * <caption>UTF-8 code units</caption><tbody>
 * <tr>
 * <th>Interval</th>
 * <th>Byte 1</th>
 * <th>Byte 2</th>
 * <th>Byte 3</th>
 * <th>Byte 4</th>
 * </tr>
 * <tr>
 * <td><code>U+0000 - U+007F</code></td>
 * <td><code>0xxxxxxx</code></td>
 * <td></td>
 * <td></td>
 * <td></td>
 * </tr>
 * <tr>
 * <td><code>U+0080 - U+07FF</code></td>
 * <td><code>110xxxxx</code></td>
 * <td><code>10xxxxxx</code></td>
 * <td></td>
 * <td></td>
 * </tr>
 * <tr>
 * <td><code>U+0800 - U+FFFF</code></td>
 * <td><code>1110xxxx</code></td>
 * <td><code>10xxxxxx</code></td>
 * <td><code>10xxxxxx</code></td>
 * <td></td>
 * </tr>
 * <tr>
 * <td><code>U+10000 - U+1FFFFF</code></td>
 * <td><code>11110xxx</code></td>
 * <td><code>10xxxxxx</code></td>
 * <td><code>10xxxxxx</code></td>
 * <td><code>10xxxxxx</code></td>
 * </tr>
 * </tbody>
 * </table>
 * <p>
 * The main features of this design are:
 * <ul>
 * <li>ASCII code points, ranging from 0 to 127, are encoded to one byte. In this case, the UTF-8 code point has the
 * same value as the ASCII code point. The most significant bit is always 0.
 * <li>Code points higher than 127 are represented by sequences of several bytes. The first byte begins with two, three
 * or four "1" followed by a 0. The following bytes all begin with '10'. The digit 1 in the beginning of the first byte
 * of a multibyte sequence indicates the number of bytes in the sequence, so that the length of the sequence can be
 * determined without considering the following bytes.
 * <li>The remaining bits (indicated by 'x' in the above table) are used by the point code being encoded, potentially
 * preceded by 0s if necessary.
 * <li>Single byte code points, the first byte of a sequence and continuation bytes (bytes following the first byte of a
 * multibyte sequence) cannot be mistaken. Using different patterns recognition it is possible to identify the type of a
 * byte, which in particular allow us to identify incorrect sequences and ignore errors (when streaming from the network
 * for example), move forward or backward a single Unicode character at a time without iterating through each byte, etc.
 * </ul>
 * <h3>BOM</h3>
 * <p>
 * The Byte Order Mark, or BOM is a Unicode character used to indicate the endianness (the order of bytes) of a text
 * file or a stream. Its code point is U+FEFF. The use of the BOM is optional and if it is used, it must be present at
 * the beginning of the stream. Beyond its specific use as a byte order mark, the BOM can also be used to indicate the
 * Unicode encoding.
 * <h4>UTF-8</h4>
 * <p>
 * UTF-8 can contain a BOM, however, it is not required, nor recommended to use it. UTF-8 characters are interpreted as
 * a sequence of bytes, the endianness has no impact. The BOM is used as a signature, indicating that a file is encoded
 * in UTF-8.
 * <h4>UTF-16 and UTF-32</h4>
 * <p>
 * In UTF-16 (and UTF-32), the BOM may be placed at the beginning of a stream to indicate the endianness of all the
 * 16-bit code units (32-bit code units in UTF-32 ) of the stream.
 * <p>
 * Even if the BOM is optional it is highly recommended as we want to avoid as much as guessing as we want and possible
 * mistakes when a program runs on platforms with different endiannesses.
 * <ul>
 * <li>If the 16-bit units are represented in big-endian order, the BOM character will appear in the sequence of bytes
 * 0xFE followed by 0xFF
 * <li>If the 16-bit units are represented in the little-endian byte sequence will be followed by 0xFF 0xFE.
 * </ul>
 * <p>
 * On a final note, the endianess affect a code point. Therefore, only the 2 bytes of each surrogate are reversed, not
 * the surrogates of a pair. For example, U+10083 is encoded to <code>D8 00 DC 83</code> in UTF-16BE and <code>00,
 * D8 83 DC</code> in UTF-16LE.
 * <h4>In Summary</h4>
 * <table border="1">
 * <caption>UTF and BOM</caption> <tbody>
 * <tr>
 * <th>Encoding</th>
 * <th>Representation</th>
 * </tr>
 * <tr>
 * <td>UTF-8</td>
 * <td><code>EF BB BF</code></td>
 * </tr>
 * <tr>
 * <td>UTF-16 Big Endian</td>
 * <td><code>FE FF</code></td>
 * </tr>
 * <tr>
 * <td>UTF-16 Little Endian</td>
 * <td><code>FF FE</code></td>
 * </tr>
 * <tr>
 * <td>UTF-32 Big Endian</td>
 * <td><code>00 00 FE FF</code></td>
 * </tr>
 * <tr>
 * <td>UTF-32 Little Endian</td>
 * <td><code>FF FE 00 00</code></td>
 * </tr>
 * </tbody>
 * </table>
 * <h2>Planes</h2>
 * <p>
 * As of Unicode 7.0
 * <ul>
 * <li>Plane 0 - 0000–​FFFF - Basic Multilingual Plane (BMP)
 * <ul>
 * <li>...</li>
 * <li>D800-DB7F - High Surrogate Area</li>
 * <li>DB80-DBFF - High Private Use Surrogates</li>
 * <li>DC00-DFFF - Low Surrogate Area</li>
 * <li>E000-F8FF - Private Use Area</li>
 * <li>...</li>
 * <li>FDD0-FDEF - Noncharacters (Part of Arabic Presentation Forms-A)</li>
 * </ul>
 * </li>
 * <li>Plane 1 - 10000–​1FFFF - Supplementary Multilingual Plane (SMP)</li>
 * <li>Plane 2 - 20000–​2FFFF - Supplementary Ideographic Plane (SIP)</li>
 * <li>Plane 3-13 - 30000–​DFFFF - unassigned</li>
 * <li>Plane 14 - E0000–​E0FFF - Supplementary Special-purpose Plane (SSP)</li>
 * <li>Plane 15 - F0000–​FFFFF - Supplementary Private Use Area A (S PUA A)</li>
 * <li>Plane 16 - 100000–​10FFFF - Supplementary Private Use Area B (S PUA B)</li>
 * </ul>
 */
public class CodePoints {
  @FunctionalInterface
  public static interface UtfToCodePoint {
    int toCodePoint(UnicodeInputStream stream);
  }

  @FunctionalInterface
  public static interface CodePointToUtf {
    void toUtf(final int codePoint, final ByteArrayOutputStream outputStream);
  }

  /**
   * Allowed Character Sets.
   */
  public static enum Charset {
    UTF8, UTF8BOM, UTF16BE, UTF16LE, UTF32BE, UTF32LE
  }

  /**
   * All possible code points (from 0 to 0x10FFFF).
   */
  final public static int UNICODE_CODESPACE_SIZE = 1_114_112;

  /**
   * <p>
   * All valid code points.
   * <p>
   * Remember that surrogates are not valid code points as a high (U+D800..U+DBFF) or low (U+DC00..U+DFFF) surrogate
   * can't stand on its own without it's counterpart (respectively low and high). A high and a low surrogate constitute
   * "a surrogate pair".
   * <p>
   * Moreover, surrogates have only been created (for the UTF-16 encoding to support code points outside of the BMP) to
   * avoid any collision between a real code point and a 16-bit code unit (where two 16-bit code unit will constitute a
   * real code point).
   * <p>
   * Finally, even if non-characters code points are guaranteed to never to be used for encoding characters, there are
   * still code points and therefore parts of the count of the valid code points. The same goes for non characters.
   */
  final public static int NUMBER_OF_VALID_CODEPOINTS = 1_112_064;

  /**
   * <p>
   * U+E000..U+F8FF: BMP (0) - 6400 code points.<br>
   * U+F0000..U+FFFFD: PUP (15) - 65534 code points.<br>
   * U+100000..U+10FFFD: PUP (16) - 65534 code points.
   */
  final public static int NUMBER_OF_CODEPOINTS_IN_PRIVATE_USE_AREA = 137_468;

  /**
   * <p>
   * U+FDD0..U+FDEF: BMP (0) - 32 code points.<br>
   * All code points ending by 0xFFFE or 0xFFFF. 17 planes * 2 = 34 code points.
   */
  final public static int NUMBER_OF_NONCHARACTERS = 66;

  final public static byte[] UTF8_BOM = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };

  final public static byte[] UTF16BE_BOM = { (byte) 0xFE, (byte) 0xFF };

  final public static byte[] UTF16LE_BOM = { (byte) 0xFF, (byte) 0xFE };

  final public static byte[] UTF32BE_BOM = { 0, 0, (byte) 0xFE, (byte) 0xFF };

  final public static byte[] UTF32LE_BOM = { (byte) 0xFF, (byte) 0xFE, 0, 0 };

  /**
   * ZERO WIDTH NO-BREAK SPACE (BOM).
   */
  final public static int BOM_CODEPOINT = 0xFEFF;

  /**
   * Default buffer size.
   */
  final private static int DEFAULT_BUFFER_SIZE = 1024;

  /**
   * <p>
   * Size of the buffer.
   * <p>
   * Moreover, the buffer will grow of this size if we don't have enough room in it.
   */
  private final int bufferGrowthSize;

  /**
   * Buffer containing the code points.
   */
  private int[] buffer;

  /**
   * Position of the last code point + 1 in the buffer.
   */
  private int index;

  /**
   * <p>
   * Instantiates a new <code>CodePoints</code>.
   * <p>
   * The initial buffer will be {@link DEFAULT_BUFFER_SIZE}.
   */
  public CodePoints() {
    this(CodePoints.DEFAULT_BUFFER_SIZE);
  }

  /**
   * <p>
   * Instantiates a new <code>CodePoints</code> with an initial size.
   * <p>
   * This size will be used thereafter to grow the buffer size if we don't have enough room to add a new code point.
   *
   * @param initialSize
   *          is the initial size of the buffer.
   * @throws UnicodeException
   *           if the initial buffer size is 0 or less.
   */
  public CodePoints(final int initialSize) {
    if (initialSize <= 0) {
      throw new UnicodeException("The size of the buffer can't be 0 or less.");
    }

    this.buffer = new int[initialSize];
    this.bufferGrowthSize = initialSize;
    this.index = 0;
  }

  /**
   * <p>
   * Instantiates a new <code>CodePoints</code> with an array of code points.
   * <p>
   * The initial size of the buffer will be the length of the array. Therefore, if a new code point if added, the buffer
   * size will have to be grown, and it will be by the length of the array. And so on every time we will have to expand
   * the buffer.
   *
   * @param codePoints
   *          is an array of code points.
   * @throws NullPointerException
   *           if the array is <code>null</code>.
   */
  public CodePoints(final int[] codePoints) {
    this.buffer = codePoints;
    this.bufferGrowthSize = this.buffer.length;
    this.index = codePoints.length;
  }

  /**
   * <p>
   * Instantiates a new <code>CodePoints</code> with a String.
   * <p>
   * The initial size of the buffer will be the number of code points contained in the String. Therefore, if a new code
   * point if added, the buffer size will have to be grown, and it will be by the length of the number of code points.
   * And so on every time we will have to expand the buffer.
   *
   * @param string
   *          is a String.
   */
  public CodePoints(final String string) {
    this.buffer = string.codePoints().toArray();
    this.bufferGrowthSize = this.buffer.length;
    this.index = this.bufferGrowthSize;
  }

  /**
   * Is the buffer empty ?
   *
   * @return <code>true</code> if the buffer is empty, <code>false</code> otherwise.
   */
  public boolean isEmpty() {
    return this.index == 0;
  }

  /**
   * <p>
   * Returns the number of code points stored in the buffer.
   * <p>
   * But be careful, even if this class solve the problem of code points outside of the BMP, it DOESN'T solve the
   * problem of the graphemes count (what we usually call "characters"). For most scripts it's not a problem as one code
   * point is equal to one grapheme (one user-perceived character), but for some scripts like Devanagari a grapheme is
   * composed of more than one code point, like 'नि' which is the result of two code points (U+0928 and U+093F),
   * visually represented by 'न ' and' ि'. For this case, this method will return 2 and not 1 as one might expect.
   *
   * @return the number of code points stored in the buffer.
   */
  public int length() {
    return this.index;
  }

  /**
   * <p>
   * Returns the number of code points stored in the buffer.
   * <p>
   * Clearing the buffer instead of creating a new instance of {@link CodePoints} is less memory consuming as we don't
   * have to create a new int array.
   *
   * @return the number of code points stored in the buffer.
   */
  public int lengthAndReset() {
    final int length = this.length();
    this.clear();
    return length;
  }

  /**
   * <p>
   * Sets the length of the code points sequence.
   * <p>
   * This method will usually be used to discard characters at the end of the sequence.
   *
   * @param newLength
   *          is the new length of the code points sequence.
   * @throws UnicodeException
   *           if the new length is out of range (<tt>length() &lt; 0 || length() &gt;= size()</tt>).
   */
  public void setLength(final int newLength) {
    if (newLength < 0 || newLength > this.index) {
      throw new UnicodeException("The new length is outside of range (" + newLength
          + "). It should be greater than or equal to 0 and less than or equal to the current length.");
    }

    this.index = newLength;
  }

  /**
   * <p>
   * Remove the last code point added to the sequence.
   * <p>
   * If the sequence is empty nothing is done.
   */
  public void remove() {
    if (this.index > 0) {
      this.index--;
    }
  }

  /**
   * <p>
   * Returns the last code point in the sequence and remove it.
   * <p>
   * If the sequence is empty, returns -1.
   *
   * @return the last code point in the sequence.
   */
  public int getLastAndRemove() {
    int codePoint = -1;
    if (this.index > 0) {
      this.index--;
      codePoint = this.buffer[this.index];
    }

    return codePoint;
  }

  /**
   * <p>
   * Clears the buffer.
   * <p>
   * Clearing the buffer instead of creating a new instance of {@link CodePoints} is less memory consuming as we don't
   * have to create a new int array.
   */
  public void clear() {
    this.index = 0;
  }

  /**
   * <p>
   * Adds a code point to the buffer.
   * <p>
   * If the code point is the BOM it will be added but it shouldn't as it doesn't have another meaning than indicating
   * the byte order of code units. Used as an indication of non-breaking is deprecated, see U+2060 instead.
   *
   * @param codePoint
   *          is a code point to be added.
   */
  public void add(final int codePoint) {
    if (this.index >= this.buffer.length) {
      this.buffer = Arrays.copyOf(this.buffer, this.buffer.length + this.bufferGrowthSize);
    }

    this.buffer[this.index++] = codePoint;
  }

  /**
   * Adds an array of code points to the buffer.
   *
   * @param codePoints
   *          is an array of code points to add to the buffer.
   */
  public void add(final int[] codePoints) {
    for (final int codePoint : codePoints) {
      this.add(codePoint);
    }
  }

  /**
   * Adds the code points of a string to the buffer.
   *
   * @param s
   *          is the string to add to the buffer.
   */
  public void add(final String s) {
    s.codePoints().forEach(codePoint -> this.add(codePoint));
  }

  /**
   * Returns the code point at the specified position in the buffer.
   *
   * @param index
   *          is the index of the code point to return.
   * @return the code point at the specified position in this buffer.
   * @throws UnicodeException
   *           if the index is out of range (<tt>length() &lt; 0 || length() &gt;= size()</tt>).
   */
  public int at(final int index) {
    if (index < 0 || index >= this.index) {
      throw new UnicodeException("Index outside of range (" + index
          + "). It should be greater than or equal to 0 and less than the current length.");
    }

    return this.buffer[index];
  }

  /**
   * <p>
   * Returns the code points as an array of <code>int</code>.
   * <p>
   * Be careful if there is BOM code points in the code points array they won't be removed.
   *
   * @return the code points as an array of <code>int</code>.
   */
  public int[] toArray() {
    return Arrays.copyOf(this.buffer, this.index);
  }

  /**
   * <p>
   * Returns the code points currently in the buffer as an array of <code>int</code> and clear the buffer afterward.
   *
   * @return the code points as an array of <code>int</code>.
   */
  public int[] toArrayAndReset() {
    final int[] codePoints = this.toArray();
    this.clear();
    return codePoints;
  }

  /**
   * <p>
   * Returns the string representation of the code points currently in the buffer and clear the buffer afterward.
   * <p>
   * If there is a BOM code point at the beginning of the code points array, it will be removed, but not at any other
   * position.
   * <p>
   * Clearing the buffer instead of creating a new instance of {@link CodePoints} is less memory consuming as we don't
   * have to create a new int array.
   *
   * @return the string representation of the code points.
   */
  public String toStringAndReset() {
    final String string = this.toString();
    this.clear();
    return string;
  }

  /**
   * <p>
   * Returns the string representation of the code points currently in the buffer.
   * <p>
   * If there is a BOM code point at the beginning of the code points array, it will be removed.
   *
   * @return the string representation of the code points.
   */
  @Override
  public String toString() {
    int start;
    int count;
    if (CodePoints.isBOM(this.buffer[0])) {
      start = 1;
      count = this.index - 1;
    } else {
      start = 0;
      count = this.index;
    }
    return new String(this.buffer, start, count);
  }

  /**
   * Converts an array of code points into a String.
   *
   * @param codePoints
   *          is the array of code points to be converted into a String.
   * @return the string representation of the code points in parameters.
   */
  public static String toString(final int[] codePoints) {
    return new String(codePoints, 0, codePoints.length);
  }

  /*
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(this.buffer);
    return result;
  }

  /*
   * {@inheritDoc}
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final CodePoints other = (CodePoints) obj;
    if (!Arrays.equals(this.buffer, other.buffer)) {
      return false;
    }
    return true;
  }

  // -------------------------------------------------------------------------------------------------------------------
  // code points/code units Sequence conversion
  // -------------------------------------------------------------------------------------------------------------------

  /**
   * <p>
   * Encodes the code points in the internal buffer encoded to code units of a specified encoding.
   * <p>
   * If there is a BOM code point at the beginning of the code points array, it will be removed.
   *
   * @param outputCharset
   *          is the output encoding.
   * @return a byte array of code units.
   */
  public byte[] toUtf(final Charset outputCharset) {
    final CodePointToUtf codePointToUtf = CodePoints.findCodePointToUtf(outputCharset);
    final ByteArrayOutputStream outputStream = CodePoints.initByteArray(outputCharset);

    final int codePoint = this.buffer[0];
    int start;
    if (CodePoints.isBOM(codePoint)) {
      start = 1;
    } else {
      start = 0;
    }

    for (int i = start; i < this.index; i++) {
      codePointToUtf.toUtf(this.buffer[i], outputStream);
    }

    return outputStream.toByteArray();
  }

  /**
   * Decodes a byte array of code units from a specified encoding to an array of code points.
   *
   * @param fromCharset
   *          is the input encoding.
   * @param utf
   *          is a byte array of code units.
   * @return an array of code points.
   */
  public static int[] toCodePoints(final Charset fromCharset, final byte[] utf) {
    return CodePoints.toCodePoints(fromCharset, new UnicodeInputStream(utf));
  }

  /**
   * <p>
   * Decodes the code units contained by an {@link UnicodeInputStream} from a specified encoding to code points.
   * <p>
   * The code points will be added to the internal buffer.
   *
   * @param charset
   *          is the encoding of the code units.
   * @param inputStream
   *          is an {@link UnicodeInputStream} containing code units.
   * @return an array of code points.
   */
  public static int[] toCodePoints(final Charset charset, final UnicodeInputStream inputStream) {
    final CodePoints codePoints = new CodePoints();

    final UtfToCodePoint utfToCodePoint = CodePoints.findUtfToCodePoint(charset);
    CodePoints.readBom(charset, inputStream);

    while (inputStream.hasNext()) {
      final int codePoint = utfToCodePoint.toCodePoint(inputStream);
      codePoints.add(codePoint);
    }

    return codePoints.toArray();
  }

  /**
   * Encodes an array of code points to a byte array of code units of a specified encoding.
   *
   * @param codePointsArray
   *          is an array of code points.
   * @param outputCharset
   *          is the output encoding.
   * @return a byte array of code units.
   */
  public static byte[] toUtf(final int[] codePointsArray, final Charset outputCharset) {
    final CodePoints codePoints = new CodePoints(codePointsArray);
    return codePoints.toUtf(outputCharset);
  }

  /**
   * Encodes a byte array of code units from a specified encoding to another byte array of code units of another
   * specified encoding.
   *
   * @param bytes
   *          is a byte array of code units
   * @param fromCharset
   *          is the input encoding.
   * @param toCharset
   *          is the output encoding.
   * @return an array of code units.
   */
  public static byte[] utfToUtf(final byte[] bytes, final Charset fromCharset, final Charset toCharset) {
    if (fromCharset == toCharset) {
      return bytes;
    } else {
      return CodePoints.utfToUtf(new UnicodeInputStream(bytes), fromCharset, toCharset);
    }
  }

  /**
   * Encodes the code units contained by an {@link UnicodeInputStream} from a specified encoding to a byte array of code
   * units of another specified encoding.
   *
   * @param inputStream
   *          is an {@link UnicodeInputStream} containing code units.
   * @param fromCharset
   *          is the input encoding.
   * @param toCharset
   *          is the output encoding.
   * @return an array of code units.
   */
  public static byte[] utfToUtf(final UnicodeInputStream inputStream, final Charset fromCharset,
      final Charset toCharset) {
    if (fromCharset == toCharset) {
      final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      while (inputStream.hasNext()) {
        outputStream.write(inputStream.read());
      }

      return outputStream.toByteArray();
    } else {
      final UtfToCodePoint utfToCodePoint = CodePoints.findUtfToCodePoint(fromCharset);
      final CodePointToUtf codePointToUtf = CodePoints.findCodePointToUtf(toCharset);
      final ByteArrayOutputStream outputStream = CodePoints.initByteArray(toCharset);

      CodePoints.readBom(fromCharset, inputStream);

      while (inputStream.hasNext()) {
        final int codePoint = utfToCodePoint.toCodePoint(inputStream);
        codePointToUtf.toUtf(codePoint, outputStream);
      }

      return outputStream.toByteArray();
    }
  }

  /**
   * Returns an {@link IntStream} containing the code points of the internal buffer.
   *
   * @return an {@link IntStream} containing the code points of the internal buffer.
   */
  public IntStream stream() {
    return Arrays.stream(this.buffer, 0, this.index);
  }

  // -----------------------------------------------------------------------------------------------------------------
  // Find UTF to CodePoint converter method
  // -----------------------------------------------------------------------------------------------------------------

  /**
   * Returns an {@link UtfToCodePoint} containing the right method to decode the next (from 1 up 4) code units contained
   * in a {@link UnicodeInputStream} of the specified encoding to a code point.
   *
   * @param charset
   *          is the input encoding.
   * @return an {@link UtfToCodePoint}.
   */
  public static UtfToCodePoint findUtfToCodePoint(final Charset charset) {
    switch (charset) {
    case UTF8:
    case UTF8BOM:
      return CodePoints::utf8ToCodePoint;
    case UTF16BE:
      return CodePoints::utf16beToCodePoint;
    case UTF16LE:
      return CodePoints::utf16leToCodePoint;
    case UTF32BE:
      return CodePoints::utf32beToCodePoint;
    case UTF32LE:
      return CodePoints::utf32leToCodePoint;
    default:
      throw new UnicodeException("Unknown charset!");
    }
  }

  // -----------------------------------------------------------------------------------------------------------------
  // Find CodePoint to UTF converter method
  // -----------------------------------------------------------------------------------------------------------------

  /**
   * Returns a {@link CodePointToUtf} containing the right method to convert a code point to an array of code units of a
   * specified encoding.
   *
   * @param charset
   *          is the output encoding.
   * @return a {@link CodePointToUtf}.
   */
  public static CodePointToUtf findCodePointToUtf(final Charset charset) {
    switch (charset) {
    case UTF8:
    case UTF8BOM:
      return CodePoints::codePointToUtf8;
    case UTF16BE:
      return CodePoints::codePointToUtf16be;
    case UTF16LE:
      return CodePoints::codePointToUtf16le;
    case UTF32BE:
      return CodePoints::codePointToUtf32be;
    case UTF32LE:
      return CodePoints::codePointToUtf32le;
    default:
      throw new UnicodeException("Unknown charset!");
    }
  }

  // -----------------------------------------------------------------------------------------------------------------
  // Read the BOM from the inputStream if any
  // -----------------------------------------------------------------------------------------------------------------
  /**
   * <p>
   * Checks and discards the BOM (if any) from the {@link UnicodeInputStream}.
   * <p>
   * Only {@link Charset#UTF8} doesn't have a BOM.
   *
   * @param charset
   *          is the encoding of the code units in the {@link UnicodeInputStream}.
   * @param inputStream
   *          is an {@link UnicodeInputStream} containing code units of the specified encoding.
   * @throws UnicodeException
   *           if the expected BOM is not present.
   */
  public static void readBom(final Charset charset, final UnicodeInputStream inputStream) {
    switch (charset) {
    case UTF8:
      break;
    case UTF8BOM:
      if (inputStream.read() != 0xEF) {
        throw new UnicodeException("Wrong UTF-8 BOM. Expected 0xEF (byte 0).");
      }
      if (inputStream.read() != 0xBB) {
        throw new UnicodeException("Wrong UTF-8 BOM. Expected 0xBB (byte 1).");
      }
      if (inputStream.read() != 0xBF) {
        throw new UnicodeException("Wrong UTF-8 BOM. Expected 0xBF (byte 2).");
      }
      break;
    case UTF16BE:
      if (inputStream.read() != 0xFE) {
        throw new UnicodeException("Wrong UTF-16-BE BOM. Expected 0xFE (byte 0).");
      }
      if (inputStream.read() != 0xFF) {
        throw new UnicodeException("Wrong UTF-16-BE BOM. Expected 0xFF (byte 1).");
      }
      break;
    case UTF16LE:
      if (inputStream.read() != 0xFF) {
        throw new UnicodeException("Wrong UTF-16-LE BOM. Expected 0xFF (byte 0).");
      }
      if (inputStream.read() != 0xFE) {
        throw new UnicodeException("Wrong UTF-16-LE BOM. Expected 0xFE (byte 1).");
      }
      break;
    case UTF32BE:
      if (inputStream.read() != 0x00) {
        throw new UnicodeException("Wrong UTF-32-BE BOM. Expected 0x00 (byte 0).");
      }
      if (inputStream.read() != 0x00) {
        throw new UnicodeException("Wrong UTF-32-BE BOM. Expected 0x00 (byte 1).");
      }
      if (inputStream.read() != 0xFE) {
        throw new UnicodeException("Wrong UTF-32-BE BOM. Expected 0xFE (byte 2).");
      }
      if (inputStream.read() != 0xFF) {
        throw new UnicodeException("Wrong UTF-32-BE BOM. Expected 0xFF (byte 3).");
      }
      break;
    case UTF32LE:
      if (inputStream.read() != 0xFF) {
        throw new UnicodeException("Wrong UTF-32-LE BOM. Expected 0xFF (byte 0).");
      }
      if (inputStream.read() != 0xFE) {
        throw new UnicodeException("Wrong UTF-32-LE BOM. Expected 0xFE (byte 1).");
      }
      if (inputStream.read() != 0x00) {
        throw new UnicodeException("Wrong UTF-32-LE BOM. Expected 0x00 (byte 2).");
      }
      if (inputStream.read() != 0x00) {
        throw new UnicodeException("Wrong UTF-32-LE BOM. Expected 0x00 (byte 3).");
      }
      break;
    default:
      throw new UnicodeException("Unknown charset!");
    }
  }

  // -----------------------------------------------------------------------------------------------------------------
  // Init ByteArrayOutputStream with the right BOM
  // -----------------------------------------------------------------------------------------------------------------

  /**
   * Initializes a new {@link ByteArrayOutputStream} and add the correct BOM (if any).
   *
   * @param charset
   *          is the character set corresponding to the encoding of the characters that will be added to the
   *          {@link ByteArrayOutputStream}.
   * @return a new {@link ByteArrayOutputStream}.
   */
  public static ByteArrayOutputStream initByteArray(final Charset charset) {
    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    switch (charset) {
    case UTF8:
      break;
    case UTF8BOM:
      outputStream.write(0xEF);
      outputStream.write(0xBB);
      outputStream.write(0xBF);
      break;
    case UTF16BE:
      outputStream.write(0xFE);
      outputStream.write(0xFF);
      break;
    case UTF16LE:
      outputStream.write(0xFF);
      outputStream.write(0xFE);
      break;
    case UTF32BE:
      outputStream.write(0x00);
      outputStream.write(0x00);
      outputStream.write(0xFE);
      outputStream.write(0xFF);
      break;
    case UTF32LE:
      outputStream.write(0xFF);
      outputStream.write(0xFE);
      outputStream.write(0x00);
      outputStream.write(0x00);
      break;
    default:
      throw new UnicodeException("Unknown charset!");
    }

    return outputStream;
  }

  // -----------------------------------------------------------------------------------------------------------------
  // UTF To CodePoint
  // -----------------------------------------------------------------------------------------------------------------

  /**
   * <p>
   * Decodes the next character (from the {@link UnicodeInputStream}) represented by code units of UTF-8 to its
   * corresponding code point.
   * <p>
   * Before calling this method you should call {@link UnicodeInputStream#hasNext()}, otherwise if there is no more
   * character to read an {@link UnicodeException} will be thrown.
   * <p>
   * If the data are corrupted (missing bytes to form the code point) an {@link UnicodeException} will be thrown.
   *
   * @param inputStream
   *          is the source from which the bytes will be read.
   * @return the code point corresponding to the next character in the {@link UnicodeInputStream}.
   * @throws UnicodeException
   *           if there is no more character to read or if the data are corrupted.
   */
  public static int utf8ToCodePoint(final UnicodeInputStream inputStream) {
    final int character = inputStream.read();

    int codePoint = 0;
    // U+0000 - U+007F
    if (character >> 7 == 0) {
      codePoint = character; // & 0b0111_1111;
    }
    // U+0080 - U+07FF
    else if (character >> 5 == 0b110) {
      codePoint = (character & 0b0001_1111) << 6;
      codePoint |= inputStream.read() & 0b0011_1111;
    }
    // U+0800 - U+FFFF
    else if (character >> 4 == 0b1110) {
      codePoint = (character & 0b0000_1111) << 12;
      codePoint |= (inputStream.read() & 0b0011_1111) << 6;
      codePoint |= inputStream.read() & 0b0011_1111;
    }
    // U+10000 - U+1FFFFF
    else if (character >> 3 == 0b1_1110) {
      codePoint = (character & 0b0000_0111) << 18;
      codePoint |= (inputStream.read() & 0b0011_1111) << 12;
      codePoint |= (inputStream.read() & 0b0011_1111) << 6;
      codePoint |= inputStream.read() & 0b0011_1111;
    }

    return codePoint;
  }

  /**
   * <p>
   * Decodes the next character from the {@link UnicodeInputStream} represented by code units of UTF-16BE to its
   * corresponding code point.
   * <p>
   * Before calling this method you should call {@link UnicodeInputStream#hasNext()}, otherwise if there is no more
   * character to read an {@link UnicodeException} will be thrown.
   * <p>
   * If the data are corrupted (missing bytes to form the code point) an {@link UnicodeException} will be thrown.
   *
   * @param inputStream
   *          is the source from which the bytes will be read.
   * @return the code point corresponding to the next character in the {@link UnicodeInputStream}.
   * @throws UnicodeException
   *           if there is no more character to read or if the data are corrupted.
   */
  public static int utf16beToCodePoint(final UnicodeInputStream inputStream) {
    final int character = inputStream.read() << 8 | inputStream.read();

    if (CodePoints.isSurrogate(character)) {
      final int highSurrogate = character - 0xD800 << 10;
      int lowSurrogate = inputStream.read() << 8 | inputStream.read();
      lowSurrogate -= 0xDC00;
      return (highSurrogate | lowSurrogate) + 0x10000;
    } else {
      return character;
    }
  }

  /**
   * <p>
   * Decodes the next character from the {@link UnicodeInputStream} represented by code units of UTF-16LE to its
   * corresponding code point.
   * <p>
   * Before calling this method you should call {@link UnicodeInputStream#hasNext()}, otherwise if there is no more
   * character to read an {@link UnicodeException} will be thrown.
   * <p>
   * If the data are corrupted (missing bytes to form the code point) an {@link UnicodeException} will be thrown.
   *
   * @param inputStream
   *          is the source from which the bytes will be read.
   * @return the code point corresponding to the next character in the {@link UnicodeInputStream}.
   * @throws UnicodeException
   *           if there is no more character to read or if the data are corrupted.
   */
  public static int utf16leToCodePoint(final UnicodeInputStream inputStream) {
    final int character = inputStream.read() | inputStream.read() << 8;

    if (CodePoints.isSurrogate(character)) {
      final int highSurrogate = character - 0xD800 << 10;
      int lowSurrogate = inputStream.read() | inputStream.read() << 8;
      lowSurrogate -= 0xDC00;
      return (highSurrogate | lowSurrogate) + 0x10000;
    } else {
      return character;
    }
  }

  /**
   * <p>
   * Decodes the next character from the {@link UnicodeInputStream} represented by code units of UTF-32BE to its
   * corresponding code point.
   * <p>
   * Before calling this method you should call {@link UnicodeInputStream#hasNext()}, otherwise if there is no more
   * character to read an {@link UnicodeException} will be thrown.
   * <p>
   * If the data are corrupted (missing bytes to form the code point) an {@link UnicodeException} will be thrown.
   *
   * @param inputStream
   *          is the source from which the bytes will be read.
   * @return the code point corresponding to the next character in the {@link UnicodeInputStream}.
   * @throws UnicodeException
   *           if there is no more character to read or if the data are corrupted.
   */
  public static int utf32beToCodePoint(final UnicodeInputStream inputStream) {
    return inputStream.read() << 24 | inputStream.read() << 16 | inputStream.read() << 8 | inputStream.read();
  }

  /**
   * <p>
   * Decodes the next character from the {@link UnicodeInputStream} represented by code units of UTF-32LE to its
   * corresponding code point.
   * <p>
   * Before calling this method you should call {@link UnicodeInputStream#hasNext()}, otherwise if there is no more
   * character to read an {@link UnicodeException} will be thrown.
   * <p>
   * If the data are corrupted (missing bytes to form the code point) an {@link UnicodeException} will be thrown.
   *
   * @param inputStream
   *          is the source from which the bytes will be read.
   * @return the code point corresponding to the next character in the {@link UnicodeInputStream}.
   * @throws UnicodeException
   *           if there is no more character to read or if the data are corrupted.
   */
  public static int utf32leToCodePoint(final UnicodeInputStream inputStream) {
    return inputStream.read() | inputStream.read() << 8 | inputStream.read() << 16 | inputStream.read() << 24;
  }

  // -----------------------------------------------------------------------------------------------------------------
  // CodePoint to UTF
  // -----------------------------------------------------------------------------------------------------------------

  /**
   * <p>
   * Encodes a code point to UTF-8 code units (with or without BOM it doesn't make any difference) and add them to the
   * {@link ByteArrayOutputStream}.
   * <p>
   * <u>Important note</u>: The {@link ByteArrayOutputStream} SHOULD be instantiated by the method
   * {@link #initByteArray(CodePoints.Charset)} which will add the correct BOM at the beginning of the stream.
   *
   * @param codePoint
   *          is the code point to encode.
   * @param outputStream
   *          is the {@link ByteArrayOutputStream} to which the code units will be added to.
   */
  public static void codePointToUtf8(final int codePoint, final ByteArrayOutputStream outputStream) {
    if (codePoint <= 0x007F) {
      outputStream.write(codePoint);
    } else if (codePoint <= 0x07FF) {
      outputStream.write(codePoint >>> 6 | 0b1100_0000);
      outputStream.write(codePoint & 0x3F | 0b1000_0000);
    } else if (codePoint <= 0xFFFF) {
      outputStream.write(codePoint >>> 12 | 0b1110_0000);
      outputStream.write(codePoint >>> 6 & 0x3F | 0b1000_0000);
      outputStream.write(codePoint & 0x3F | 0b1000_0000);
    } else {
      outputStream.write(codePoint >>> 18 | 0b1111_0000);
      outputStream.write(codePoint >>> 12 & 0x3F | 0b1000_0000);
      outputStream.write(codePoint >>> 6 & 0x3F | 0b1000_0000);
      outputStream.write(codePoint & 0x3F | 0b1000_0000);
    }
  }

  /**
   * <p>
   * Encodes a code point to UTF-16BE code units and add it to the {@link ByteArrayOutputStream}.
   * <p>
   * <u>Important note</u>: The {@link ByteArrayOutputStream} SHOULD be instantiated by the method
   * {@link #initByteArray(CodePoints.Charset)} which will add the correct BOM at the beginning of the stream.
   *
   * @param codePoint
   *          is the code point to encode.
   * @param outputStream
   *          is the {@link ByteArrayOutputStream} to which the code units will be added to.
   */
  public static void codePointToUtf16be(final int codePoint, final ByteArrayOutputStream outputStream) {
    if (codePoint > 0xFFFF) {
      final int tmp = codePoint - 0x10000;
      final int highSurrogate = (tmp >>> 10) + 0xD800;
      final int lowSurrogate = (tmp & 0x3FF) + 0xDC00;

      outputStream.write(highSurrogate >>> 8 & 0xFF);
      outputStream.write(highSurrogate & 0xFF);
      outputStream.write(lowSurrogate >>> 8 & 0xFF);
      outputStream.write(lowSurrogate & 0xFF);
    } else {
      outputStream.write(codePoint >>> 8 & 0xFF);
      outputStream.write(codePoint & 0xFF);
    }
  }

  /**
   * <p>
   * Encodes a code point to UTF-16LE code units and add it to the {@link ByteArrayOutputStream}.
   * <p>
   * <u>Important note</u>: The {@link ByteArrayOutputStream} SHOULD be instantiated by the method
   * {@link #initByteArray(CodePoints.Charset)} which will add the correct BOM at the beginning of the stream.
   *
   * @param codePoint
   *          is the code point to encode.
   * @param outputStream
   *          is the {@link ByteArrayOutputStream} to which the code units will be added to.
   */
  public static void codePointToUtf16le(final int codePoint, final ByteArrayOutputStream outputStream) {
    if (codePoint > 0xFFFF) {
      final int tmp = codePoint - 0x10000;
      final int highSurrogate = (tmp >>> 10) + 0xD800;
      final int lowSurrogate = (tmp & 0x3FF) + 0xDC00;

      outputStream.write(highSurrogate & 0xFF);
      outputStream.write(highSurrogate >>> 8 & 0xFF);
      outputStream.write(lowSurrogate & 0xFF);
      outputStream.write(lowSurrogate >>> 8 & 0xFF);
    } else {
      outputStream.write(codePoint & 0xFF);
      outputStream.write(codePoint >>> 8 & 0xFF);
    }
  }

  /**
   * <p>
   * Encodes a code point to UTF-32BE code units and add it to the {@link ByteArrayOutputStream}.
   * <p>
   * <u>Important note</u>: The {@link ByteArrayOutputStream} SHOULD be instantiated by the method
   * {@link #initByteArray(CodePoints.Charset)} which will add the correct BOM at the beginning of the stream.
   *
   * @param codePoint
   *          is the code point to encode.
   * @param outputStream
   *          is the {@link ByteArrayOutputStream} to which the code units will be added to.
   */
  public static void codePointToUtf32be(final int codePoint, final ByteArrayOutputStream outputStream) {
    outputStream.write(codePoint >>> 24);
    outputStream.write(codePoint >>> 16 & 0xFF);
    outputStream.write(codePoint >>> 8 & 0xFF);
    outputStream.write(codePoint & 0xFF);
  }

  /**
   * <p>
   * Encodes a code point to UTF-32LE code units and add it to the {@link ByteArrayOutputStream}.
   * <p>
   * <u>Important note</u>: The {@link ByteArrayOutputStream} SHOULD be instantiated by the method
   * {@link #initByteArray(CodePoints.Charset)} which will add the correct BOM at the beginning of the stream.
   *
   * @param codePoint
   *          is the code point to encode.
   * @param outputStream
   *          is the {@link ByteArrayOutputStream} to which the code units will be added to.
   */
  public static void codePointToUtf32le(final int codePoint, final ByteArrayOutputStream outputStream) {
    outputStream.write(codePoint & 0xFF);
    outputStream.write(codePoint >>> 8 & 0xFF);
    outputStream.write(codePoint >>> 16 & 0xFF);
    outputStream.write(codePoint >>> 24);
  }

  // -----------------------------------------------------------------------------------------------------------------
  // UTF to UTF
  // -----------------------------------------------------------------------------------------------------------------

  /**
   * <p>
   * Encodes a byte array of code units representing ONE character from a specified encoding to another byte array of
   * code units of another specified encoding.
   * <p>
   * If <code>fromCharacter</code> is equals to <code>toCharacter</code> the input argument <code>oneCharacter</code> is
   * returned.
   * <p>
   * <u>Important note</u>: Don't use this method to encode multiple characters. Use the method
   * {@link CodePoints#utfToUtf(byte[], Charset, Charset)} or
   * {@link CodePoints#utfToUtf(UnicodeInputStream, Charset, Charset)} instead as they won't create a memory overhead.
   *
   * @param oneCharacter
   *          the character to encode.
   * @param fromCharset
   *          the character set in which the character if encoded.
   * @param toCharset
   *          the character set in which the character will be encoded.
   * @return the character to another encoding.
   */
  public static byte[] characterUtfToUtf(final byte[] oneCharacter, final Charset fromCharset,
      final Charset toCharset) {
    if (fromCharset == toCharset) {
      return oneCharacter;
    } else {
      final UtfToCodePoint utfToCodePoint = CodePoints.findUtfToCodePoint(fromCharset);
      final CodePointToUtf codePointToUtf = CodePoints.findCodePointToUtf(toCharset);
      final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(4);
      final int codePoint = utfToCodePoint.toCodePoint(new UnicodeInputStream(oneCharacter));
      codePointToUtf.toUtf(codePoint, outputStream);
      return outputStream.toByteArray();
    }
  }

  // -----------------------------------------------------------------------------------------------------------------
  // Helpers
  // -----------------------------------------------------------------------------------------------------------------

  /**
   * Is the code point a BOM (also known as ZERO WIDTH NO-BREAK SPACE) ?
   *
   * @param codePoint
   *          is the code point to check.
   * @return <code>true</code> if the code point correspond to the BOM (<code>0xfeff</code>), <code>false</code>
   *         otherwise.
   */
  public static boolean isBOM(final int codePoint) {
    return codePoint == CodePoints.BOM_CODEPOINT;
  }

  /**
   * Is the code point a surrogate ?
   *
   * @param codePoint
   *          is the code point to check.
   * @return <code>true</code> if the code point is a surrogate, <code>false</code> otherwise.
   */
  public static boolean isSurrogate(final int codePoint) {
    return CodePoints.isHighSurrogate(codePoint) || CodePoints.isLowSurrogate(codePoint);
  }

  /**
   * Is the code point a high surrogate ?
   *
   * @param codePoint
   *          is the code point to check.
   * @return <code>true</code> if the code point is a high surrogate, <code>false</code> otherwise.
   */
  public static boolean isHighSurrogate(final int codePoint) {
    return codePoint >= 0xD800 && codePoint <= 0xDBFF;
  }

  /**
   * Is the code point a low surrogate ?
   *
   * @param codePoint
   *          is the code point to check.
   * @return <code>true</code> if the code point is a low surrogate, <code>false</code> otherwise.
   */
  public static boolean isLowSurrogate(final int codePoint) {
    return codePoint >= 0xDC00 && codePoint <= 0xDFFF;
  }

  /**
   * Is the code point a noncharacter ?
   *
   * @param codePoint
   *          is the code point to check.
   * @return <code>true</code> if the code point is a noncharacter, <code>false</code> otherwise.
   */
  public static boolean isNoncharacter(final int codePoint) {
    final int tmp = codePoint & 0xFFFF;
    return codePoint >= 0xFDD0 && codePoint <= 0xFDEF // 32
        || tmp == 0xFFFE // All code points ending in the value FFFE
        || tmp == 0xFFFF; // All code points ending in the value FFFF
  }

  /**
   * Is the code point from a Private Use Area ?
   *
   * @param codePoint
   *          is the code point to check.
   * @return <code>true</code> if the code point is from a Private Use Area (PUA), <code>false</code> otherwise.
   */
  public static boolean isInPrivateUseArea(final int codePoint) {
    return codePoint >= 0xE000 && codePoint <= 0xF8FF // Private Use Area
        || codePoint >= 0xF0000 && codePoint <= 0xFFFFD // Supplementary Private Use Area-A
        || codePoint >= 0x100000 && codePoint <= 0x10FFFD; // Supplementary Private Use Area-B
  }

  /**
   * Is the value a code point ?
   *
   * @param value
   *          is the value to check.
   * @return <code>true</code> if the value is a code point, <code>false</code> otherwise.
   */
  public static boolean isCodePoint(final int value) {
    return value <= 0x10FFFF && !CodePoints.isSurrogate(value);
  }

  public static byte[] codePointToUtf32be(final int codePoint) {
    final byte[] array = new byte[4];
    array[0] = (byte) (codePoint >> 24);
    array[1] = (byte) (codePoint >> 16 & 0xFF);
    array[2] = (byte) (codePoint >> 8 & 0xFF);
    array[3] = (byte) (codePoint & 0xFF);
    return array;
  }

  public static byte[] codePointToUtf32le(final int codePoint) {
    final byte[] array = new byte[4];
    array[0] = (byte) (codePoint & 0xFF);
    array[1] = (byte) (codePoint >> 8 & 0xFF);
    array[2] = (byte) (codePoint >> 16 & 0xFF);
    array[3] = (byte) (codePoint >> 24);
    return array;
  }

  /**
   * <p>
   * A <code>CodePoints.Reader</code> offers the ability to:
   * <ul>
   * <li>read from a {@link UnicodeInputStream} one code point at a time without an intermediate representation ;
   * <li>peek the next code point or
   * <li>unread a code point.
   * </ul>
   * <p>
   * Unread code points are cached in a {@link CodePoints} with an initial size of 128.
   */
  public static class Reader implements QuietAutoCloseable {
    private final CodePoints buffer = new CodePoints(128);
    private final UnicodeInputStream inputStream;
    private final UtfToCodePoint utfToCodePoint;

    /**
     * Instantiates a new {@link Reader}.
     *
     * @param charset
     *          is the input encoding.
     * @param inputStream
     *          is an {@link UnicodeInputStream} containing code units.
     */
    public Reader(final Charset charset, final UnicodeInputStream inputStream) {
      this.inputStream = inputStream;
      this.utfToCodePoint = CodePoints.findUtfToCodePoint(charset);
    }

    /**
     * <p>
     * Returns the next code point.
     * <p>
     * If there is nothing more to read, returns -1.
     * 
     * @return the next code point.
     */
    public int read() {
      if (!this.buffer.isEmpty()) {
        return this.buffer.getLastAndRemove();
      } else if (this.inputStream.hasNext()) {
        return this.utfToCodePoint.toCodePoint(this.inputStream);
      } else {
        return -1;
      }
    }

    /**
     * <p>
     * Adds a code point (previously read or not) into the {@link Reader}.
     * <p>
     * This code point will be returned the next time {@link #read()} or {@link #peek()} will be called.
     * 
     * @param codePoint
     *          is the code point to add.
     */
    public void unread(final int codePoint) {
      if (codePoint != -1) {
        this.buffer.add(codePoint);
      }
    }

    /**
     * Returns the next code point without reading it.
     * 
     * @return the next code point.
     */
    public int peek() {
      final int codePoint = this.read();
      this.unread(codePoint);
      return codePoint;
    }

    /**
     * Checks if there is something to read.
     *
     * @return <code>true</code> if there is still something to read, <code>false</code> otherwise.
     */
    public boolean hasNext() {
      return !this.buffer.isEmpty() || this.inputStream.hasNext();
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public void close() {
      if (this.inputStream == null) {
        return;
      }

      this.inputStream.close();
    }
  }
}
