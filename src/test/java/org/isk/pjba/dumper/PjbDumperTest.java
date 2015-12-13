package org.isk.pjba.dumper;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import org.isk.pjba.assembler.MethodTester;
import org.isk.pjba.assembler.ParameterizedClassFile;
import org.isk.pjba.structure.ClassFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(value = Parameterized.class)
public class PjbDumperTest extends ParameterizedClassFile {

  public PjbDumperTest(final String testName, final Function<String, ClassFile> classFileGenerator,
      final MethodTester methodTester) {
    super(testName, classFileGenerator, methodTester);
  }

  @Test
  public void test() {
    final ClassFile classFile = this.classFileGenerator.apply("");
    final String pjb = PjbDumper.dump(classFile);

    final InputStream is = this.getReferencePjb(classFile.getJavaClassName());

    assertThat(pjb.getBytes(StandardCharsets.UTF_8)).isEqualTo(this.toBytes(is));
  }
}
