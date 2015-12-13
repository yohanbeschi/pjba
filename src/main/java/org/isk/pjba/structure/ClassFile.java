package org.isk.pjba.structure;

import org.isk.pjba.structure.Constant.ConstantPoolEntry;
import org.isk.pjba.structure.attribute.constraint.ClassAttribute;
import org.isk.pjba.util.Ascii;
import org.isk.pjba.util.PjbaList;
import org.isk.pjba.visitor.Visitable;
import org.isk.pjba.visitor.Visitor;

public class ClassFile implements Visitable {
  final public static int MODIFIER_PUBLIC = 0x0001;
  final public static int MODIFIER_FINAL = 0x0010;
  final public static int MODIFIER_SUPER = 0x0020;
  final public static int MODIFIER_ABSTRACT = 0x0200;
  final public static int MODIFIER_INTERFACE = 0x0400;

  final private int magicNumber = 0xcafebabe;
  private int version = 0x30; // 48.0 = 0x00 (minor version) | 0x30 (major version) = Java 1.4

  private PjbaList<Constant.ConstantPoolEntry> constantPool = new PjbaList<>();

  private int accessFlags = MODIFIER_SUPER;

  private int thisClass;
  private int superClass;

  private int[] interfaces = new int[0];

  private PjbaList<Field> fields = new PjbaList<>();
  private PjbaList<Method> methods = new PjbaList<>();
  private PjbaList<ClassAttribute> attributes = new PjbaList<>();

  private String fullyQualifiedJavaClassName;
  private String className;
  private String directories;

  public ClassFile() {

  }

  public ClassFile(final String fullyQualifiedName) {
    this.parseName(fullyQualifiedName);

    this.constantPool.add(null); // Index 0 reserved by the JVM

    // This
    final int classNameIndex = this.addConstantUTF8(fullyQualifiedName);
    this.thisClass = this.addConstantClass(classNameIndex);

    // Parent - Hard coded for now
    final String superName = "java/lang/Object";
    final int superNameIndex = this.addConstantUTF8(superName);
    this.superClass = this.addConstantClass(superNameIndex);
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Support methods
  // -------------------------------------------------------------------------------------------------------------------

  private void parseName(final String fullyQualifiedName) {
    this.fullyQualifiedJavaClassName = fullyQualifiedName.replace(Ascii.SLASH, Ascii.PERIOD);
    int index = fullyQualifiedName.lastIndexOf(Ascii.SLASH);

    if (index >= 0) {
      this.directories = fullyQualifiedName.substring(0, ++index);
      this.className = fullyQualifiedName.substring(index);
    } else {
      this.className = fullyQualifiedName;
    }
  }

  // -------------------------------------------------------------------------------------------------------------------
  // External getters
  // -------------------------------------------------------------------------------------------------------------------

  public String getClassName() {
    return this.className;
  }

  public String getJavaClassName() {
    return this.fullyQualifiedJavaClassName;
  }

  public String getDirectories() {
    return this.directories;
  }

  // -------------------------------------------------------------------------------------------------------------------
  // ConstantPool methods
  // -------------------------------------------------------------------------------------------------------------------

  public void setVersion(final int version) {
    this.version = version;
  }

  public void setConstantPool(final PjbaList<Constant.ConstantPoolEntry> constantPool) {
    this.constantPool = constantPool;
  }

  public void setAccessFlags(final int accessFlags) {
    this.accessFlags = accessFlags;
  }

  public void addAccessFlags(final int accessFlags) {
    this.accessFlags |= accessFlags;
  }

  public void setThisClass(final int thisClass) {
    this.thisClass = thisClass;
  }

  public void setSuperClass(final int superClass) {
    this.superClass = superClass;
  }

  public void setInterfaces(final int[] interfaces) {
    this.interfaces = interfaces;
  }

  public void setFields(final PjbaList<Field> fields) {
    this.fields = fields;
  }

  public void setMethods(final PjbaList<Method> methods) {
    this.methods = methods;
  }

  public void setAttributes(final PjbaList<ClassAttribute> attributes) {
    this.attributes = attributes;
  }

  public void setFullyQualifiedClassName(final String fullyQualifiedClassName) {
    this.parseName(fullyQualifiedClassName);
  }

  // -------------------------------------------------------------------------------------------------------------------
  // ConstantPool methods
  // -------------------------------------------------------------------------------------------------------------------

  public int addConstantClass(final int utf8Index) {
    final Constant.Class constant = new Constant.Class(utf8Index);
    return this.addConstant(constant);
  }

  public int addConstantString(final int utf8Index) {
    final Constant.String constant = new Constant.String(utf8Index);
    return this.addConstant(constant);
  }

  public int addConstantInteger(final int value) {
    final Constant.Integer constant = new Constant.Integer(value);
    return this.addConstant(constant);
  }

  public int addConstantFloat(final float value) {
    final Constant.Float constant = new Constant.Float(value);
    return this.addConstant(constant);
  }

  public int addConstantLong(final long value) {
    final Constant.Long constant = new Constant.Long(value);
    final int index = this.addConstant(constant);
    this.constantPool.add(null);
    return index;
  }

  public int addConstantDouble(final double value) {
    final Constant.Double constant = new Constant.Double(value);
    final int index = this.addConstant(constant);
    this.constantPool.add(null);
    return index;
  }

  public int addConstantUTF8(final String value) {
    final Constant.UTF8 constant = new Constant.UTF8(value);
    return this.addConstant(constant);
  }

  private int addConstant(final Constant.ConstantPoolEntry constant) {
    final int index = this.constantPool.indexOf(constant);

    if (index == -1) {
      this.constantPool.add(constant);
      return this.getCurrentCPIndex();
    } else {
      return index;
    }
  }

  public int getCurrentCPIndex() {
    return this.constantPool.size() - 1;
  }

  public ConstantPoolEntry getConstant(final int index) {
    return this.constantPool.get(index);
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Method methods
  // -------------------------------------------------------------------------------------------------------------------

  public void addMethod(final Method method) {
    this.methods.add(method);
  }

  public PjbaList<Method> getMethods() {
    return this.methods;
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Overrides
  // -------------------------------------------------------------------------------------------------------------------

  @Override
  public void accept(final Visitor visitor) {
    visitor.visitMagicNumber(this.magicNumber);
    visitor.visitVersion(this.version);
    visitor.visitConstantPoolSize(this.constantPool.size());
    this.constantPool.accept(visitor);
    visitor.visitClassAccessFlags(this.accessFlags);
    visitor.visitThisClass(this.thisClass);
    visitor.visitSuperClass(this.superClass);
    visitor.visitInterfacesSize(this.interfaces.length);
    visitor.visitFieldsSize(this.fields.size());
    this.fields.accept(visitor);
    visitor.visitMethodsSize(this.methods.size());
    this.methods.accept(visitor);
    visitor.visitClassAttributeSize(this.attributes.size());
    this.attributes.accept(visitor);
  }
}
