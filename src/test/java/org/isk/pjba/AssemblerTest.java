package org.isk.pjba;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;

import org.isk.pjba.assembler.MethodTester;
import org.isk.pjba.assembler.ParameterizedClassFile;
import org.isk.pjba.structure.ClassFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(value = Parameterized.class)
public class AssemblerTest extends ParameterizedClassFile {

  public AssemblerTest(final String testName, final ClassFile classFile, final MethodTester methodTester) {
    super(testName, classFile, methodTester);
  }

  @Test
  public void test() throws ClassNotFoundException {
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    final DataOutput bytecode = new DataOutputStream(baos);
    Assembler.assemble(this.classFile, bytecode);

    final String javaClass = this.classFile.getJavaClassName();
    PjbaClassLoader.CLASS_LOADER.put(javaClass, baos.toByteArray());
    final Class<?> clazz = PjbaClassLoader.CLASS_LOADER.findClass(javaClass);
    this.methodTester.getExpected().forEach(e -> e.accept(clazz));
  }
}
