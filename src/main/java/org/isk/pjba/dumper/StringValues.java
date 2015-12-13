package org.isk.pjba.dumper;

import org.isk.pjba.structure.ClassFile;
import org.isk.pjba.structure.Constant;
import org.isk.pjba.structure.Constant.ConstantPoolEntry;
import org.isk.pjba.structure.Method;

public interface StringValues {

  public static String constantTagName(final int tag) {
    switch (tag) {
    case 1:
      return "UTF8";
    case 3:
      return "Integer";
    case 4:
      return "Float";
    case 5:
      return "Long";
    case 6:
      return "Double";
    case 7:
      return "Class";
    case 8:
      return "FieldRef";
    case 9:
      return "MethodRef";
    case 10:
      return "MethodRef";
    case 11:
      return "InterfaceMethodRef";
    case 12:
      return "NameAndType";
    }
    return null;
  }

  public static String getClassModifiers(final int accessFlags) {
    final StringBuilder sb = new StringBuilder();

    if ((accessFlags & ClassFile.MODIFIER_PUBLIC) == ClassFile.MODIFIER_PUBLIC) {
      sb.append("public ");
    }

    if ((accessFlags & ClassFile.MODIFIER_FINAL) == ClassFile.MODIFIER_FINAL) {
      sb.append("final ");
    }

    if ((accessFlags & ClassFile.MODIFIER_SUPER) == ClassFile.MODIFIER_SUPER) {
      sb.append("super ");
    }

    if ((accessFlags & ClassFile.MODIFIER_INTERFACE) == ClassFile.MODIFIER_INTERFACE) {
      sb.append("interface ");
    }

    if ((accessFlags & ClassFile.MODIFIER_ABSTRACT) == ClassFile.MODIFIER_ABSTRACT) {
      sb.append("abstract ");
    }

    return sb.toString();
  }

  public static String getMethodModifiers(final int accessFlags) {
    final StringBuilder sb = new StringBuilder();

    if ((accessFlags & Method.MODIFIER_PUBLIC) == Method.MODIFIER_PUBLIC) {
      sb.append("public ");
    }

    if ((accessFlags & Method.MODIFIER_PRIVATE) == Method.MODIFIER_PRIVATE) {
      sb.append("private ");
    }

    if ((accessFlags & Method.MODIFIER_PROTECTED) == Method.MODIFIER_PROTECTED) {
      sb.append("protected ");
    }

    if ((accessFlags & Method.MODIFIER_STATIC) == Method.MODIFIER_STATIC) {
      sb.append("static ");
    }

    if ((accessFlags & Method.MODIFIER_FINAL) == Method.MODIFIER_FINAL) {
      sb.append("final ");
    }

    if ((accessFlags & Method.MODIFIER_SYNCHRONIZED) == Method.MODIFIER_SYNCHRONIZED) {
      sb.append("synchronized ");
    }

    if ((accessFlags & Method.MODIFIER_NATIVE) == Method.MODIFIER_NATIVE) {
      sb.append("native ");
    }

    if ((accessFlags & Method.MODIFIER_ABSTRACT) == Method.MODIFIER_ABSTRACT) {
      sb.append("abstract ");
    }

    if ((accessFlags & Method.MODIFIER_STRICTFP) == Method.MODIFIER_STRICTFP) {
      sb.append("strictfp ");
    }

    return sb.toString();
  }

  public static String getPrintableConstant(final int index, final ClassFile classFile) {
    final ConstantPoolEntry constant = classFile.getConstant(index);

    switch (constant.tag) {
    case 3:
      final Constant.Integer cInteger = (Constant.Integer) constant;
      return String.valueOf(cInteger.integer);
    case 4:
      final Constant.Float cFloat = (Constant.Float) constant;
      return String.valueOf(cFloat.floatValue) + "f";
    case 5:
      final Constant.Long cLong = (Constant.Long) constant;
      return String.valueOf(cLong.longValue) + "l";
    case 6:
      final Constant.Double cDouble = (Constant.Double) constant;
      return String.valueOf(cDouble.doubleValue);
    case 8:
      final Constant.String cString = (Constant.String) constant;
      final String value = ((Constant.UTF8) classFile.getConstant(cString.utf8Index)).value;
      return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    default:
      return null;
    }
  }
}
