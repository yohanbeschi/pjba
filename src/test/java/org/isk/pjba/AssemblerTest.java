package org.isk.pjba;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.function.Function;

import org.isk.pjba.assembler.MethodTester;
import org.isk.pjba.assembler.ParameterizedClassFile;
import org.isk.pjba.structure.ClassFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(value = Parameterized.class)
public class AssemblerTest extends ParameterizedClassFile {

  public AssemblerTest(final String testName, final Function<String, ClassFile> classFileGenerator,
      final MethodTester methodTester) {
    super(testName, classFileGenerator, methodTester);
  }

  @Test
  public void test() throws ClassNotFoundException {
    final ClassFile classFile = this.classFileGenerator.apply("Assembled");
    final ByteArrayOutputStream baos = this.assemble(classFile);
    final String javaClass = classFile.getJavaClassName();

    PjbaClassLoader.CLASS_LOADER.put(javaClass, baos.toByteArray());
    final Class<?> clazz = PjbaClassLoader.CLASS_LOADER.findClass(javaClass);
    this.methodTester.getExpected().forEach(e -> e.accept(clazz));

    this.compareFiles();
  }

  private void compareFiles() {
    final ClassFile classFile = this.classFileGenerator.apply("Generated");
    final ByteArrayOutputStream baos = this.assemble(classFile);
    final String javaClass = classFile.getJavaClassName();

    final InputStream is = Thread.currentThread().getContextClassLoader()
        .getResourceAsStream("reference/class/" + javaClass + ".class");
    final byte[] expected = this.toBytes(is);
    assertThat(baos.toByteArray()).isEqualTo(expected);
  }
}
