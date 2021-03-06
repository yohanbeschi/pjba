package org.isk.pjba.structure;

import java.io.UnsupportedEncodingException;

import org.isk.pjba.structure.attribute.constraint.MethodAttribute;
import org.isk.pjba.util.Ascii;
import org.isk.pjba.util.PjbaList;
import org.isk.pjba.visitor.Visitable;
import org.isk.pjba.visitor.Visitor;

public class Method implements Visitable {
  final private static String ENCODING = "UTF-8";

  final public static int MODIFIER_PUBLIC = 0x0001;
  final public static int MODIFIER_PRIVATE = 0x0002;
  final public static int MODIFIER_PROTECTED = 0x0004;
  final public static int MODIFIER_STATIC = 0x0008;
  final public static int MODIFIER_FINAL = 0x0010;
  final public static int MODIFIER_SYNCHRONIZED = 0x0020;
  final public static int MODIFIER_NATIVE = 0x0100;
  final public static int MODIFIER_ABSTRACT = 0x0400;
  final public static int MODIFIER_STRICTFP = 0x0800;

  private int accessFlags;
  private int nameIndex;
  private int descriptorIndex;

  private PjbaList<MethodAttribute> attributes = new PjbaList<>();

  public void setAccessFlags(final int accessFlags) {
    this.accessFlags = accessFlags;
  }

  public void addAccessFlags(final int accessFlags) {
    this.accessFlags |= accessFlags;
  }

  public void setNameIndex(final int utf8NameIndex) {
    this.nameIndex = utf8NameIndex;
  }

  public void setDescriptorIndex(final int utf8DescriptorIndex) {
    this.descriptorIndex = utf8DescriptorIndex;
  }

  public void setAttributes(final PjbaList<MethodAttribute> attributes) {
    this.attributes = attributes;
  }

  public void addAttibute(final MethodAttribute attribute) {
    this.attributes.add(attribute);
  }

  public int countParameters(final String methodDescriptor) {
    try {
      final byte[] descriptor = methodDescriptor.getBytes(ENCODING);

      int index = 1; // Step over the first element: Left parenthesis
      int locals = 0;
      int previousLocals = locals;

      for (;;) {
        final long pack = this.countOneParameter(descriptor, index, locals);
        locals = (int) (pack & 0xffff);
        index = (int) (pack >>> 32);

        // We encountered a right bracket or a void
        if (locals == previousLocals) {
          break;
        }

        previousLocals = locals;
      }

      return locals;
    } catch (final UnsupportedEncodingException e) {
      throw new RuntimeException("Should never happen", e);
    }
  }

  private long countOneParameter(final byte[] descriptor, int index, int locals) {
    byte character = descriptor[index++];

    switch (character) {
    case Ascii.UPPERCASE_B: // byte
    case Ascii.UPPERCASE_C: // char
    case Ascii.UPPERCASE_F: // float
    case Ascii.UPPERCASE_I: // int
    case Ascii.UPPERCASE_S: // short
    case Ascii.UPPERCASE_Z: // boolean
      locals++;
      break;
    case Ascii.UPPERCASE_J: // long
    case Ascii.UPPERCASE_D: // double
      locals += 2;
      break;
    case Ascii.LEFT_BRACKET: // array
      locals++;
      final long pack = this.countOneParameter(descriptor, index, 0); // ignore locals
      index = (int) (pack >>> 32);
      break;
    case Ascii.UPPERCASE_L: // reference
      locals++;
      while (index < descriptor.length && (character = descriptor[index++]) != Ascii.SEMICOLON)
        ;
      break;
    }

    // Locals maximum value = 65,535
    // Arrays maximum size in Java = 2,147,483,647
    return (long) index << 32 | locals;
  }

  @Override
  public void accept(final Visitor visitor) {
    visitor.visitMethodAccessFlags(this.accessFlags);
    visitor.visitMethodNameIndex(this.nameIndex);
    visitor.visitMethodDescriptorIndex(this.descriptorIndex);
    visitor.visitMethodAttributesSize(this.attributes.size());
    this.attributes.accept(visitor);
  }
}
