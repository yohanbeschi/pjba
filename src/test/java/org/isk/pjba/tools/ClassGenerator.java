package org.isk.pjba.tools;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.function.Function;

import org.isk.pjba.Assembler;
import org.isk.pjba.assembler.MethodTester;
import org.isk.pjba.assembler.ParameterizedClassFile;
import org.isk.pjba.structure.ClassFile;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(value = Parameterized.class)
public class ClassGenerator extends ParameterizedClassFile {

  public ClassGenerator(final String testName, final Function<String, ClassFile> classFileGenerator,
      final MethodTester methodTester) {
    super(testName, classFileGenerator, methodTester);
  }

  @Test
  @Ignore
  public void test() throws Exception {
    final ClassFile classFile = this.classFileGenerator.apply("Generated");

    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    final DataOutput bytecode = new DataOutputStream(baos);
    Assembler.assemble(classFile, bytecode);

    final File file = new File("src/test/resources/reference/class/" + classFile.getJavaClassName() + ".class");
    try (FileOutputStream fos = new FileOutputStream(file)) {
      fos.write(baos.toByteArray());
    }
  }
}
