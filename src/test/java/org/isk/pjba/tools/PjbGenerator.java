package org.isk.pjba.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import org.isk.pjba.assembler.MethodTester;
import org.isk.pjba.assembler.ParameterizedClassFile;
import org.isk.pjba.dumper.PjbDumper;
import org.isk.pjba.structure.ClassFile;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(value = Parameterized.class)
public class PjbGenerator extends ParameterizedClassFile {

  public PjbGenerator(final String testName, final Function<String, ClassFile> classFileGenerator,
      final MethodTester methodTester) {
    super(testName, classFileGenerator, methodTester);
  }

  @Test
  @Ignore
  public void test() throws Exception {
    final ClassFile classFile = this.classFileGenerator.apply("");
    final String pjb = PjbDumper.dump(classFile);

    final File file = new File("src/test/resources/reference/pjb/" + classFile.getJavaClassName() + ".pjb");
    try (FileOutputStream fos = new FileOutputStream(file)) {
      fos.write(pjb.getBytes(StandardCharsets.UTF_8));
    }
  }
}
