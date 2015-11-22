package org.isk.pjba.unicode;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

import org.isk.pjba.inputstream.QuietAutoCloseable;
import org.isk.pjba.inputstream.QuietAutoCloseableException;
import org.isk.pjba.unicode.exception.UnicodeException;

/**
 * <p>
 * An <code>UnicodeInputStream</code> offers the ability to push back bytes already read and a {@link #hasNext()}
 * method.
 * <p>
 * Unlike the standard {@link InputStream#read()}, if the end of the steam has been reached, the {@link #read()} method
 * will throw an exception.
 */
public class UnicodeInputStream implements QuietAutoCloseable {

  final private PushbackInputStream inputStream;

  /**
   * <p>
   * Instantiates a new {@link UnicodeInputStream}.
   *
   * @param bytes
   *          are the bytes that will be read.
   * @throws UnicodeException
   *           if the byte array is <code>null</code> or empty.
   */
  public UnicodeInputStream(final byte[] bytes) {
    super();
    if (bytes == null || bytes.length == 0) {
      throw new UnicodeException("Impossible to instantiate an UnicodeInputStream, there is no bytes to read.");
    }
    this.inputStream = new PushbackInputStream(new ByteArrayInputStream(bytes), 128);
  }

  /**
   * <p>
   * Instantiates a new {@link UnicodeInputStream}.
   *
   * @param inputStream
   *          is the {@link InputStream} that will be read.
   *
   * @throws UnicodeException
   *           if the {@link InputStream} is <code>null</code>.
   */
  public UnicodeInputStream(final InputStream inputStream) {
    super();
    if (inputStream == null) {
      throw new UnicodeException("Impossible to instantiate an UnicodeInputStream, the InputStream is null.");
    }
    this.inputStream = new PushbackInputStream(inputStream, 128);
  }

  /**
   * Checks if there is something to read.
   *
   * @return <code>true</code> if there is still something to read, <code>false</code> otherwise.
   * @throws UnicodeException
   *           if there is nothing to be read or the {@link InputStream} can't be read.
   */
  public boolean hasNext() {
    try {
      final int next = this.inputStream.read();
      if (next == -1) {
        return false;
      } else {
        this.inputStream.unread(next);
        return true;
      }
    } catch (final IOException e) {
      throw new UnicodeException("Something went wrong while reading this UnicodeInputStream!", e);
    }
  }

  /**
   * <p>
   * Reads the next byte.
   *
   * @return the next byte.
   * @throws UnicodeException
   *           if there is nothing to be read or the {@link InputStream} can't be read.
   */
  public int read() {
    try {
      final int next = this.inputStream.read();

      if (next == -1) {
        throw new UnicodeException("This UnicodeInputStream has been read completely!");
      } else {
        return next;
      }
    } catch (final IOException e) {
      throw new UnicodeException("Something went wrong while reading this UnicodeInputStream!", e);
    }
  }

  /**
   * <p>
   * Add a byte into the {@link UnicodeInputStream}.
   * <p>
   * 128 bytes can be unread before the next read.
   *
   * <p>
   * Note that even if we usually unread a byte previously read, it is possible to unread any byte, even before starting
   * to 'read' the input stream.
   *
   * @param b
   *          is the byte to unread.
   * @throws UnicodeException
   *           if the <code>byte</code> can't be unread.
   */
  public void unread(final int b) {
    try {
      this.inputStream.unread(b);
    } catch (final IOException e) {
      throw new UnicodeException("Something went wrong while unreading this UnicodeInputStream!", e);
    }
  }

  /*
   * {@inheritDoc}
   */
  @Override
  public void close() {
    if (this.inputStream == null) {
      return;
    }

    try {
      this.inputStream.close();
    } catch (final IOException e) {
      throw new QuietAutoCloseableException("Something went wrong will trying to close input stream.", e);
    }
  }
}
