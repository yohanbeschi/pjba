package org.isk.pjba.assembler;

import java.util.function.Function;

import org.isk.pjba.structure.ClassFile;

public class ClassFileTestData {
  public static Function<String, ClassFile> nop() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Nop", subfix) //
        .publicStaticMethod("nop", "()V") //
        .with(e -> e.nop().return_()) //
        .build();
  }

  // 0x01
  public static Function<String, ClassFile> aconst_null() {
    return (subfix) -> ClassFileGenerator.newPublicClass("AConstNull", subfix) //
        .publicStaticMethod("aconst_null", "()Ljava/lang/Object;") //
        .with(e -> e.aconst_null().areturn()) //
        .build();
  }

  // 0x02
  public static Function<String, ClassFile> iconst_m1() {
    return (subfix) -> ClassFileGenerator.newPublicClass("IConstM1", subfix) //
        .publicStaticMethod("iconst_m1", "()I") //
        .with(e -> e.iconst_m1().ireturn()) //
        .build();
  }

  // 0x03
  public static Function<String, ClassFile> iconst_0() {
    return (subfix) -> ClassFileGenerator.newPublicClass("IConst0", subfix) //
        .publicStaticMethod("iconst_0", "()I") //
        .with(e -> e.iconst_0().ireturn()) //
        .build();
  }

  // 0x04
  public static Function<String, ClassFile> iconst_1() {
    return (subfix) -> ClassFileGenerator.newPublicClass("IConst1", subfix) //
        .publicStaticMethod("iconst_1", "()I") //
        .with(e -> e.iconst_1().ireturn()) //
        .build();
  }

  // 0x05
  public static Function<String, ClassFile> iconst_2() {
    return (subfix) -> ClassFileGenerator.newPublicClass("IConst2", subfix) //
        .publicStaticMethod("iconst_2", "()I") //
        .with(e -> e.iconst_2().ireturn()) //
        .build();
  }

  // 0x06
  public static Function<String, ClassFile> iconst_3() {
    return (subfix) -> ClassFileGenerator.newPublicClass("IConst3", subfix) //
        .publicStaticMethod("iconst_3", "()I") //
        .with(e -> e.iconst_3().ireturn()) //
        .build();
  }

  // 0x07
  public static Function<String, ClassFile> iconst_4() {
    return (subfix) -> ClassFileGenerator.newPublicClass("IConst4", subfix) //
        .publicStaticMethod("iconst_4", "()I") //
        .with(e -> e.iconst_4().ireturn()) //
        .build();
  }

  // 0x08
  public static Function<String, ClassFile> iconst_5() {
    return (subfix) -> ClassFileGenerator.newPublicClass("IConst5", subfix) //
        .publicStaticMethod("iconst_5", "()I") //
        .with(e -> e.iconst_5().ireturn()) //
        .build();
  }

  // 0x09
  public static Function<String, ClassFile> lconst_0() {
    return (subfix) -> ClassFileGenerator.newPublicClass("LConst0", subfix) //
        .publicStaticMethod("lconst_0", "()J") //
        .with(e -> e.lconst_0().lreturn()) //
        .build();
  }

  // 0x0A
  public static Function<String, ClassFile> lconst_1() {
    return (subfix) -> ClassFileGenerator.newPublicClass("LConst1", subfix) //
        .publicStaticMethod("lconst_1", "()J") //
        .with(e -> e.lconst_1().lreturn()) //
        .build();
  }

  // 0x0B
  public static Function<String, ClassFile> fconst_0() {
    return (subfix) -> ClassFileGenerator.newPublicClass("FConst0", subfix) //
        .publicStaticMethod("fconst_0", "()F") //
        .with(e -> e.fconst_0().freturn()) //
        .build();
  }

  // 0x0C
  public static Function<String, ClassFile> fconst_1() {
    return (subfix) -> ClassFileGenerator.newPublicClass("FConst1", subfix) //
        .publicStaticMethod("fconst_1", "()F") //
        .with(e -> e.fconst_1().freturn()) //
        .build();
  }

  // 0x0D
  public static Function<String, ClassFile> fconst_2() {
    return (subfix) -> ClassFileGenerator.newPublicClass("FConst2", subfix) //
        .publicStaticMethod("fconst_2", "()F") //
        .with(e -> e.fconst_2().freturn()) //
        .build();
  }

  // 0x0E
  public static Function<String, ClassFile> dconst_0() {
    return (subfix) -> ClassFileGenerator.newPublicClass("DConst0", subfix) //
        .publicStaticMethod("dconst_0", "()D") //
        .with(e -> e.dconst_0().dreturn()) //
        .build();
  }

  // 0x0F
  public static Function<String, ClassFile> dconst_1() {
    return (subfix) -> ClassFileGenerator.newPublicClass("DConst1", subfix) //
        .publicStaticMethod("dconst_1", "()D") //
        .with(e -> e.dconst_1().dreturn()) //
        .build();
  }

  // 0x10
  public static Function<String, ClassFile> bipush() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Bipush", subfix) //
        .publicStaticMethod("bipush", "()I") //
        .with(e -> e.bipush(125).ireturn()) //
        .build();
  }

  // 0x11
  public static Function<String, ClassFile> sipush() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Sipush", subfix) //
        .publicStaticMethod("sipush", "()I") //
        .with(e -> e.sipush(5_396).ireturn()) //
        .build();
  }

  // 0x12
  public static Function<String, ClassFile> ldc_string() {
    return (subfix) -> ClassFileGenerator.newPublicClass("LdcString", subfix) //
        .publicStaticMethod("ldc_string", "()Ljava/lang/String;") //
        .with(e -> e.ldc("Hello World").areturn()) //
        .build();
  }

  // 0x12
  public static Function<String, ClassFile> ldc_int() {
    return (subfix) -> ClassFileGenerator.newPublicClass("LdcInt", subfix) //
        .publicStaticMethod("ldc_int", "()I") //
        .with(e -> e.ldc(167_980_564).ireturn()) //
        .build();
  }

  // 0x12
  public static Function<String, ClassFile> ldc_float() {
    return (subfix) -> ClassFileGenerator.newPublicClass("LdcFloat", subfix) //
        .publicStaticMethod("ldc_float", "()F") //
        .with(e -> e.ldc(3.5f).freturn()) //
        .build();
  }

  // TODO: 0x13 (ldc_w)

  // 0x14
  public static Function<String, ClassFile> ldc2_w_long() {
    return (subfix) -> ClassFileGenerator.newPublicClass("LdcLong", subfix) //
        .publicStaticMethod("ldc2_w_long", "()J") //
        .with(e -> e.ldc(167_980_564_900l).lreturn()) //
        .build();
  }

  // 0x14
  public static Function<String, ClassFile> ldc2_w_double() {
    return (subfix) -> ClassFileGenerator.newPublicClass("LdcDouble", subfix) //
        .publicStaticMethod("ldc2_w_double", "()D") //
        .with(e -> e.ldc(3.578_978_979).dreturn()) //
        .build();
  }

  // 0x15
  public static Function<String, ClassFile> iload() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Iload", subfix) //
        .publicStaticMethod("iload", "(ZZZZZZZZZZI)I") //
        .with(e -> e.iload(10).ireturn()) //
        .build();
  }

  // 0x16
  public static Function<String, ClassFile> lload() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Lload", subfix) //
        .publicStaticMethod("lload", "(ZZZZZZZZZZJ)J") //
        .with(e -> e.lload(10).lreturn()) //
        .build();
  }

  // 0x17
  public static Function<String, ClassFile> fload() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Fload", subfix) //
        .publicStaticMethod("fload", "(ZZZZZZZZZZF)F") //
        .with(e -> e.fload(10).freturn()) //
        .build();
  }

  // 0x18
  public static Function<String, ClassFile> dload() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Dload", subfix) //
        .publicStaticMethod("dload", "(ZZZZZZZZZZD)D") //
        .with(e -> e.dload(10).dreturn()) //
        .build();
  }

  // 0x19
  public static Function<String, ClassFile> aload() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Aload", subfix) //
        .publicStaticMethod("aload", "(ZZZZZZZZZZLjava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload(10).areturn()) //
        .build();
  }

  // 0x1A
  public static Function<String, ClassFile> iload_0() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Iload0", subfix) //
        .publicStaticMethod("iload_0", "(I)I") //
        .with(e -> e.iload_0().ireturn()) //
        .build();
  }

  // 0x1B
  public static Function<String, ClassFile> iload_1() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Iload1", subfix) //
        .publicStaticMethod("iload_1", "(ZI)I") //
        .with(e -> e.iload_1().ireturn()) //
        .build();
  }

  // 0x1C
  public static Function<String, ClassFile> iload_2() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Iload2", subfix) //
        .publicStaticMethod("iload_2", "(ZZI)I") //
        .with(e -> e.iload_2().ireturn()) //
        .build();
  }

  // 0x1D
  public static Function<String, ClassFile> iload_3() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Iload3", subfix) //
        .publicStaticMethod("iload_3", "(ZZZI)I") //
        .with(e -> e.iload_3().ireturn()) //
        .build();
  }

  // 0x1E
  public static Function<String, ClassFile> lload_0() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Lload0", subfix) //
        .publicStaticMethod("lload_0", "(J)J") //
        .with(e -> e.lload_0().lreturn()) //
        .build();
  }

  // 0x1F
  public static Function<String, ClassFile> lload_1() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Lload1", subfix) //
        .publicStaticMethod("lload_1", "(ZJ)J") //
        .with(e -> e.lload_1().lreturn()) //
        .build();
  }

  // 0x20
  public static Function<String, ClassFile> lload_2() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Lload2", subfix) //
        .publicStaticMethod("lload_2", "(ZZJ)J") //
        .with(e -> e.lload_2().lreturn()) //
        .build();
  }

  // 0x21
  public static Function<String, ClassFile> lload_3() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Lload3", subfix) //
        .publicStaticMethod("lload_3", "(ZZZJ)J") //
        .with(e -> e.lload_3().lreturn()) //
        .build();
  }

  // 0x22
  public static Function<String, ClassFile> fload_0() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Fload0", subfix) //
        .publicStaticMethod("fload_0", "(F)F") //
        .with(e -> e.fload_0().freturn()) //
        .build();
  }

  // 0x23
  public static Function<String, ClassFile> fload_1() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Fload1", subfix) //
        .publicStaticMethod("fload_1", "(ZF)F") //
        .with(e -> e.fload_1().freturn()) //
        .build();
  }

  // 0x24
  public static Function<String, ClassFile> fload_2() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Fload2", subfix) //
        .publicStaticMethod("fload_2", "(ZZF)F") //
        .with(e -> e.fload_2().freturn()) //
        .build();
  }

  // 0x25
  public static Function<String, ClassFile> fload_3() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Fload3", subfix) //
        .publicStaticMethod("fload_3", "(ZZZF)F") //
        .with(e -> e.fload_3().freturn()) //
        .build();
  }

  // 0x26
  public static Function<String, ClassFile> dload_0() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Dload0", subfix) //
        .publicStaticMethod("dload_0", "(D)D") //
        .with(e -> e.dload_0().dreturn()) //
        .build();
  }

  // 0x27
  public static Function<String, ClassFile> dload_1() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Dload1", subfix) //
        .publicStaticMethod("dload_1", "(ZD)D") //
        .with(e -> e.dload_1().dreturn()) //
        .build();
  }

  // 0x28
  public static Function<String, ClassFile> dload_2() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Dload2", subfix) //
        .publicStaticMethod("dload_2", "(ZZD)D") //
        .with(e -> e.dload_2().dreturn()) //
        .build();
  }

  // 0x29
  public static Function<String, ClassFile> dload_3() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Dload3", subfix) //
        .publicStaticMethod("dload_3", "(ZZZD)D") //
        .with(e -> e.dload_3().dreturn()) //
        .build();
  }

  // 0x2A
  public static Function<String, ClassFile> aload_0() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Aload0", subfix) //
        .publicStaticMethod("aload_0", "(Ljava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_0().areturn()) //
        .build();
  }

  // 0x2B
  public static Function<String, ClassFile> aload_1() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Aload1", subfix) //
        .publicStaticMethod("aload_1", "(ZLjava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_1().areturn()) //
        .build();
  }

  // 0x2C
  public static Function<String, ClassFile> aload_2() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Aload2", subfix) //
        .publicStaticMethod("aload_2", "(ZZLjava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_2().areturn()) //
        .build();
  }

  // 0x2D
  public static Function<String, ClassFile> aload_3() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Aload3", subfix) //
        .publicStaticMethod("aload_3", "(ZZZLjava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_3().areturn()) //
        .build();
  }

  // TODO: 0x2E to 0x35 (Load arrays)

  // 0x36
  public static Function<String, ClassFile> istore() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Istore", subfix) //
        .publicStaticMethod("istore", "(IZZZZZZZZZI)I") //
        .with(e -> e.iload_0() //
            .istore(10) //
            .iload(10) //
            .ireturn()) //
        .build();
  }

  // 0x37
  public static Function<String, ClassFile> lstore() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Lstore", subfix) //
        .publicStaticMethod("lstore", "(JZZZZZZZZZJ)J") //
        .with(e -> e.lload_0() //
            .lstore(10) //
            .lload(10) //
            .lreturn()) //
        .build();
  }

  // 0x38
  public static Function<String, ClassFile> fstore() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Fstore", subfix) //
        .publicStaticMethod("fstore", "(FZZZZZZZZZF)F") //
        .with(e -> e.fload_0() //
            .fstore(10) //
            .fload(10) //
            .freturn()) //
        .build();
  }

  // 0x39
  public static Function<String, ClassFile> dstore() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Dstore", subfix) //
        .publicStaticMethod("dstore", "(DZZZZZZZZZD)D") //
        .with(e -> e.dload_0() //
            .dstore(10) //
            .dload(10) //
            .dreturn()) //
        .build();
  }

  // 0x3A
  public static Function<String, ClassFile> astore() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Astore", subfix) //
        .publicStaticMethod("astore", "(Ljava/lang/Integer;ZZZZZZZZZLjava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_0() //
            .astore(10) //
            .aload(10) //
            .areturn()) //
        .build();
  }

  // 0x3B
  public static Function<String, ClassFile> istore_0() {
    // v0 + 5 + v1
    return (subfix) -> ClassFileGenerator.newPublicClass("Istore0", subfix) //
        .publicStaticMethod("istore_0", "(II)I") //
        .with(e -> e.iload_0() //
            .iconst_5() //
            .iadd() //
            .istore_0() //
            .iload_1() //
            .iload_0() //
            .iadd() //
            .ireturn()) //
        .build();
  }

  // 0x3C
  public static Function<String, ClassFile> istore_1() {
    // v1 + 5 + v0
    return (subfix) -> ClassFileGenerator.newPublicClass("Istore1", subfix) //
        .publicStaticMethod("istore_1", "(II)I") //
        .with(e -> e.iload_1() //
            .iconst_5() //
            .iadd() //
            .istore_1() //
            .iload_0() //
            .iload_1() //
            .iadd() //
            .ireturn()) //
        .build();
  }

  // 0x3D
  public static Function<String, ClassFile> istore_2() {
    // v0 + v1
    return (subfix) -> ClassFileGenerator.newPublicClass("Istore2", subfix) //
        .publicStaticMethod("istore_2", "(III)I") //
        .with(e -> e.iload_0() //
            .istore_2() //
            .iload_1() //
            .iload_2() //
            .iadd() //
            .ireturn()) //
        .build();
  }

  // 0x3E
  public static Function<String, ClassFile> istore_3() {
    // v0 + v2
    return (subfix) -> ClassFileGenerator.newPublicClass("Istore3", subfix) //
        .publicStaticMethod("istore_3", "(IIII)I") //
        .with(e -> e.iload_0() //
            .istore_3() //
            .iload_2() //
            .iload_3() //
            .iadd() //
            .ireturn()) //
        .build();
  }

  // 0x3F
  public static Function<String, ClassFile> lstore_0() {
    // v0 + 5 + v1
    return (subfix) -> ClassFileGenerator.newPublicClass("Lstore0", subfix) //
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
        .build();
  }

  // 0x40
  public static Function<String, ClassFile> lstore_1() {
    // v1 + 5 + v0
    return (subfix) -> ClassFileGenerator.newPublicClass("Lstore1", subfix) //
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
        .build();
  }

  // 0x41
  public static Function<String, ClassFile> lstore_2() {
    // v0 + v2
    return (subfix) -> ClassFileGenerator.newPublicClass("Lstore2", subfix) //
        .publicStaticMethod("lstore_2", "(JJJ)J") //
        .with(e -> e.lload_0() //
            .lstore_2() //
            .lload(4) //
            .lload_2() //
            .ladd() //
            .lreturn()) //
        .build();
  }

  // 0x42
  public static Function<String, ClassFile> lstore_3() {
    // v0 + v1
    return (subfix) -> ClassFileGenerator.newPublicClass("Lstore3", subfix) //
        .publicStaticMethod("lstore_3", "(JI)J") //
        .with(e -> e.lload_0() //
            .lstore_3() //
            .iload_2() //
            .i2l() //
            .lload_3() //
            .ladd() //
            .lreturn()) //
        .build();
  }

  // 0x43
  public static Function<String, ClassFile> fstore_0() {
    // v0 + 5 + v1
    return (subfix) -> ClassFileGenerator.newPublicClass("Fstore0", subfix) //
        .publicStaticMethod("fstore_0", "(FF)F") //
        .with(e -> e.fload_0() //
            .ldc(5f) //
            .fadd() //
            .fstore_0() //
            .fload_1() //
            .fload_0() //
            .fadd() //
            .freturn()) //
        .build();
  }

  // 0x44
  public static Function<String, ClassFile> fstore_1() {
    // v1 + 5 + v0
    return (subfix) -> ClassFileGenerator.newPublicClass("Fstore1", subfix) //
        .publicStaticMethod("fstore_1", "(FF)F") //
        .with(e -> e.fload_1() //
            .ldc(5f) //
            .fadd() //
            .fstore_1() //
            .fload_0() //
            .fload_1() //
            .fadd() //
            .freturn()) //
        .build();
  }

  // 0x45
  public static Function<String, ClassFile> fstore_2() {
    // v0 + v1
    return (subfix) -> ClassFileGenerator.newPublicClass("Fstore2", subfix) //
        .publicStaticMethod("fstore_2", "(FFF)F") //
        .with(e -> e.fload_0() //
            .fstore_2() //
            .fload_1() //
            .fload_2() //
            .fadd() //
            .freturn()) //
        .build();
  }

  // 0x46
  public static Function<String, ClassFile> fstore_3() {
    // v0 + v2
    return (subfix) -> ClassFileGenerator.newPublicClass("Fstore3", subfix) //
        .publicStaticMethod("fstore_3", "(FFFF)F") //
        .with(e -> e.fload_0() //
            .fstore_3() //
            .fload_2() //
            .fload_3() //
            .fadd() //
            .freturn()) //
        .build();
  }

  // 0x47
  public static Function<String, ClassFile> dstore_0() {
    // v0 + 5 + v1
    return (subfix) -> ClassFileGenerator.newPublicClass("Dstore0", subfix) //
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
        .build();
  }

  // 0x48
  public static Function<String, ClassFile> dstore_1() {
    // v1 + 5 + v0
    return (subfix) -> ClassFileGenerator.newPublicClass("Dstore1", subfix) //
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
        .build();
  }

  // 0x49
  public static Function<String, ClassFile> dstore_2() {
    // v0 + v2
    return (subfix) -> ClassFileGenerator.newPublicClass("Dstore2", subfix) //
        .publicStaticMethod("dstore_2", "(DDD)D") //
        .with(e -> e.dload_0() //
            .dstore_2() //
            .dload(4) //
            .dload_2() //
            .dadd() //
            .dreturn()) //
        .build();
  }

  // 0x4A
  public static Function<String, ClassFile> dstore_3() {
    // v0 + v1
    return (subfix) -> ClassFileGenerator.newPublicClass("Dstore3", subfix) //
        .publicStaticMethod("dstore_3", "(DF)D") //
        .with(e -> e.dload_0() //
            .dstore_3() //
            .fload_2() //
            .f2d() //
            .dload_3() //
            .dadd() //
            .dreturn()) //
        .build();
  }

  // 0x4B
  public static Function<String, ClassFile> astore_0() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Astore0", subfix) //
        .publicStaticMethod("astore_0", "(Ljava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_0() //
            .aconst_null() //
            .astore_0() // store null
            .astore_0() // store 1st param
            .aload_0() //
            .areturn()) //
        .build();
  }

  // 0x4C
  public static Function<String, ClassFile> astore_1() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Astore1", subfix) //
        .publicStaticMethod("astore_1", "(Ljava/lang/Integer;Ljava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_0() //
            .astore_1() //
            .aload_1() //
            .areturn()) //
        .build();
  }

  // 0x4D
  public static Function<String, ClassFile> astore_2() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Astore2", subfix) //
        .publicStaticMethod("astore_2",
            "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_0() //
            .astore_2() //
            .aload_2() //
            .areturn()) //
        .build();
  }

  // 0x4E
  public static Function<String, ClassFile> astore_3() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Astore3", subfix) //
        .publicStaticMethod("astore_3",
            "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_0() //
            .astore_3() //
            .aload_3() //
            .areturn()) //
        .build();
  }

  // TODO: 0x4F to 0x56 (Store arrays)

  // 0x57
  public static Function<String, ClassFile> pop() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Pop", subfix) //
        .publicStaticMethod("pop", "()D") //
        .with(e -> e.dconst_0() //
            .iconst_1() //
            .pop() //
            .dreturn()) //
        .build();
  }

  // 0x58
  public static Function<String, ClassFile> pop2() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Pop2", subfix) //
        .publicStaticMethod("pop2", "()I") //
        .with(e -> e.iconst_3() //
            .iconst_1() //
            .iconst_1() //
            .pop2() //
            .ireturn()) //
        .build();
  }

  // 0x59
  public static Function<String, ClassFile> dup() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Dup", subfix) //
        .publicStaticMethod("dup", "()I") //
        .with(e -> e.iconst_2() //
            .iconst_1() //
            .dup() //
            .pop2() //
            .ireturn()) //
        .build();
  }

  // 0x5A
  public static Function<String, ClassFile> dup_x1() {
    return (subfix) -> ClassFileGenerator.newPublicClass("DupX1", subfix) //
        .publicStaticMethod("dup_x1", "()I") //
        .with(e -> e.iconst_2() //
            .iconst_1() //
            .dup_x1() //
            .pop2() //
            .ireturn()) //
        .build();
  }

  // 0x5B
  public static Function<String, ClassFile> dup_x2() {
    return (subfix) -> ClassFileGenerator.newPublicClass("DupX2", subfix) //
        .publicStaticMethod("dup_x2", "()I") //
        .with(e -> e.iconst_3() //
            .iconst_2() //
            .iconst_1() //
            .dup_x2() //
            .pop2() //
            .pop() //
            .ireturn()) //
        .build();
  }

  // 0x5C
  public static Function<String, ClassFile> dup2() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Dup2", subfix) //
        .publicStaticMethod("dup2", "()D") //
        .with(e -> e.dconst_1() //
            .dup2() //
            .dadd() //
            .dreturn()) //
        .build();
  }

  // 0x5D
  public static Function<String, ClassFile> dup2_x1() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Dup2X1", subfix) //
        .publicStaticMethod("dup2_x1", "()D") //
        .with(e -> e.iconst_3() //
            .dconst_1() //
            .dup2_x1() //
            .pop2() //
            .pop() //
            .dreturn()) //
        .build();
  }

  // 0x5E
  public static Function<String, ClassFile> dup2_x2() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Dup2X2", subfix) //
        .publicStaticMethod("dup2_x2", "()D") //
        .with(e -> e.dconst_0() //
            .dconst_1() //
            .dup2_x2() //
            .pop2() //
            .pop2() //
            .dreturn()) //
        .build();
  }

  // 0x5F
  public static Function<String, ClassFile> swap() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Swap", subfix) //
        .publicStaticMethod("swap", "()I") //
        .with(e -> e.iconst_1() //
            .iconst_2() //
            .swap() //
            .ireturn()) //
        .build();
  }

  // 0x60
  public static Function<String, ClassFile> iadd() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Iadd", subfix) //
        .publicStaticMethod("iadd", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .iadd() //
            .ireturn()) //
        .build();
  }

  // 0x61
  public static Function<String, ClassFile> ladd() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Ladd", subfix) //
        .publicStaticMethod("ladd", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .ladd() //
            .lreturn()) //
        .build();
  }

  // 0x62
  public static Function<String, ClassFile> fadd() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Fadd", subfix) //
        .publicStaticMethod("fadd", "(FF)F") //
        .with(e -> e.fload_0() //
            .fload_1() //
            .fadd() //
            .freturn()) //
        .build();
  }

  // 0x63
  public static Function<String, ClassFile> dadd() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Dadd", subfix) //
        .publicStaticMethod("dadd", "(DD)D") //
        .with(e -> e.dload_0() //
            .dload_2() //
            .dadd() //
            .dreturn()) //
        .build();
  }

  // 0x64
  public static Function<String, ClassFile> isub() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Isub", subfix) //
        .publicStaticMethod("isub", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .isub() //
            .ireturn()) //
        .build();
  }

  // 0x65
  public static Function<String, ClassFile> lsub() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Lsub", subfix) //
        .publicStaticMethod("lsub", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .lsub() //
            .lreturn()) //
        .build();
  }

  // 0x66
  public static Function<String, ClassFile> fsub() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Fsub", subfix) //
        .publicStaticMethod("fsub", "(FF)F") //
        .with(e -> e.fload_0() //
            .fload_1() //
            .fsub() //
            .freturn()) //
        .build();
  }

  // 0x67
  public static Function<String, ClassFile> dsub() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Dsub", subfix) //
        .publicStaticMethod("dsub", "(DD)D") //
        .with(e -> e.dload_0() //
            .dload_2() //
            .dsub() //
            .dreturn()) //
        .build();
  }

  // 0x68
  public static Function<String, ClassFile> imul() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Imul", subfix) //
        .publicStaticMethod("imul", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .imul() //
            .ireturn()) //
        .build();
  }

  // 0x69
  public static Function<String, ClassFile> lmul() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Lmul", subfix) //
        .publicStaticMethod("lmul", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .lmul() //
            .lreturn()) //
        .build();
  }

  // 0x6A
  public static Function<String, ClassFile> fmul() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Fmul", subfix) //
        .publicStaticMethod("fmul", "(FF)F") //
        .with(e -> e.fload_0() //
            .fload_1() //
            .fmul() //
            .freturn()) //
        .build();
  }

  // 0x6B
  public static Function<String, ClassFile> dmul() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Dmul", subfix) //
        .publicStaticMethod("dmul", "(DD)D") //
        .with(e -> e.dload_0() //
            .dload_2() //
            .dmul() //
            .dreturn()) //
        .build();
  }

  // 0x6C
  public static Function<String, ClassFile> idiv() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Idiv", subfix) //
        .publicStaticMethod("idiv", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .idiv() //
            .ireturn()) //
        .build();
  }

  // 0x6D
  public static Function<String, ClassFile> ldiv() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Ldiv", subfix) //
        .publicStaticMethod("ldiv", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .ldiv() //
            .lreturn()) //
        .build();
  }

  // 0x6E
  public static Function<String, ClassFile> fdiv() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Fdiv", subfix) //
        .publicStaticMethod("fdiv", "(FF)F") //
        .with(e -> e.fload_0() //
            .fload_1() //
            .fdiv() //
            .freturn()) //
        .build();
  }

  // 0x6F
  public static Function<String, ClassFile> ddiv() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Ddiv", subfix) //
        .publicStaticMethod("ddiv", "(DD)D") //
        .with(e -> e.dload_0() //
            .dload_2() //
            .ddiv() //
            .dreturn()) //
        .build();
  }

  // 0x70
  public static Function<String, ClassFile> irem() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Irem", subfix) //
        .publicStaticMethod("irem", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .irem() //
            .ireturn()) //
        .build();
  }

  // 0x71
  public static Function<String, ClassFile> lrem() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Lrem", subfix) //
        .publicStaticMethod("lrem", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .lrem() //
            .lreturn()) //
        .build();
  }

  // 0x72
  public static Function<String, ClassFile> frem() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Frem", subfix) //
        .publicStaticMethod("frem", "(FF)F") //
        .with(e -> e.fload_0() //
            .fload_1() //
            .frem() //
            .freturn()) //
        .build();
  }

  // 0x73
  public static Function<String, ClassFile> drem() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Drem", subfix) //
        .publicStaticMethod("drem", "(DD)D") //
        .with(e -> e.dload_0() //
            .dload_2() //
            .drem() //
            .dreturn()) //
        .build();
  }

  // 0x74
  public static Function<String, ClassFile> ineg() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Ineg", subfix) //
        .publicStaticMethod("ineg", "(I)I") //
        .with(e -> e.iload_0() //
            .ineg() //
            .ireturn()) //
        .build();
  }

  // 0x75
  public static Function<String, ClassFile> lneg() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Lneg", subfix) //
        .publicStaticMethod("lneg", "(J)J") //
        .with(e -> e.lload_0() //
            .lneg() //
            .lreturn()) //
        .build();
  }

  // 0x76
  public static Function<String, ClassFile> fneg() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Fneg", subfix) //
        .publicStaticMethod("fneg", "(F)F") //
        .with(e -> e.fload_0() //
            .fneg() //
            .freturn()) //
        .build();
  }

  // 0x77
  public static Function<String, ClassFile> dneg() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Dneg", subfix) //
        .publicStaticMethod("dneg", "(D)D") //
        .with(e -> e.dload_0() //
            .dneg() //
            .dreturn()) //
        .build();
  }

  //..., value1, value2 → ..., result
  // An int result is computed by shifting value1 to the left by s bit positions,
  // where s is the value of the low 5 bits of value2. (5 bits => 0 to 31)

  // 0x78
  public static Function<String, ClassFile> ishl() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Ishl", subfix) //
        .publicStaticMethod("ishl", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .ishl() //
            .ireturn()) //
        .build();
  }

  // 0x79
  public static Function<String, ClassFile> lshl() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Lshl", subfix) //
        .publicStaticMethod("lshl", "(JI)J") //
        .with(e -> e.lload_0() //
            .iload_2() //
            .lshl() //
            .lreturn()) //
        .build();
  }

  // 0x7A
  public static Function<String, ClassFile> ishr() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Ishr", subfix) //
        .publicStaticMethod("ishr", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .ishr() //
            .ireturn()) //
        .build();
  }

  // 0x7B
  public static Function<String, ClassFile> lshr() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Lshr", subfix) //
        .publicStaticMethod("lshr", "(JI)J") //
        .with(e -> e.lload_0() //
            .iload_2() //
            .lshr() //
            .lreturn()) //
        .build();
  }

  // 0x7C
  public static Function<String, ClassFile> iushr() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Iushr", subfix) //
        .publicStaticMethod("iushr", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .iushr() //
            .ireturn()) //
        .build();
  }

  // 0x7D
  public static Function<String, ClassFile> lushr() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Lushr", subfix) //
        .publicStaticMethod("lushr", "(JI)J") //
        .with(e -> e.lload_0() //
            .iload_2() //
            .lushr() //
            .lreturn()) //
        .build();
  }

  // 0x7E
  public static Function<String, ClassFile> iand() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Iand", subfix) //
        .publicStaticMethod("iand", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .iand() //
            .ireturn()) //
        .build();
  }

  // 0x7F
  public static Function<String, ClassFile> land() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Land", subfix) //
        .publicStaticMethod("land", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .land() //
            .lreturn()) //
        .build();
  }

  // 0x80
  public static Function<String, ClassFile> ior() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Ior", subfix) //
        .publicStaticMethod("ior", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .ior() //
            .ireturn()) //
        .build();
  }

  // 0x81
  public static Function<String, ClassFile> lor() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Lor", subfix) //
        .publicStaticMethod("lor", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .lor() //
            .lreturn()) //
        .build();
  }

  // 0x82
  public static Function<String, ClassFile> ixor() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Ixor", subfix) //
        .publicStaticMethod("ixor", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .ixor() //
            .ireturn()) //
        .build();
  }

  // 0x83
  public static Function<String, ClassFile> lxor() {
    return (subfix) -> ClassFileGenerator.newPublicClass("Lxor", subfix) //
        .publicStaticMethod("lxor", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .lxor() //
            .lreturn()) //
        .build();
  }

  // TODO: 0x84 (iinc)

  // 0x85
  public static Function<String, ClassFile> i2l() {
    return (subfix) -> ClassFileGenerator.newPublicClass("I2L", subfix) //
        .publicStaticMethod("i2l", "(IJ)J") //
        .with(e -> e.iload_0() //
            .i2l() //
            .lload_1() //
            .ladd() //
            .lreturn()) //
        .build();
  }

  // 0x86
  public static Function<String, ClassFile> i2f() {
    return (subfix) -> ClassFileGenerator.newPublicClass("I2F", subfix) //
        .publicStaticMethod("i2f", "(II)F") //
        .with(e -> e.iload_0() //
            .i2f() //
            .iload_1() //
            .i2f() //
            .fdiv() //
            .freturn()) //
        .build();
  }

  // 0x87
  public static Function<String, ClassFile> i2d() {
    return (subfix) -> ClassFileGenerator.newPublicClass("I2D", subfix) //
        .publicStaticMethod("i2d", "(II)D") //
        .with(e -> e.iload_0() //
            .i2d() //
            .iload_1() //
            .i2d() //
            .ddiv() //
            .dreturn()) //
        .build();
  }

  // 0x88
  public static Function<String, ClassFile> l2i() {
    return (subfix) -> ClassFileGenerator.newPublicClass("L2I", subfix) //
        .publicStaticMethod("l2i", "(J)I") //
        .with(e -> e.lload_0() //
            .l2i() //
            .ireturn()) //
        .build();
  }

  // 0x89
  public static Function<String, ClassFile> l2f() {
    return (subfix) -> ClassFileGenerator.newPublicClass("L2F", subfix) //
        .publicStaticMethod("l2f", "(JJ)F") //
        .with(e -> e.lload_0() //
            .l2f() //
            .lload_2() //
            .l2f() //
            .fdiv() //
            .freturn()) //
        .build();
  }

  // 0x8A
  public static Function<String, ClassFile> l2d() {
    return (subfix) -> ClassFileGenerator.newPublicClass("L2D", subfix) //
        .publicStaticMethod("l2d", "(JJ)D") //
        .with(e -> e.lload_0() //
            .l2d() //
            .lload_2() //
            .l2d() //
            .ddiv() //
            .dreturn()) //
        .build();
  }

  // 0x8B
  public static Function<String, ClassFile> f2i() {
    return (subfix) -> ClassFileGenerator.newPublicClass("F2I", subfix) //
        .publicStaticMethod("f2i", "(F)I") //
        .with(e -> e.fload_0() //
            .f2i() //
            .ireturn()) //
        .build();
  }

  // 0x8C
  public static Function<String, ClassFile> f2l() {
    return (subfix) -> ClassFileGenerator.newPublicClass("F2L", subfix) //
        .publicStaticMethod("f2l", "(F)J") //
        .with(e -> e.fload_0() //
            .f2l() //
            .lreturn()) //
        .build();
  }

  // 0x8D
  public static Function<String, ClassFile> f2d() {
    return (subfix) -> ClassFileGenerator.newPublicClass("F2D", subfix) //
        .publicStaticMethod("f2d", "(FD)D") //
        .with(e -> e.fload_0() //
            .f2d() //
            .dload_1() //
            .dadd() //
            .dreturn()) //
        .build();
  }

  // 0x8E
  public static Function<String, ClassFile> d2i() {
    return (subfix) -> ClassFileGenerator.newPublicClass("D2I", subfix) //
        .publicStaticMethod("d2i", "(D)I") //
        .with(e -> e.dload_0() //
            .d2i() //
            .ireturn()) //
        .build();
  }

  // 0x8F
  public static Function<String, ClassFile> d2l() {
    return (subfix) -> ClassFileGenerator.newPublicClass("D2L", subfix) //
        .publicStaticMethod("d2l", "(D)J") //
        .with(e -> e.dload_0() //
            .d2l() //
            .lreturn()) //
        .build();
  }

  // 0x90
  public static Function<String, ClassFile> d2f() {
    return (subfix) -> ClassFileGenerator.newPublicClass("D2F", subfix) //
        .publicStaticMethod("d2f", "(DF)F") //
        .with(e -> e.dload_0() //
            .d2f() //
            .fload_2() //
            .fadd() //
            .freturn()) //
        .build();
  }

  // 0x91
  public static Function<String, ClassFile> i2b() {
    return (subfix) -> ClassFileGenerator.newPublicClass("I2B", subfix) //
        .publicStaticMethod("i2b", "(I)I") //
        .with(e -> e.iload_0() //
            .i2b() //
            .ireturn()) //
        .build();
  }

  // 0x92
  public static Function<String, ClassFile> i2c() {
    return (subfix) -> ClassFileGenerator.newPublicClass("I2C", subfix) //
        .publicStaticMethod("i2c", "(I)I") //
        .with(e -> e.iload_0() //
            .i2c() //
            .ireturn()) //
        .build();
  }

  // 0x93
  public static Function<String, ClassFile> i2s() {
    return (subfix) -> ClassFileGenerator.newPublicClass("I2S", subfix) //
        .publicStaticMethod("i2s", "(I)I") //
        .with(e -> e.iload_0() //
            .i2s() //
            .ireturn()) //
        .build();
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

  public static Function<String, ClassFile> istore_load_unsigned() {
    return (subfix) -> ClassFileGenerator.newPublicClass("IstoreLoadUnsigned", subfix) //
        .publicStaticMethod("istore_load_unsigned", "()I") //
        .with(e -> e.sipush(7_687) //
            .istore(230) //
            .iload(0xe6) // 230
            .ireturn()) //
        .build();
  }

  public static Function<String, ClassFile> lstore_load_unsigned() {
    return (subfix) -> ClassFileGenerator.newPublicClass("LstoreLoadUnsigned", subfix) //
        .publicStaticMethod("lstore_load_unsigned", "()J") //
        .with(e -> e.ldc(7_687_000l) //
            .lstore(199) //
            .lload(199) //
            .lreturn()) //
        .build();
  }

  public static Function<String, ClassFile> fstore_load_unsigned() {
    return (subfix) -> ClassFileGenerator.newPublicClass("FstoreLoadUnsigned", subfix) //
        .publicStaticMethod("fstore_load_unsigned", "()F") //
        .with(e -> e.ldc(134.89f) //
            .fstore(142) //
            .fload(142) //
            .freturn()) //
        .build();
  }

  public static Function<String, ClassFile> dstore_load_unsigned() {
    return (subfix) -> ClassFileGenerator.newPublicClass("DstoreLoadUnsigned", subfix) //
        .publicStaticMethod("dstore_load_unsigned", "()D") //
        .with(e -> e.ldc(33.33) //
            .dstore(210) //
            .dload(210) //
            .dreturn()) //
        .build();
  }

  public static Function<String, ClassFile> astore_load_unsigned() {
    return (subfix) -> ClassFileGenerator.newPublicClass("AstoreLoadUnsigned", subfix) //
        .publicStaticMethod("astore_load_unsigned", "()Ljava/lang/Object;") //
        .with(e -> e.aconst_null() //
            .astore(175) //
            .aload(175) //
            .areturn()) //
        .build();
  }
}
