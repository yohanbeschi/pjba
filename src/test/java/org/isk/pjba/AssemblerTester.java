package org.isk.pjba;

import static org.assertj.core.api.Assertions.assertThat;
import static org.isk.pjba.structure.Method.MODIFIER_PUBLIC;
import static org.isk.pjba.structure.Method.MODIFIER_STATIC;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.lang.reflect.Method;
import java.util.function.Consumer;

import org.assertj.core.data.Offset;
import org.isk.pjba.builder.ClassFileBuilder;
import org.isk.pjba.builder.MethodBuilder;
import org.isk.pjba.structure.ClassFile;

public class AssemblerTester {

  private final ClassFileBuilder classFileBuilder;
  private MethodBuilder methodBuilder;
  private String lastMethod;
  private String methodToExecute;
  private Object[] parameters;

  public AssemblerTester(final ClassFileBuilder classFileBuilder) {
    this.classFileBuilder = classFileBuilder;
  }

  public static AssemblerTester newPublicClass(final String fullyQualifiedName) {
    return new AssemblerTester(new ClassFileBuilder(ClassFile.MODIFIER_PUBLIC, fullyQualifiedName));
  }

  public AssemblerTester publicStaticMethod(final String methodName, final String descriptor) {
    this.lastMethod = methodName;
    this.methodBuilder = this.classFileBuilder.newMethod(MODIFIER_PUBLIC | MODIFIER_STATIC, methodName, descriptor);
    return this;
  }

  public AssemblerTester call(final Object... parameters) {
    this.parameters = parameters;
    this.methodToExecute = this.lastMethod;
    return this;
  }

  public AssemblerTester with(final Consumer<MethodBuilder> consumer) {
    consumer.accept(this.methodBuilder);
    return this;
  }

  private Object buildClassAndExecute() {
    final ClassFile classFile = this.classFileBuilder.build();
    return this.execute(classFile, this.methodToExecute, this.parameters);
  }

  private Object execute(final ClassFile classFile, final String methodName, final Object[] parameters) {
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    final DataOutput bytecode = new DataOutputStream(baos);
    Assembler.assemble(classFile, bytecode);

    try {
      final String javaClass = classFile.getJavaClassName();
      PjbaClassLoader.CLASS_LOADER.put(javaClass, baos.toByteArray());
      final Class<?> clazz = PjbaClassLoader.CLASS_LOADER.findClass(javaClass);

      final Method[] methods = clazz.getMethods();
      Method method = null;
      ;

      for (final Method m : methods) {
        if (m.getName().equals(methodName)) {
          method = m;
          break;
        }
      }

      if (parameters == null) {
        return method.invoke(null);
      } else {
        return method.invoke(null, parameters);
      }
    } catch (final Exception e) {
      throw new RuntimeException("Error", e);
    }
  }

  public AssemblerTester assertNull() {
    final Object obj = this.buildClassAndExecute();
    assertThat(obj).isNull();
    return this;
  }

  public AssemblerTester assertInteger(final int i) {
    final Object obj = this.buildClassAndExecute();
    assertThat(obj).isInstanceOf(Integer.class);
    assertThat(obj).isEqualTo(i);
    return this;
  }

  public AssemblerTester assertLong(final long l) {
    final Object obj = this.buildClassAndExecute();
    assertThat(obj).isInstanceOf(Long.class);
    assertThat(obj).isEqualTo(l);
    return this;
  }

  public AssemblerTester assertFloat(final float f) {
    final Object obj = this.buildClassAndExecute();
    assertThat(obj).isInstanceOf(Float.class);
    assertThat((Float) obj).isCloseTo(f, Offset.offset(0.0001f));
    return this;
  }

  public AssemblerTester assertDouble(final double d) {
    final Object obj = this.buildClassAndExecute();
    assertThat(obj).isInstanceOf(Double.class);
    assertThat((Double) obj).isCloseTo(d, Offset.offset(0.0001));
    return this;
  }

  public AssemblerTester assertString(final String s) {
    final Object obj = this.buildClassAndExecute();
    assertThat(obj).isInstanceOf(String.class);
    assertThat(obj).isEqualTo(s);
    return this;
  }
}
