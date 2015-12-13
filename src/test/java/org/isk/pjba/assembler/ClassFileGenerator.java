package org.isk.pjba.assembler;

import static org.isk.pjba.structure.Method.MODIFIER_PUBLIC;
import static org.isk.pjba.structure.Method.MODIFIER_STATIC;

import java.util.function.Consumer;

import org.isk.pjba.builder.ClassFileBuilder;
import org.isk.pjba.builder.MethodBuilder;
import org.isk.pjba.structure.ClassFile;

public class ClassFileGenerator {
  private final ClassFileBuilder classFileBuilder;
  private MethodBuilder methodBuilder;

  public ClassFileGenerator(final ClassFileBuilder classFileBuilder) {
    this.classFileBuilder = classFileBuilder;
  }

  public static ClassFileGenerator newPublicClass(final String fullyQualifiedName, final String subfix) {
    final String fullyQualifiedNameSubfixed = fullyQualifiedName + subfix;
    return new ClassFileGenerator(new ClassFileBuilder(ClassFile.MODIFIER_PUBLIC, fullyQualifiedNameSubfixed));
  }

  public ClassFileGenerator publicStaticMethod(final String methodName, final String descriptor) {
    this.methodBuilder = this.classFileBuilder.newMethod(MODIFIER_PUBLIC | MODIFIER_STATIC, methodName, descriptor);
    return this;
  }

  public ClassFileGenerator with(final Consumer<MethodBuilder> consumer) {
    consumer.accept(this.methodBuilder);
    return this;
  }

  public ClassFile build() {
    return this.classFileBuilder.build();
  }
}
