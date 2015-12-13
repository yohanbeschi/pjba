package org.isk.pjba.dumper;

import org.isk.pjba.assembler.ClassFileTestData;
import org.junit.Ignore;
import org.junit.Test;

public class HexDumperTest {
  @Test
  @Ignore
  public void test() {
    System.out.println(HexDumper.dump(ClassFileTestData.nop().apply("Hex")));
  }
}
