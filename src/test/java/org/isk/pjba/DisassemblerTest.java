package org.isk.pjba;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.util.function.Function;

import org.isk.pjba.assembler.MethodTester;
import org.isk.pjba.assembler.ParameterizedClassFile;
import org.isk.pjba.structure.ClassFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(value = Parameterized.class)
public class DisassemblerTest extends ParameterizedClassFile {

  public DisassemblerTest(final String testName, final Function<String, ClassFile> classFileGenerator,
      final MethodTester methodTester) {
    super(testName, classFileGenerator, methodTester);
  }

  @Test
  public void test() throws ClassNotFoundException {
    final ClassFile classFile = this.classFileGenerator.apply("Generated");
    final String javaClass = classFile.getJavaClassName();

    // File
    final InputStream is = this.getReferenceClass(javaClass);

    // Disassemble
    final byte[] expected = this.toBytes(is);
    final ByteArrayInputStream bais = new ByteArrayInputStream(expected);
    final ClassFile disassembledClassFile = Disassembler.disassemble(new DataInputStream(bais));

    // Reassemble
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    Assembler.assemble(disassembledClassFile, new DataOutputStream(baos));

    final byte[] actual = baos.toByteArray();
    PjbaClassLoader.CLASS_LOADER.put(javaClass, actual);
    final Class<?> clazz = PjbaClassLoader.CLASS_LOADER.findClass(javaClass);
    this.methodTester.getExpected().forEach(e -> e.accept(clazz));

    assertThat(actual).isEqualTo(expected);
  }
}
