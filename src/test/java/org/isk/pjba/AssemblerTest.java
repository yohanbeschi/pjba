package org.isk.pjba;

import org.junit.After;
import org.junit.Test;

public class AssemblerTest {

  @After
  public void tearDown() {
    PjbaClassLoader.reset();
  }

  @Test // 0x00
  public void nop() throws Exception {
    AssemblerTester.newPublicClass("Nop") //
        .publicStaticMethod("nop", "()V") //
        .with(e -> e.nop().return_()) //
        .call() //
        .assertNull();
  }

  @Test // 0x01
  public void aconst_null() {
    AssemblerTester.newPublicClass("AConstNull") //
        .publicStaticMethod("aconst_null", "()Ljava/lang/Object;") //
        .with(e -> e.aconst_null().areturn()) //
        .call() //
        .assertNull();
  }

  @Test // 0x02
  public void iconst_m1() {
    AssemblerTester.newPublicClass("IConstM1") //
        .publicStaticMethod("iconst_m1", "()I") //
        .with(e -> e.iconst_m1().ireturn()) //
        .call() //
        .assertInteger(-1);
  }

  @Test // 0x03
  public void iconst_0() {
    AssemblerTester.newPublicClass("IConst0") //
        .publicStaticMethod("iconst_0", "()I") //
        .with(e -> e.iconst_0().ireturn()) //
        .call() //
        .assertInteger(0);
  }

  @Test // 0x04
  public void iconst_1() {
    AssemblerTester.newPublicClass("IConst1") //
        .publicStaticMethod("iconst_1", "()I") //
        .with(e -> e.iconst_1().ireturn()) //
        .call() //
        .assertInteger(1);
  }

  @Test // 0x05
  public void iconst_2() {
    AssemblerTester.newPublicClass("IConst2") //
        .publicStaticMethod("iconst_2", "()I") //
        .with(e -> e.iconst_2().ireturn()) //
        .call() //
        .assertInteger(2);
  }

  @Test // 0x06
  public void iconst_3() {
    AssemblerTester.newPublicClass("IConst3") //
        .publicStaticMethod("iconst_3", "()I") //
        .with(e -> e.iconst_3().ireturn()) //
        .call() //
        .assertInteger(3);
  }

  @Test // 0x07
  public void iconst_4() {
    AssemblerTester.newPublicClass("IConst4") //
        .publicStaticMethod("iconst_4", "()I") //
        .with(e -> e.iconst_4().ireturn()) //
        .call() //
        .assertInteger(4);
  }

  @Test // 0x08
  public void iconst_5() {
    AssemblerTester.newPublicClass("IConst5") //
        .publicStaticMethod("iconst_5", "()I") //
        .with(e -> e.iconst_5().ireturn()) //
        .call() //
        .assertInteger(5);
  }

  @Test // 0x09
  public void lconst_0() {
    AssemblerTester.newPublicClass("LConst0") //
        .publicStaticMethod("lconst_0", "()J") //
        .with(e -> e.lconst_0().lreturn()) //
        .call() //
        .assertLong(0);
  }

  @Test // 0x0A
  public void lconst_1() {
    AssemblerTester.newPublicClass("LConst1") //
        .publicStaticMethod("lconst_1", "()J") //
        .with(e -> e.lconst_1().lreturn()) //
        .call() //
        .assertLong(1);
  }

  @Test // 0x0B
  public void fconst_0() {
    AssemblerTester.newPublicClass("FConst0") //
        .publicStaticMethod("fconst_0", "()F") //
        .with(e -> e.fconst_0().freturn()) //
        .call() //
        .assertFloat(0);
  }

  @Test // 0x0C
  public void fconst_1() {
    AssemblerTester.newPublicClass("FConst1") //
        .publicStaticMethod("fconst_1", "()F") //
        .with(e -> e.fconst_1().freturn()) //
        .call() //
        .assertFloat(1);
  }

  @Test // 0x0D
  public void fconst_2() {
    AssemblerTester.newPublicClass("FConst2") //
        .publicStaticMethod("fconst_2", "()F") //
        .with(e -> e.fconst_2().freturn()) //
        .call() //
        .assertFloat(2);
  }

  @Test // 0x0E
  public void dconst_0() {
    AssemblerTester.newPublicClass("DConst0") //
        .publicStaticMethod("dconst_0", "()D") //
        .with(e -> e.dconst_0().dreturn()) //
        .call() //
        .assertDouble(0);
  }

  @Test // 0x0F
  public void dconst_1() {
    AssemblerTester.newPublicClass("DConst1") //
        .publicStaticMethod("dconst_1", "()D") //
        .with(e -> e.dconst_1().dreturn()) //
        .call() //
        .assertDouble(1);
  }

  @Test // 0x10
  public void bipush() {
    AssemblerTester.newPublicClass("Bipush") //
        .publicStaticMethod("bipush", "()I") //
        .with(e -> e.bipush(125).ireturn()) //
        .call() //
        .assertInteger(125);
  }

  @Test // 0x11
  public void sipush() {
    AssemblerTester.newPublicClass("Sipush") //
        .publicStaticMethod("sipush", "()I") //
        .with(e -> e.sipush(5_396).ireturn()) //
        .call() //
        .assertInteger(5_396);
  }

  @Test // 0x12
  public void ldc_string() {
    AssemblerTester.newPublicClass("LdcString") //
        .publicStaticMethod("ldc_String", "()Ljava/lang/String;") //
        .with(e -> e.ldc("Hello World").areturn()) //
        .call() //
        .assertString("Hello World");
  }

  @Test // 0x12
  public void ldc_int() {
    AssemblerTester.newPublicClass("LdcInt") //
        .publicStaticMethod("ldc_int", "()I") //
        .with(e -> e.ldc(167_980_564).ireturn()) //
        .call() //
        .assertInteger(167_980_564);
  }

  @Test // 0x12
  public void ldc_float() {
    AssemblerTester.newPublicClass("LdcFloat") //
        .publicStaticMethod("ldc_float", "()F") //
        .with(e -> e.ldc(3.5f).freturn()) //
        .call() //
        .assertFloat(3.5f);
  }

  // TODO: 0x13 (ldc_w)

  @Test // 0x14
  public void ldc2_w_long() {
    AssemblerTester.newPublicClass("LdcLong") //
        .publicStaticMethod("ldc2_w_long", "()J") //
        .with(e -> e.ldc(167_980_564_900l).lreturn()) //
        .call() //
        .assertLong(167_980_564_900l);
  }

  @Test // 0x14
  public void ldc2_w_double() {
    AssemblerTester.newPublicClass("LdcDouble") //
        .publicStaticMethod("ldc_double", "()D") //
        .with(e -> e.ldc(3.578_978_979).dreturn()) //
        .call() //
        .assertDouble(3.578_978_979);
  }

  @Test // 0x15
  public void iload() {
    AssemblerTester.newPublicClass("Iload") //
        .publicStaticMethod("iload", "(ZZZZZZZZZZI)I") //
        .with(e -> e.iload(10).ireturn()) //
        .call(true, true, true, true, true, true, true, true, true, true, 5) //
        .assertInteger(5);
  }

  @Test // 0x16
  public void lload() {
    AssemblerTester.newPublicClass("Lload") //
        .publicStaticMethod("lload", "(ZZZZZZZZZZJ)J") //
        .with(e -> e.lload(10).lreturn()) //
        .call(true, true, true, true, true, true, true, true, true, true, 15l) //
        .assertLong(15);
  }

  @Test // 0x17
  public void fload() {
    AssemblerTester.newPublicClass("Fload") //
        .publicStaticMethod("fload", "(ZZZZZZZZZZF)F") //
        .with(e -> e.fload(10).freturn()) //
        .call(true, true, true, true, true, true, true, true, true, true, 5.5f) //
        .assertFloat(5.5f);
  }

  @Test // 0x18
  public void dload() {
    AssemblerTester.newPublicClass("Dload") //
        .publicStaticMethod("dload", "(ZZZZZZZZZZD)D") //
        .with(e -> e.dload(10).dreturn()) //
        .call(true, true, true, true, true, true, true, true, true, true, 5.9) //
        .assertDouble(5.9);
  }

  @Test // 0x19
  public void aload() {
    AssemblerTester.newPublicClass("Aload") //
        .publicStaticMethod("aload", "(ZZZZZZZZZZLjava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload(10).areturn()) //
        .call(true, true, true, true, true, true, true, true, true, true, 89) //
        .assertInteger(89);
  }

  @Test // 0x1A
  public void iload_0() {
    AssemblerTester.newPublicClass("Iload0") //
        .publicStaticMethod("iload_0", "(I)I") //
        .with(e -> e.iload_0().ireturn()) //
        .call(5) //
        .assertInteger(5);
  }

  @Test // 0x1B
  public void iload_1() {
    AssemblerTester.newPublicClass("Iload1") //
        .publicStaticMethod("iload_1", "(ZI)I") //
        .with(e -> e.iload_1().ireturn()) //
        .call(true, 5) //
        .assertInteger(5);
  }

  @Test // 0x1C
  public void iload_2() {
    AssemblerTester.newPublicClass("Iload2") //
        .publicStaticMethod("iload_2", "(ZZI)I") //
        .with(e -> e.iload_2().ireturn()) //
        .call(true, true, 5) //
        .assertInteger(5);
  }

  @Test // 0x1D
  public void iload_3() {
    AssemblerTester.newPublicClass("Iload3") //
        .publicStaticMethod("iload_3", "(ZZZI)I") //
        .with(e -> e.iload_3().ireturn()) //
        .call(true, true, true, 5) //
        .assertInteger(5);
  }

  @Test // 0x1E
  public void lload_0() {
    AssemblerTester.newPublicClass("Lload0") //
        .publicStaticMethod("lload_0", "(J)J") //
        .with(e -> e.lload_0().lreturn()) //
        .call(15) //
        .assertLong(15);
  }

  @Test // 0x1F
  public void lload_1() {
    AssemblerTester.newPublicClass("Lload1") //
        .publicStaticMethod("lload_1", "(ZJ)J") //
        .with(e -> e.lload_1().lreturn()) //
        .call(true, 15) //
        .assertLong(15);
  }

  @Test // 0x20
  public void lload_2() {
    AssemblerTester.newPublicClass("Lload2") //
        .publicStaticMethod("lload_2", "(ZZJ)J") //
        .with(e -> e.lload_2().lreturn()) //
        .call(true, true, 15) //
        .assertLong(15);
  }

  @Test // 0x21
  public void lload_3() {
    AssemblerTester.newPublicClass("Lload3") //
        .publicStaticMethod("lload_3", "(ZZZJ)J") //
        .with(e -> e.lload_3().lreturn()) //
        .call(true, true, true, 15) //
        .assertLong(15);
  }

  @Test // 0x22
  public void fload_0() {
    AssemblerTester.newPublicClass("Fload0") //
        .publicStaticMethod("fload_0", "(F)F") //
        .with(e -> e.fload_0().freturn()) //
        .call(5.5f) //
        .assertFloat(5.5f);
  }

  @Test // 0x23
  public void fload_1() {
    AssemblerTester.newPublicClass("Fload1") //
        .publicStaticMethod("fload_1", "(ZF)F") //
        .with(e -> e.fload_1().freturn()) //
        .call(true, 5.5f) //
        .assertFloat(5.5f);
  }

  @Test // 0x24
  public void fload_2() {
    AssemblerTester.newPublicClass("Fload2") //
        .publicStaticMethod("fload_2", "(ZZF)F") //
        .with(e -> e.fload_2().freturn()) //
        .call(true, true, 5.5f) //
        .assertFloat(5.5f);
  }

  @Test // 0x25
  public void fload_3() {
    AssemblerTester.newPublicClass("Fload3") //
        .publicStaticMethod("fload_3", "(ZZZF)F") //
        .with(e -> e.fload_3().freturn()) //
        .call(true, true, true, 5.5f) //
        .assertFloat(5.5f);
  }

  @Test // 0x26
  public void dload_0() {
    AssemblerTester.newPublicClass("Dload0") //
        .publicStaticMethod("dload_0", "(D)D") //
        .with(e -> e.dload_0().dreturn()) //
        .call(5.9) //
        .assertDouble(5.9);
  }

  @Test // 0x27
  public void dload_1() {
    AssemblerTester.newPublicClass("Dload1") //
        .publicStaticMethod("dload_1", "(ZD)D") //
        .with(e -> e.dload_1().dreturn()) //
        .call(true, 5.9) //
        .assertDouble(5.9);
  }

  @Test // 0x28
  public void dload_2() {
    AssemblerTester.newPublicClass("Dload2") //
        .publicStaticMethod("dload_2", "(ZZD)D") //
        .with(e -> e.dload_2().dreturn()) //
        .call(true, true, 5.9) //
        .assertDouble(5.9);
  }

  @Test // 0x29
  public void dload_3() {
    AssemblerTester.newPublicClass("Dload3") //
        .publicStaticMethod("dload_3", "(ZZZD)D") //
        .with(e -> e.dload_3().dreturn()) //
        .call(true, true, true, 5.9) //
        .assertDouble(5.9);
  }

  @Test // 0x2A
  public void aload_0() {
    AssemblerTester.newPublicClass("Aload0") //
        .publicStaticMethod("aload_0", "(Ljava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_0().areturn()) //
        .call(5000) //
        .assertInteger(5000);
  }

  @Test // 0x2B
  public void aload_1() {
    AssemblerTester.newPublicClass("Aload1") //
        .publicStaticMethod("aload_1", "(ZLjava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_1().areturn()) //
        .call(true, 5000) //
        .assertInteger(5000);
  }

  @Test // 0x2C
  public void aload_2() {
    AssemblerTester.newPublicClass("Aload2") //
        .publicStaticMethod("aload_2", "(ZZLjava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_2().areturn()) //
        .call(true, true, 5000) //
        .assertInteger(5000);
  }

  @Test // 0x2D
  public void aload_3() {
    AssemblerTester.newPublicClass("Aload3") //
        .publicStaticMethod("aload_3", "(ZZZLjava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_3().areturn()) //
        .call(true, true, true, 5000) //
        .assertInteger(5000);
  }

  // TODO: 0x2E to 0x35 (Load arrays)

  @Test // 0x36
  public void istore() {
    AssemblerTester.newPublicClass("Istore") //
        .publicStaticMethod("istore", "(IZZZZZZZZZI)I") //
        .with(e -> e.iload_0() //
            .istore(10) //
            .iload(10) //
            .ireturn()) //
        .call(2, true, true, true, true, true, true, true, true, true, 4) //
        .assertInteger(2);
  }

  @Test // 0x37
  public void lstore() {
    AssemblerTester.newPublicClass("Lstore") //
        .publicStaticMethod("lstore", "(JZZZZZZZZZJ)J") //
        .with(e -> e.lload_0() //
            .lstore(10) //
            .lload(10) //
            .lreturn()) //
        .call(2l, true, true, true, true, true, true, true, true, true, 4l) //
        .assertLong(2l);
  }

  @Test // 0x38
  public void fstore() {
    AssemblerTester.newPublicClass("Fstore") //
        .publicStaticMethod("fstore", "(FZZZZZZZZZF)F") //
        .with(e -> e.fload_0() //
            .fstore(10) //
            .fload(10) //
            .freturn()) //
        .call(2.5f, true, true, true, true, true, true, true, true, true, 4.5f) //
        .assertFloat(2.5f);
  }

  @Test // 0x39
  public void dstore() {
    AssemblerTester.newPublicClass("Dstore") //
        .publicStaticMethod("dstore", "(DZZZZZZZZZD)D") //
        .with(e -> e.dload_0() //
            .dstore(10) //
            .dload(10) //
            .dreturn()) //
        .call(2.98, true, true, true, true, true, true, true, true, true, 40.56) //
        .assertDouble(2.98);
  }

  @Test // 0x3A
  public void astore() {
    AssemblerTester.newPublicClass("Astore") //
        .publicStaticMethod("astore", "(Ljava/lang/Integer;ZZZZZZZZZLjava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_0() //
            .astore(10) //
            .aload(10) //
            .areturn()) //
        .call(2, true, true, true, true, true, true, true, true, true, 4) //
        .assertInteger(2);
  }

  @Test // 0x3B
  public void istore_0() {
    // v0 + 5 + v1
    AssemblerTester.newPublicClass("Istore0") //
        .publicStaticMethod("istore_0", "(II)I") //
        .with(e -> e.iload_0() //
            .iconst_5() //
            .iadd() //
            .istore_0() //
            .iload_1() //
            .iload_0() //
            .iadd() //
            .ireturn()) //
        .call(2, 4) //
        .assertInteger(11);
  }

  @Test // 0x3C
  public void istore_1() {
    // v1 + 5 + v0
    AssemblerTester.newPublicClass("Istore1") //
        .publicStaticMethod("istore_1", "(II)I") //
        .with(e -> e.iload_1() //
            .iconst_5() //
            .iadd() //
            .istore_1() //
            .iload_0() //
            .iload_1() //
            .iadd() //
            .ireturn()) //
        .call(2, 4) //
        .assertInteger(11);
  }

  @Test // 0x3D
  public void istore_2() {
    // v0 + v1
    AssemblerTester.newPublicClass("Istore2") //
        .publicStaticMethod("istore_2", "(III)I") //
        .with(e -> e.iload_0() //
            .istore_2() //
            .iload_1() //
            .iload_2() //
            .iadd() //
            .ireturn()) //
        .call(2, 5, 6) //
        .assertInteger(7);
  }

  @Test // 0x3E
  public void istore_3() {
    // v0 + v2
    AssemblerTester.newPublicClass("Istore3") //
        .publicStaticMethod("istore_3", "(IIII)I") //
        .with(e -> e.iload_0() //
            .istore_3() //
            .iload_2() //
            .iload_3() //
            .iadd() //
            .ireturn()) //
        .call(2, 5, 6, 7) //
        .assertInteger(8);
  }

  @Test // 0x3F
  public void lstore_0() {
    // v0 + 5 + v1
    AssemblerTester.newPublicClass("Lstore0") //
        .publicStaticMethod("lstore_0", "(JJ)J") //
        .with(e -> e.lload_0() //
            .iconst_5() //
            .i2l() //
            .ladd() //
            .lstore_0() //
            .lload_2() //
            .lload_0() //
            .ladd() //
            .lreturn()) //
        .call(2l, 4l) //
        .assertLong(11);
  }

  @Test // 0x40
  public void lstore_1() {
    // v1 + 5 + v0
    AssemblerTester.newPublicClass("Lstore1") //
        .publicStaticMethod("lstore_1", "(IJ)J") //
        .with(e -> e.lload_1() //
            .iconst_5() //
            .i2l() //
            .ladd() //
            .lstore_1() //
            .iload_0() //
            .i2l() //
            .lload_1() //
            .ladd() //
            .lreturn()) //
        .call(2, 4l) //
        .assertLong(11);
  }

  @Test // 0x41
  public void lstore_2() {
    // v0 + v2
    AssemblerTester.newPublicClass("Lstore2") //
        .publicStaticMethod("lstore_2", "(JJJ)J") //
        .with(e -> e.lload_0() //
            .lstore_2() //
            .lload(4) //
            .lload_2() //
            .ladd() //
            .lreturn()) //
        .call(2l, 5l, 6l) //
        .assertLong(8);
  }

  @Test // 0x42
  public void lstore_3() {
    // v0 + v1
    AssemblerTester.newPublicClass("Lstore3") //
        .publicStaticMethod("lstore_3", "(JI)J") //
        .with(e -> e.lload_0() //
            .lstore_3() //
            .iload_2() //
            .i2l() //
            .lload_3() //
            .ladd() //
            .lreturn()) //
        .call(2l, 5) //
        .assertLong(7);
  }

  @Test // 0x43
  public void fstore_0() {
    // v0 + 5 + v1
    AssemblerTester.newPublicClass("Fstore0") //
        .publicStaticMethod("fstore_0", "(FF)F") //
        .with(e -> e.fload_0() //
            .ldc(5f) //
            .fadd() //
            .fstore_0() //
            .fload_1() //
            .fload_0() //
            .fadd() //
            .freturn()) //
        .call(2f, 4f) //
        .assertFloat(11);
  }

  @Test // 0x44
  public void fstore_1() {
    // v1 + 5 + v0
    AssemblerTester.newPublicClass("Fstore1") //
        .publicStaticMethod("fstore_1", "(FF)F") //
        .with(e -> e.fload_1() //
            .ldc(5f) //
            .fadd() //
            .fstore_1() //
            .fload_0() //
            .fload_1() //
            .fadd() //
            .freturn()) //
        .call(2f, 4f) //
        .assertFloat(11);
  }

  @Test // 0x45
  public void fstore_2() {
    // v0 + v1
    AssemblerTester.newPublicClass("Fstore2") //
        .publicStaticMethod("fstore_2", "(FFF)F") //
        .with(e -> e.fload_0() //
            .fstore_2() //
            .fload_1() //
            .fload_2() //
            .fadd() //
            .freturn()) //
        .call(2f, 5f, 6f) //
        .assertFloat(7);
  }

  @Test // 0x46
  public void fstore_3() {
    // v0 + v2
    AssemblerTester.newPublicClass("Fstore3") //
        .publicStaticMethod("fstore_3", "(FFFF)F") //
        .with(e -> e.fload_0() //
            .fstore_3() //
            .fload_2() //
            .fload_3() //
            .fadd() //
            .freturn()) //
        .call(2f, 5f, 6f, 7f) //
        .assertFloat(8);
  }

  @Test // 0x47
  public void dstore_0() {
    // v0 + 5 + v1
    AssemblerTester.newPublicClass("Dstore0") //
        .publicStaticMethod("dstore_0", "(DD)D") //
        .with(e -> e.dload_0() //
            .ldc(5f) //
            .f2d() //
            .dadd() //
            .dstore_0() //
            .dload_2() //
            .dload_0() //
            .dadd() //
            .dreturn()) //
        .call(2., 4.) //
        .assertDouble(11);
  }

  @Test // 0x48
  public void dstore_1() {
    // v1 + 5 + v0
    AssemblerTester.newPublicClass("Dstore1") //
        .publicStaticMethod("dstore_1", "(FD)D") //
        .with(e -> e.dload_1() //
            .ldc(5f) //
            .f2d() //
            .dadd() //
            .dstore_1() //
            .fload_0() //
            .f2d() //
            .dload_1() //
            .dadd() //
            .dreturn()) //
        .call(2f, 4.) //
        .assertDouble(11);
  }

  @Test // 0x49
  public void dstore_2() {
    // v0 + v2
    AssemblerTester.newPublicClass("Dstore2") //
        .publicStaticMethod("dstore_2", "(DDD)D") //
        .with(e -> e.dload_0() //
            .dstore_2() //
            .dload(4) //
            .dload_2() //
            .dadd() //
            .dreturn()) //
        .call(2., 5., 6.) //
        .assertDouble(8);
  }

  @Test // 0x4A
  public void dstore_3() {
    // v0 + v1
    AssemblerTester.newPublicClass("Dstore3") //
        .publicStaticMethod("dstore_3", "(DF)D") //
        .with(e -> e.dload_0() //
            .dstore_3() //
            .fload_2() //
            .f2d() //
            .dload_3() //
            .dadd() //
            .dreturn()) //
        .call(2., 5f) //
        .assertDouble(7);
  }

  @Test // 0x4B
  public void astore_0() {
    AssemblerTester.newPublicClass("Astore0") //
        .publicStaticMethod("astore_0", "(Ljava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_0() //
            .aconst_null() //
            .astore_0() // store null
            .astore_0() // store 1st param
            .aload_0() //
            .areturn()) //
        .call(2) //
        .assertInteger(2);
  }

  @Test // 0x4C
  public void astore_1() {
    AssemblerTester.newPublicClass("Astore1") //
        .publicStaticMethod("astore_1", "(Ljava/lang/Integer;Ljava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_0() //
            .astore_1() //
            .aload_1() //
            .areturn()) //
        .call(2, 5) //
        .assertInteger(2);
  }

  @Test // 0x4D
  public void astore_2() {
    AssemblerTester.newPublicClass("Astore2") //
        .publicStaticMethod("astore_2",
            "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_0() //
            .astore_2() //
            .aload_2() //
            .areturn()) //
        .call(2, 5, 4) //
        .assertInteger(2);
  }

  @Test // 0x4E
  public void astore_3() {
    AssemblerTester.newPublicClass("Astore3") //
        .publicStaticMethod("astore_3",
            "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_0() //
            .astore_3() //
            .aload_3() //
            .areturn()) //
        .call(2, 5, 4, 7) //
        .assertInteger(2);
  }

  // TODO: 0x4F to 0x56 (Store arrays)

  @Test // 0x57
  public void pop() {
    AssemblerTester.newPublicClass("Pop") //
        .publicStaticMethod("pop", "()D") //
        .with(e -> e.dconst_0() //
            .iconst_1() //
            .pop() //
            .dreturn()) //
        .call() //
        .assertDouble(0);
  }

  @Test // 0x58
  public void pop2() {
    AssemblerTester.newPublicClass("Pop2") //
        .publicStaticMethod("pop2", "()I") //
        .with(e -> e.iconst_3() //
            .iconst_1() //
            .iconst_1() //
            .pop2() //
            .ireturn()) //
        .call() //
        .assertInteger(3);
  }

  @Test // 0x59
  public void dup() {
    AssemblerTester.newPublicClass("Dup") //
        .publicStaticMethod("dup", "()I") //
        .with(e -> e.iconst_2() //
            .iconst_1() //
            .dup() //
            .pop2() //
            .ireturn()) //
        .call() //
        .assertInteger(2);
  }

  @Test // 0x5A
  public void dup_x1() {
    AssemblerTester.newPublicClass("DupX1") //
        .publicStaticMethod("dup_x1", "()I") //
        .with(e -> e.iconst_2() //
            .iconst_1() //
            .dup_x1() //
            .pop2() //
            .ireturn()) //
        .call() //
        .assertInteger(1);
  }

  @Test // 0x5B
  public void dup_x2() {
    AssemblerTester.newPublicClass("DupX2") //
        .publicStaticMethod("dup_x2", "()I") //
        .with(e -> e.iconst_3() //
            .iconst_2() //
            .iconst_1() //
            .dup_x2() //
            .pop2() //
            .pop() //
            .ireturn()) //
        .call() //
        .assertInteger(1);
  }

  @Test // 0x5C
  public void dup2() {
    AssemblerTester.newPublicClass("Dup2") //
        .publicStaticMethod("dup2", "()D") //
        .with(e -> e.dconst_1() //
            .dup2() //
            .dadd() //
            .dreturn()) //
        .call() //
        .assertDouble(2);
  }

  @Test // 0x5D
  public void dup2_x1() {
    AssemblerTester.newPublicClass("Dup2X1") //
        .publicStaticMethod("dup2_x1", "()D") //
        .with(e -> e.iconst_3() //
            .dconst_1() //
            .dup2_x1() //
            .pop2() //
            .pop() //
            .dreturn()) //
        .call() //
        .assertDouble(1);
  }

  @Test // 0x5E
  public void dup2_x2() {
    AssemblerTester.newPublicClass("Dup2X2") //
        .publicStaticMethod("dup2_x2", "()D") //
        .with(e -> e.dconst_0() //
            .dconst_1() //
            .dup2_x2() //
            .pop2() //
            .pop2() //
            .dreturn()) //
        .call() //
        .assertDouble(1);
  }

  @Test // 0x5F
  public void swap() {
    AssemblerTester.newPublicClass("Swap") //
        .publicStaticMethod("swap", "()I") //
        .with(e -> e.iconst_1() //
            .iconst_2() //
            .swap() //
            .ireturn()) //
        .call() //
        .assertInteger(1);
  }

  @Test // 0x60
  public void iadd() {
    AssemblerTester.newPublicClass("Iadd") //
        .publicStaticMethod("iadd", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .iadd() //
            .ireturn()) //
        .call(5, 10) //
        .assertInteger(15);
  }

  @Test // 0x61
  public void ladd() {
    AssemblerTester.newPublicClass("Ladd") //
        .publicStaticMethod("ladd", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .ladd() //
            .lreturn()) //
        .call(15l, 100l) //
        .assertLong(115);
  }

  @Test // 0x62
  public void fadd() {
    AssemblerTester.newPublicClass("Fadd") //
        .publicStaticMethod("fadd", "(FF)F") //
        .with(e -> e.fload_0() //
            .fload_1() //
            .fadd() //
            .freturn()) //
        .call(5.5f, 10.5f) //
        .assertFloat(16);
  }

  @Test // 0x63
  public void dadd() {
    AssemblerTester.newPublicClass("Dadd") //
        .publicStaticMethod("dadd", "(DD)D") //
        .with(e -> e.dload_0() //
            .dload_2() //
            .dadd() //
            .dreturn()) //
        .call(5.9, 10.9) //
        .assertDouble(16.8);
  }

  @Test // 0x64
  public void isub() {
    AssemblerTester.newPublicClass("Isub") //
        .publicStaticMethod("isub", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .isub() //
            .ireturn()) //
        .call(5, 10) //
        .assertInteger(-5);
  }

  @Test // 0x65
  public void lsub() {
    AssemblerTester.newPublicClass("Lsub") //
        .publicStaticMethod("lsub", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .lsub() //
            .lreturn()) //
        .call(15, 100) //
        .assertLong(-85);
  }

  @Test // 0x66
  public void fsub() {
    AssemblerTester.newPublicClass("Fsub") //
        .publicStaticMethod("fsub", "(FF)F") //
        .with(e -> e.fload_0() //
            .fload_1() //
            .fsub() //
            .freturn()) //
        .call(5.5f, 10.5f) //
        .assertFloat(-5);
  }

  @Test // 0x67
  public void dsub() {
    AssemblerTester.newPublicClass("Dsub") //
        .publicStaticMethod("dsub", "(DD)D") //
        .with(e -> e.dload_0() //
            .dload_2() //
            .dsub() //
            .dreturn()) //
        .call(5.9, 10.9) //
        .assertDouble(-5);
  }

  @Test // 0x68
  public void imul() {
    AssemblerTester.newPublicClass("Imul") //
        .publicStaticMethod("imul", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .imul() //
            .ireturn()) //
        .call(5, 10) //
        .assertInteger(50);
  }

  @Test // 0x69
  public void lmul() {
    AssemblerTester.newPublicClass("Lmul") //
        .publicStaticMethod("lmul", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .lmul() //
            .lreturn()) //
        .call(15l, 100l) //
        .assertLong(1500);
  }

  @Test // 0x6A
  public void fmul() {
    AssemblerTester.newPublicClass("Fmul") //
        .publicStaticMethod("fmul", "(FF)F") //
        .with(e -> e.fload_0() //
            .fload_1() //
            .fmul() //
            .freturn()) //
        .call(5.2f, 10.5f) //
        .assertFloat(54.6f);
  }

  @Test // 0x6B
  public void dmul() {
    AssemblerTester.newPublicClass("Dmul") //
        .publicStaticMethod("dmul", "(DD)D") //
        .with(e -> e.dload_0() //
            .dload_2() //
            .dmul() //
            .dreturn()) //
        .call(5.9, 10.9) //
        .assertDouble(64.31);
  }

  @Test // 0x6C
  public void idiv() {
    AssemblerTester.newPublicClass("Idiv") //
        .publicStaticMethod("idiv", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .idiv() //
            .ireturn()) //
        .call(10, 5) //
        .assertInteger(2);
  }

  @Test // 0x6D
  public void ldiv() {
    AssemblerTester.newPublicClass("Ldiv") //
        .publicStaticMethod("ldiv", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .ldiv() //
            .lreturn()) //
        .call(150l, 10l) //
        .assertLong(15);
  }

  @Test // 0x6E
  public void fdiv() {
    AssemblerTester.newPublicClass("Fdiv") //
        .publicStaticMethod("fdiv", "(FF)F") //
        .with(e -> e.fload_0() //
            .fload_1() //
            .fdiv() //
            .freturn()) //
        .call(3.8f, 2.f) //
        .assertFloat(1.9f);
  }

  @Test // 0x6F
  public void ddiv() {
    AssemblerTester.newPublicClass("Ddiv") //
        .publicStaticMethod("ddiv", "(DD)D") //
        .with(e -> e.dload_0() //
            .dload_2() //
            .ddiv() //
            .dreturn()) //
        .call(10.0, 2.0) //
        .assertDouble(5);
  }

  @Test // 0x70
  public void irem() {
    AssemblerTester.newPublicClass("Irem") //
        .publicStaticMethod("irem", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .irem() //
            .ireturn()) //
        .call(7, 2) //
        .assertInteger(1);
  }

  @Test // 0x71
  public void lrem() {
    AssemblerTester.newPublicClass("Lrem") //
        .publicStaticMethod("lrem", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .lrem() //
            .lreturn()) //
        .call(155l, 10l) //
        .assertLong(5);
  }

  @Test // 0x72
  public void frem() {
    AssemblerTester.newPublicClass("Frem") //
        .publicStaticMethod("frem", "(FF)F") //
        .with(e -> e.fload_0() //
            .fload_1() //
            .frem() //
            .freturn()) //
        .call(0.5f, 0.3f) //
        .assertFloat(0.2f);
  }

  @Test // 0x73
  public void drem() {
    AssemblerTester.newPublicClass("Drem") //
        .publicStaticMethod("drem", "(DD)D") //
        .with(e -> e.dload_0() //
            .dload_2() //
            .drem() //
            .dreturn()) //
        .call(17.5, 15.3) //
        .assertDouble(2.2);
  }

  @Test // 0x74
  public void ineg() {
    AssemblerTester.newPublicClass("Ineg") //
        .publicStaticMethod("ineg", "(I)I") //
        .with(e -> e.iload_0() //
            .ineg() //
            .ireturn()) //
        .call(7) //
        .assertInteger(-7);
  }

  @Test // 0x75
  public void lneg() {
    AssemblerTester.newPublicClass("Lneg") //
        .publicStaticMethod("lneg", "(J)J") //
        .with(e -> e.lload_0() //
            .lneg() //
            .lreturn()) //
        .call(-155l) //
        .assertLong(155);
  }

  @Test // 0x76
  public void fneg() {
    AssemblerTester.newPublicClass("Fneg") //
        .publicStaticMethod("fneg", "(F)F") //
        .with(e -> e.fload_0() //
            .fneg() //
            .freturn()) //
        .call(0.5f) //
        .assertFloat(-0.5f);
  }

  @Test // 0x77
  public void dneg() {
    AssemblerTester.newPublicClass("Dneg") //
        .publicStaticMethod("dneg", "(D)D") //
        .with(e -> e.dload_0() //
            .dneg() //
            .dreturn()) //
        .call(-17.5) //
        .assertDouble(17.5);
  }

  //..., value1, value2 → ..., result
  // An int result is computed by shifting value1 to the left by s bit positions,
  // where s is the value of the low 5 bits of value2. (5 bits => 0 to 31)

  @Test // 0x78
  public void ishl() {
    AssemblerTester.newPublicClass("Ishl") //
        .publicStaticMethod("ishl", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .ishl() //
            .ireturn()) //
        .call(10, 32) //
        .assertInteger(10) //
        .call(1, 31) //
        .assertInteger(Integer.MIN_VALUE);
  }

  @Test // 0x79
  public void lshl() {
    AssemblerTester.newPublicClass("Lshl") //
        .publicStaticMethod("lshl", "(JI)J") //
        .with(e -> e.lload_0() //
            .iload_2() //
            .lshl() //
            .lreturn()) //
        .call(10, 64) //
        .assertLong(10) //
        .call(1, 63) //
        .assertLong(Long.MIN_VALUE);
  }

  @Test // 0x7A
  public void ishr() {
    AssemblerTester.newPublicClass("Ishr") //
        .publicStaticMethod("ishr", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .ishr() //
            .ireturn()) //
        .call(10, 32) //
        .assertInteger(10) //
        .call(Integer.MIN_VALUE, 31) //
        .assertInteger(-1);
  }

  @Test // 0x7B
  public void lshr() {
    AssemblerTester.newPublicClass("Lshr") //
        .publicStaticMethod("lshr", "(JI)J") //
        .with(e -> e.lload_0() //
            .iload_2() //
            .lshr() //
            .lreturn()) //
        .call(10, 64) //
        .assertLong(10) //
        .call(Long.MIN_VALUE, 63) //
        .assertLong(-1);
  }

  @Test // 0x7C
  public void iushr() {
    AssemblerTester.newPublicClass("Iushr") //
        .publicStaticMethod("iushr", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .iushr() //
            .ireturn()) //
        .call(Integer.MIN_VALUE, 31) //
        .assertInteger(1);
  }

  @Test // 0x7D
  public void lushr() {
    AssemblerTester.newPublicClass("Lushr") //
        .publicStaticMethod("lushr", "(JI)J") //
        .with(e -> e.lload_0() //
            .iload_2() //
            .lushr() //
            .lreturn()) //
        .call(Long.MIN_VALUE, 63) //
        .assertLong(1);
  }

  @Test // 0x7E
  public void iand() {
    AssemblerTester.newPublicClass("Iand") //
        .publicStaticMethod("iand", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .iand() //
            .ireturn()) //
        .call(5, 4) //
        .assertInteger(4);
  }

  @Test // 0x7F
  public void land() {
    AssemblerTester.newPublicClass("Land") //
        .publicStaticMethod("land", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .land() //
            .lreturn()) //
        .call(Long.MAX_VALUE, Long.MIN_VALUE) //
        .assertLong(0);
  }

  @Test // 0x80
  public void ior() {
    AssemblerTester.newPublicClass("Ior") //
        .publicStaticMethod("ior", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .ior() //
            .ireturn()) //
        .call(5, 4) //
        .assertInteger(5);
  }

  @Test // 0x81
  public void lor() {
    AssemblerTester.newPublicClass("Lor") //
        .publicStaticMethod("lor", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .lor() //
            .lreturn()) //
        .call(Long.MAX_VALUE, Long.MIN_VALUE) //
        .assertLong(-1);
  }

  @Test // 0x82
  public void ixor() {
    AssemblerTester.newPublicClass("Ixor") //
        .publicStaticMethod("ixor", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .ixor() //
            .ireturn()) //
        .call(5, 4) //
        .assertInteger(1);
  }

  @Test // 0x83
  public void lxor() {
    AssemblerTester.newPublicClass("Lxor") //
        .publicStaticMethod("lxor", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .lxor() //
            .lreturn()) //
        .call(Long.MAX_VALUE, Long.MIN_VALUE) //
        .assertLong(-1);
  }

  // TODO: 0x84 (iinc)

  @Test // 0x85
  public void i2l() {
    AssemblerTester.newPublicClass("I2L") //
        .publicStaticMethod("i2l", "(IJ)J") //
        .with(e -> e.iload_0() //
            .i2l() //
            .lload_1() //
            .ladd() //
            .lreturn()) //
        .call(5, 4l) //
        .assertLong(9);
  }

  @Test // 0x86
  public void i2f() {
    AssemblerTester.newPublicClass("I2F") //
        .publicStaticMethod("i2f", "(II)F") //
        .with(e -> e.iload_0() //
            .i2f() //
            .iload_1() //
            .i2f() //
            .fdiv() //
            .freturn()) //
        .call(5, 2) //
        .assertFloat(2.5f);
  }

  @Test // 0x87
  public void i2d() {
    AssemblerTester.newPublicClass("I2D") //
        .publicStaticMethod("i2d", "(II)D") //
        .with(e -> e.iload_0() //
            .i2d() //
            .iload_1() //
            .i2d() //
            .ddiv() //
            .dreturn()) //
        .call(10, 3) //
        .assertDouble(3.3333);
  }

  @Test // 0x88
  public void l2i() {
    AssemblerTester.newPublicClass("L2I") //
        .publicStaticMethod("l2i", "(J)I") //
        .with(e -> e.lload_0() //
            .l2i() //
            .ireturn()) //
        .call(Long.MAX_VALUE) //
        .assertInteger(-1);
  }

  @Test // 0x89
  public void l2f() {
    AssemblerTester.newPublicClass("L2F") //
        .publicStaticMethod("l2f", "(JJ)F") //
        .with(e -> e.lload_0() //
            .l2f() //
            .lload_2() //
            .l2f() //
            .fdiv() //
            .freturn()) //
        .call(5l, 2l) //
        .assertFloat(2.5f);
  }

  @Test // 0x8A
  public void l2d() {
    AssemblerTester.newPublicClass("L2D") //
        .publicStaticMethod("l2d", "(JJ)D") //
        .with(e -> e.lload_0() //
            .l2d() //
            .lload_2() //
            .l2d() //
            .ddiv() //
            .dreturn()) //
        .call(10l, 3l) //
        .assertDouble(3.3333);
  }

  @Test // 0x8B
  public void f2i() {
    AssemblerTester.newPublicClass("F2I") //
        .publicStaticMethod("f2i", "(F)I") //
        .with(e -> e.fload_0() //
            .f2i() //
            .ireturn()) //
        .call(4.56f) //
        .assertInteger(4);
  }

  @Test // 0x8C
  public void f2l() {
    AssemblerTester.newPublicClass("F2L") //
        .publicStaticMethod("f2l", "(F)J") //
        .with(e -> e.fload_0() //
            .f2l() //
            .lreturn()) //
        .call(45.908f) //
        .assertLong(45);
  }

  @Test // 0x8D
  public void f2d() {
    AssemblerTester.newPublicClass("F2D") //
        .publicStaticMethod("f2d", "(FD)D") //
        .with(e -> e.fload_0() //
            .f2d() //
            .dload_1() //
            .dadd() //
            .dreturn()) //
        .call(10.5f, 5.6f) //
        .assertDouble(16.1);
  }

  @Test // 0x8E
  public void d2i() {
    AssemblerTester.newPublicClass("D2I") //
        .publicStaticMethod("d2i", "(D)I") //
        .with(e -> e.dload_0() //
            .d2i() //
            .ireturn()) //
        .call(4.56f) //
        .assertInteger(4);
  }

  @Test // 0x8F
  public void d2l() {
    AssemblerTester.newPublicClass("D2L") //
        .publicStaticMethod("d2l", "(D)J") //
        .with(e -> e.dload_0() //
            .d2l() //
            .lreturn()) //
        .call(45.908f) //
        .assertLong(45);
  }

  @Test // 0x90
  public void d2f() {
    AssemblerTester.newPublicClass("D2F") //
        .publicStaticMethod("d2f", "(DF)F") //
        .with(e -> e.dload_0() //
            .d2f() //
            .fload_2() //
            .fadd() //
            .freturn()) //
        .call(10.5f, 5.6f) //
        .assertFloat(16.1f);
  }

  @Test // 0x91
  public void i2b() {
    AssemblerTester.newPublicClass("I2B") //
        .publicStaticMethod("i2b", "(I)I") //
        .with(e -> e.iload_0() //
            .i2b() //
            .ireturn()) //
        .call(Integer.MAX_VALUE) //
        .assertInteger(-1);
  }

  @Test // 0x92
  public void i2c() {
    AssemblerTester.newPublicClass("I2C") //
        .publicStaticMethod("i2c", "(I)I") //
        .with(e -> e.iload_0() //
            .i2c() //
            .ireturn()) //
        .call(Integer.MAX_VALUE) //
        .assertInteger(65_535);
  }

  @Test // 0x93
  public void i2s() {
    AssemblerTester.newPublicClass("I2S") //
        .publicStaticMethod("i2s", "(I)I") //
        .with(e -> e.iload_0() //
            .i2s() //
            .ireturn()) //
        .call(Integer.MAX_VALUE) //
        .assertInteger(-1);
  }

  // TODO: 0x94 to 0xAB

  // Already tested throughout this file
  // 0xAC: ireturn
  // 0xAD: lreturn
  // 0xAE: freturn
  // 0xAF: dreturn
  // 0xB0: areturn
  // 0xB1: return

  // TODO: 0xB2 to CA

  @Test
  public void istore_load_unsigned() {
    AssemblerTester.newPublicClass("IstoreLoadUnsigned") //
        .publicStaticMethod("istore_load_unsigned", "()I") //
        .call() //
        .with(e -> e.sipush(7_687) //
            .istore(230) //
            .iload(0xe6) // 230
            .ireturn()) //
        .assertInteger(7_687);
  }

  @Test
  public void lstore_load_unsigned() {
    AssemblerTester.newPublicClass("LstoreLoadUnsigned") //
        .publicStaticMethod("lstore_load_unsigned", "()J") //
        .call() //
        .with(e -> e.ldc(7_687_000l) //
            .lstore(199) //
            .lload(199) //
            .lreturn()) //
        .assertLong(7_687_000);
  }

  @Test
  public void fstore_load_unsigned() {
    AssemblerTester.newPublicClass("FstoreLoadUnsigned") //
        .publicStaticMethod("fstore_load_unsigned", "()F") //
        .call() //
        .with(e -> e.ldc(134.89f) //
            .fstore(142) //
            .fload(142) //
            .freturn()) //
        .assertFloat(134.89f);
  }

  @Test
  public void dstore_load_unsigned() {
    AssemblerTester.newPublicClass("DstoreLoadUnsigned") //
        .publicStaticMethod("dstore_load_unsigned", "()D") //
        .call() //
        .with(e -> e.ldc(33.33) //
            .dstore(210) //
            .dload(210) //
            .dreturn()) //
        .assertDouble(33.33);
  }

  @Test
  public void astore_load_unsigned() {
    AssemblerTester.newPublicClass("AstoreLoadUnsigned") //
        .publicStaticMethod("astore_load_unsigned", "()Ljava/lang/Object;") //
        .call() //
        .with(e -> e.aconst_null() //
            .astore(175) //
            .aload(175) //
            .areturn()) //
        .assertNull();
  }

  // TODO: Limits bipush/sipush/ldc

}
