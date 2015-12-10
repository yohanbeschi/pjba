package org.isk.pjba.assembler;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.assertj.core.data.Offset;

public class MethodTester {
  private final String methodName;
  private Object[] parameters;

  final List<Consumer<Class<?>>> expected = new ArrayList<>();

  public MethodTester(final String methodName) {
    super();
    this.methodName = methodName;
  }

  public static MethodTester $(final String methodName) {
    return new MethodTester(methodName);
  }

  public String getMethodName() {
    return this.methodName;
  }

  public List<Consumer<Class<?>>> getExpected() {
    return this.expected;
  }

  public MethodTester call(final Object... parameters) {
    this.parameters = parameters;
    return this;
  }

  public MethodTester assertNull() {
    final Object[] localParameters = this.parameters;
    this.expected.add(clazz -> {
      final Object obj = this.execute(clazz, localParameters);
      assertThat(obj).isNull();
    });
    return this;
  }

  public MethodTester assertInteger(final int expected) {
    final Object[] localParameters = this.parameters;
    this.expected.add(clazz -> {
      final Object obj = this.execute(clazz, localParameters);
      assertThat(obj).isInstanceOf(Integer.class);
      assertThat(obj).isEqualTo(expected);
    });
    return this;
  }

  public MethodTester assertLong(final long expected) {
    final Object[] localParameters = this.parameters;
    this.expected.add(clazz -> {
      final Object obj = this.execute(clazz, localParameters);
      assertThat(obj).isInstanceOf(Long.class);
      assertThat(obj).isEqualTo(expected);
    });
    return this;
  }

  public MethodTester assertFloat(final float expected) {
    final Object[] localParameters = this.parameters;
    this.expected.add(clazz -> {
      final Object obj = this.execute(clazz, localParameters);
      assertThat(obj).isInstanceOf(Float.class);
      assertThat((Float) obj).isCloseTo(expected, Offset.offset(0.0001f));
    });
    return this;
  }

  public MethodTester assertDouble(final double expected) {
    final Object[] localParameters = this.parameters;
    this.expected.add(clazz -> {
      final Object obj = this.execute(clazz, localParameters);
      assertThat(obj).isInstanceOf(Double.class);
      assertThat((Double) obj).isCloseTo(expected, Offset.offset(0.0001));
    });
    return this;
  }

  public MethodTester assertString(final String expected) {
    final Object[] localParameters = this.parameters;
    this.expected.add(clazz -> {
      final Object obj = this.execute(clazz, localParameters);
      assertThat(obj).isInstanceOf(String.class);
      assertThat(obj).isEqualTo(expected);
    });
    return this;
  }

  private Object execute(final Class<?> clazz, final Object[] parameters) {
    try {

      final Method[] methods = clazz.getMethods();
      Method method = null;

      for (final Method m : methods) {
        if (m.getName().equals(this.methodName)) {
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
}
