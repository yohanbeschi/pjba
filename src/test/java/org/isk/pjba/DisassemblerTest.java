package org.isk.pjba;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.isk.pjba.assembler.ClassFileGenerator;
import org.isk.pjba.assembler.MethodTester;
import org.isk.pjba.assembler.ParameterizedClassFile;
import org.isk.pjba.structure.ClassFile;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(value = Parameterized.class)
public class DisassemblerTest extends ParameterizedClassFile {

  @BeforeClass
  public static void init() {
    ClassFileGenerator.subfixClassName("Disassambled");
  }

  @AfterClass
  public static void clear() {
    ClassFileGenerator.subfixClassName("");
  }

  public DisassemblerTest(final String testName, final ClassFile classFile, final MethodTester methodTester) {
    super(testName, classFile, methodTester);
  }

  @Test
  public void test() throws ClassNotFoundException {
    // Assemble
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    Assembler.assemble(this.classFile, new DataOutputStream(baos));

    // Disassemble
    final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    final ClassFile disassembledClassFile = Disassembler.disassemble(new DataInputStream(bais));

    // Reassemble
    final String javaClass = disassembledClassFile.getJavaClassName();
    baos = new ByteArrayOutputStream();
    Assembler.assemble(disassembledClassFile, new DataOutputStream(baos));

    PjbaClassLoader.CLASS_LOADER.put(javaClass, baos.toByteArray());
    final Class<?> clazz = PjbaClassLoader.CLASS_LOADER.findClass(javaClass);
    this.methodTester.getExpected().forEach(e -> e.accept(clazz));
  }
}
