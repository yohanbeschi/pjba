package org.isk.pjba.assembler;

import org.isk.pjba.structure.ClassFile;

public class ClassFileTestData {
  public static ClassFile nop() {
    return ClassFileGenerator.newPublicClass("Nop") //
        .publicStaticMethod("nop", "()V") //
        .with(e -> e.nop().return_()) //
        .build();
  }

  // 0x01
  public static ClassFile aconst_null() {
    return ClassFileGenerator.newPublicClass("AConstNull") //
        .publicStaticMethod("aconst_null", "()Ljava/lang/Object;") //
        .with(e -> e.aconst_null().areturn()) //
        .build();
  }

  // 0x02
  public static ClassFile iconst_m1() {
    return ClassFileGenerator.newPublicClass("IConstM1") //
        .publicStaticMethod("iconst_m1", "()I") //
        .with(e -> e.iconst_m1().ireturn()) //
        .build();
  }

  // 0x03
  public static ClassFile iconst_0() {
    return ClassFileGenerator.newPublicClass("IConst0") //
        .publicStaticMethod("iconst_0", "()I") //
        .with(e -> e.iconst_0().ireturn()) //
        .build();
  }

  // 0x04
  public static ClassFile iconst_1() {
    return ClassFileGenerator.newPublicClass("IConst1") //
        .publicStaticMethod("iconst_1", "()I") //
        .with(e -> e.iconst_1().ireturn()) //
        .build();
  }

  // 0x05
  public static ClassFile iconst_2() {
    return ClassFileGenerator.newPublicClass("IConst2") //
        .publicStaticMethod("iconst_2", "()I") //
        .with(e -> e.iconst_2().ireturn()) //
        .build();
  }

  // 0x06
  public static ClassFile iconst_3() {
    return ClassFileGenerator.newPublicClass("IConst3") //
        .publicStaticMethod("iconst_3", "()I") //
        .with(e -> e.iconst_3().ireturn()) //
        .build();
  }

  // 0x07
  public static ClassFile iconst_4() {
    return ClassFileGenerator.newPublicClass("IConst4") //
        .publicStaticMethod("iconst_4", "()I") //
        .with(e -> e.iconst_4().ireturn()) //
        .build();
  }

  // 0x08
  public static ClassFile iconst_5() {
    return ClassFileGenerator.newPublicClass("IConst5") //
        .publicStaticMethod("iconst_5", "()I") //
        .with(e -> e.iconst_5().ireturn()) //
        .build();
  }

  // 0x09
  public static ClassFile lconst_0() {
    return ClassFileGenerator.newPublicClass("LConst0") //
        .publicStaticMethod("lconst_0", "()J") //
        .with(e -> e.lconst_0().lreturn()) //
        .build();
  }

  // 0x0A
  public static ClassFile lconst_1() {
    return ClassFileGenerator.newPublicClass("LConst1") //
        .publicStaticMethod("lconst_1", "()J") //
        .with(e -> e.lconst_1().lreturn()) //
        .build();
  }

  // 0x0B
  public static ClassFile fconst_0() {
    return ClassFileGenerator.newPublicClass("FConst0") //
        .publicStaticMethod("fconst_0", "()F") //
        .with(e -> e.fconst_0().freturn()) //
        .build();
  }

  // 0x0C
  public static ClassFile fconst_1() {
    return ClassFileGenerator.newPublicClass("FConst1") //
        .publicStaticMethod("fconst_1", "()F") //
        .with(e -> e.fconst_1().freturn()) //
        .build();
  }

  // 0x0D
  public static ClassFile fconst_2() {
    return ClassFileGenerator.newPublicClass("FConst2") //
        .publicStaticMethod("fconst_2", "()F") //
        .with(e -> e.fconst_2().freturn()) //
        .build();
  }

  // 0x0E
  public static ClassFile dconst_0() {
    return ClassFileGenerator.newPublicClass("DConst0") //
        .publicStaticMethod("dconst_0", "()D") //
        .with(e -> e.dconst_0().dreturn()) //
        .build();
  }

  // 0x0F
  public static ClassFile dconst_1() {
    return ClassFileGenerator.newPublicClass("DConst1") //
        .publicStaticMethod("dconst_1", "()D") //
        .with(e -> e.dconst_1().dreturn()) //
        .build();
  }

  // 0x10
  public static ClassFile bipush() {
    return ClassFileGenerator.newPublicClass("Bipush") //
        .publicStaticMethod("bipush", "()I") //
        .with(e -> e.bipush(125).ireturn()) //
        .build();
  }

  // 0x11
  public static ClassFile sipush() {
    return ClassFileGenerator.newPublicClass("Sipush") //
        .publicStaticMethod("sipush", "()I") //
        .with(e -> e.sipush(5_396).ireturn()) //
        .build();
  }

  // 0x12
  public static ClassFile ldc_string() {
    return ClassFileGenerator.newPublicClass("LdcString") //
        .publicStaticMethod("ldc_string", "()Ljava/lang/String;") //
        .with(e -> e.ldc("Hello World").areturn()) //
        .build();
  }

  // 0x12
  public static ClassFile ldc_int() {
    return ClassFileGenerator.newPublicClass("LdcInt") //
        .publicStaticMethod("ldc_int", "()I") //
        .with(e -> e.ldc(167_980_564).ireturn()) //
        .build();
  }

  // 0x12
  public static ClassFile ldc_float() {
    return ClassFileGenerator.newPublicClass("LdcFloat") //
        .publicStaticMethod("ldc_float", "()F") //
        .with(e -> e.ldc(3.5f).freturn()) //
        .build();
  }

  // TODO: 0x13 (ldc_w)

  // 0x14
  public static ClassFile ldc2_w_long() {
    return ClassFileGenerator.newPublicClass("LdcLong") //
        .publicStaticMethod("ldc2_w_long", "()J") //
        .with(e -> e.ldc(167_980_564_900l).lreturn()) //
        .build();
  }

  // 0x14
  public static ClassFile ldc2_w_double() {
    return ClassFileGenerator.newPublicClass("LdcDouble") //
        .publicStaticMethod("ldc2_w_double", "()D") //
        .with(e -> e.ldc(3.578_978_979).dreturn()) //
        .build();
  }

  // 0x15
  public static ClassFile iload() {
    return ClassFileGenerator.newPublicClass("Iload") //
        .publicStaticMethod("iload", "(ZZZZZZZZZZI)I") //
        .with(e -> e.iload(10).ireturn()) //
        .build();
  }

  // 0x16
  public static ClassFile lload() {
    return ClassFileGenerator.newPublicClass("Lload") //
        .publicStaticMethod("lload", "(ZZZZZZZZZZJ)J") //
        .with(e -> e.lload(10).lreturn()) //
        .build();
  }

  // 0x17
  public static ClassFile fload() {
    return ClassFileGenerator.newPublicClass("Fload") //
        .publicStaticMethod("fload", "(ZZZZZZZZZZF)F") //
        .with(e -> e.fload(10).freturn()) //
        .build();
  }

  // 0x18
  public static ClassFile dload() {
    return ClassFileGenerator.newPublicClass("Dload") //
        .publicStaticMethod("dload", "(ZZZZZZZZZZD)D") //
        .with(e -> e.dload(10).dreturn()) //
        .build();
  }

  // 0x19
  public static ClassFile aload() {
    return ClassFileGenerator.newPublicClass("Aload") //
        .publicStaticMethod("aload", "(ZZZZZZZZZZLjava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload(10).areturn()) //
        .build();
  }

  // 0x1A
  public static ClassFile iload_0() {
    return ClassFileGenerator.newPublicClass("Iload0") //
        .publicStaticMethod("iload_0", "(I)I") //
        .with(e -> e.iload_0().ireturn()) //
        .build();
  }

  // 0x1B
  public static ClassFile iload_1() {
    return ClassFileGenerator.newPublicClass("Iload1") //
        .publicStaticMethod("iload_1", "(ZI)I") //
        .with(e -> e.iload_1().ireturn()) //
        .build();
  }

  // 0x1C
  public static ClassFile iload_2() {
    return ClassFileGenerator.newPublicClass("Iload2") //
        .publicStaticMethod("iload_2", "(ZZI)I") //
        .with(e -> e.iload_2().ireturn()) //
        .build();
  }

  // 0x1D
  public static ClassFile iload_3() {
    return ClassFileGenerator.newPublicClass("Iload3") //
        .publicStaticMethod("iload_3", "(ZZZI)I") //
        .with(e -> e.iload_3().ireturn()) //
        .build();
  }

  // 0x1E
  public static ClassFile lload_0() {
    return ClassFileGenerator.newPublicClass("Lload0") //
        .publicStaticMethod("lload_0", "(J)J") //
        .with(e -> e.lload_0().lreturn()) //
        .build();
  }

  // 0x1F
  public static ClassFile lload_1() {
    return ClassFileGenerator.newPublicClass("Lload1") //
        .publicStaticMethod("lload_1", "(ZJ)J") //
        .with(e -> e.lload_1().lreturn()) //
        .build();
  }

  // 0x20
  public static ClassFile lload_2() {
    return ClassFileGenerator.newPublicClass("Lload2") //
        .publicStaticMethod("lload_2", "(ZZJ)J") //
        .with(e -> e.lload_2().lreturn()) //
        .build();
  }

  // 0x21
  public static ClassFile lload_3() {
    return ClassFileGenerator.newPublicClass("Lload3") //
        .publicStaticMethod("lload_3", "(ZZZJ)J") //
        .with(e -> e.lload_3().lreturn()) //
        .build();
  }

  // 0x22
  public static ClassFile fload_0() {
    return ClassFileGenerator.newPublicClass("Fload0") //
        .publicStaticMethod("fload_0", "(F)F") //
        .with(e -> e.fload_0().freturn()) //
        .build();
  }

  // 0x23
  public static ClassFile fload_1() {
    return ClassFileGenerator.newPublicClass("Fload1") //
        .publicStaticMethod("fload_1", "(ZF)F") //
        .with(e -> e.fload_1().freturn()) //
        .build();
  }

  // 0x24
  public static ClassFile fload_2() {
    return ClassFileGenerator.newPublicClass("Fload2") //
        .publicStaticMethod("fload_2", "(ZZF)F") //
        .with(e -> e.fload_2().freturn()) //
        .build();
  }

  // 0x25
  public static ClassFile fload_3() {
    return ClassFileGenerator.newPublicClass("Fload3") //
        .publicStaticMethod("fload_3", "(ZZZF)F") //
        .with(e -> e.fload_3().freturn()) //
        .build();
  }

  // 0x26
  public static ClassFile dload_0() {
    return ClassFileGenerator.newPublicClass("Dload0") //
        .publicStaticMethod("dload_0", "(D)D") //
        .with(e -> e.dload_0().dreturn()) //
        .build();
  }

  // 0x27
  public static ClassFile dload_1() {
    return ClassFileGenerator.newPublicClass("Dload1") //
        .publicStaticMethod("dload_1", "(ZD)D") //
        .with(e -> e.dload_1().dreturn()) //
        .build();
  }

  // 0x28
  public static ClassFile dload_2() {
    return ClassFileGenerator.newPublicClass("Dload2") //
        .publicStaticMethod("dload_2", "(ZZD)D") //
        .with(e -> e.dload_2().dreturn()) //
        .build();
  }

  // 0x29
  public static ClassFile dload_3() {
    return ClassFileGenerator.newPublicClass("Dload3") //
        .publicStaticMethod("dload_3", "(ZZZD)D") //
        .with(e -> e.dload_3().dreturn()) //
        .build();
  }

  // 0x2A
  public static ClassFile aload_0() {
    return ClassFileGenerator.newPublicClass("Aload0") //
        .publicStaticMethod("aload_0", "(Ljava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_0().areturn()) //
        .build();
  }

  // 0x2B
  public static ClassFile aload_1() {
    return ClassFileGenerator.newPublicClass("Aload1") //
        .publicStaticMethod("aload_1", "(ZLjava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_1().areturn()) //
        .build();
  }

  // 0x2C
  public static ClassFile aload_2() {
    return ClassFileGenerator.newPublicClass("Aload2") //
        .publicStaticMethod("aload_2", "(ZZLjava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_2().areturn()) //
        .build();
  }

  // 0x2D
  public static ClassFile aload_3() {
    return ClassFileGenerator.newPublicClass("Aload3") //
        .publicStaticMethod("aload_3", "(ZZZLjava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_3().areturn()) //
        .build();
  }

  // TODO: 0x2E to 0x35 (Load arrays)

  // 0x36
  public static ClassFile istore() {
    return ClassFileGenerator.newPublicClass("Istore") //
        .publicStaticMethod("istore", "(IZZZZZZZZZI)I") //
        .with(e -> e.iload_0() //
            .istore(10) //
            .iload(10) //
            .ireturn()) //
        .build();
  }

  // 0x37
  public static ClassFile lstore() {
    return ClassFileGenerator.newPublicClass("Lstore") //
        .publicStaticMethod("lstore", "(JZZZZZZZZZJ)J") //
        .with(e -> e.lload_0() //
            .lstore(10) //
            .lload(10) //
            .lreturn()) //
        .build();
  }

  // 0x38
  public static ClassFile fstore() {
    return ClassFileGenerator.newPublicClass("Fstore") //
        .publicStaticMethod("fstore", "(FZZZZZZZZZF)F") //
        .with(e -> e.fload_0() //
            .fstore(10) //
            .fload(10) //
            .freturn()) //
        .build();
  }

  // 0x39
  public static ClassFile dstore() {
    return ClassFileGenerator.newPublicClass("Dstore") //
        .publicStaticMethod("dstore", "(DZZZZZZZZZD)D") //
        .with(e -> e.dload_0() //
            .dstore(10) //
            .dload(10) //
            .dreturn()) //
        .build();
  }

  // 0x3A
  public static ClassFile astore() {
    return ClassFileGenerator.newPublicClass("Astore") //
        .publicStaticMethod("astore", "(Ljava/lang/Integer;ZZZZZZZZZLjava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_0() //
            .astore(10) //
            .aload(10) //
            .areturn()) //
        .build();
  }

  // 0x3B
  public static ClassFile istore_0() {
    // v0 + 5 + v1
    return ClassFileGenerator.newPublicClass("Istore0") //
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
  public static ClassFile istore_1() {
    // v1 + 5 + v0
    return ClassFileGenerator.newPublicClass("Istore1") //
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
  public static ClassFile istore_2() {
    // v0 + v1
    return ClassFileGenerator.newPublicClass("Istore2") //
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
  public static ClassFile istore_3() {
    // v0 + v2
    return ClassFileGenerator.newPublicClass("Istore3") //
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
  public static ClassFile lstore_0() {
    // v0 + 5 + v1
    return ClassFileGenerator.newPublicClass("Lstore0") //
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
  public static ClassFile lstore_1() {
    // v1 + 5 + v0
    return ClassFileGenerator.newPublicClass("Lstore1") //
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
  public static ClassFile lstore_2() {
    // v0 + v2
    return ClassFileGenerator.newPublicClass("Lstore2") //
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
  public static ClassFile lstore_3() {
    // v0 + v1
    return ClassFileGenerator.newPublicClass("Lstore3") //
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
  public static ClassFile fstore_0() {
    // v0 + 5 + v1
    return ClassFileGenerator.newPublicClass("Fstore0") //
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
  public static ClassFile fstore_1() {
    // v1 + 5 + v0
    return ClassFileGenerator.newPublicClass("Fstore1") //
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
  public static ClassFile fstore_2() {
    // v0 + v1
    return ClassFileGenerator.newPublicClass("Fstore2") //
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
  public static ClassFile fstore_3() {
    // v0 + v2
    return ClassFileGenerator.newPublicClass("Fstore3") //
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
  public static ClassFile dstore_0() {
    // v0 + 5 + v1
    return ClassFileGenerator.newPublicClass("Dstore0") //
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
  public static ClassFile dstore_1() {
    // v1 + 5 + v0
    return ClassFileGenerator.newPublicClass("Dstore1") //
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
  public static ClassFile dstore_2() {
    // v0 + v2
    return ClassFileGenerator.newPublicClass("Dstore2") //
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
  public static ClassFile dstore_3() {
    // v0 + v1
    return ClassFileGenerator.newPublicClass("Dstore3") //
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
  public static ClassFile astore_0() {
    return ClassFileGenerator.newPublicClass("Astore0") //
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
  public static ClassFile astore_1() {
    return ClassFileGenerator.newPublicClass("Astore1") //
        .publicStaticMethod("astore_1", "(Ljava/lang/Integer;Ljava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_0() //
            .astore_1() //
            .aload_1() //
            .areturn()) //
        .build();
  }

  // 0x4D
  public static ClassFile astore_2() {
    return ClassFileGenerator.newPublicClass("Astore2") //
        .publicStaticMethod("astore_2",
            "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;)Ljava/lang/Integer;") //
        .with(e -> e.aload_0() //
            .astore_2() //
            .aload_2() //
            .areturn()) //
        .build();
  }

  // 0x4E
  public static ClassFile astore_3() {
    return ClassFileGenerator.newPublicClass("Astore3") //
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
  public static ClassFile pop() {
    return ClassFileGenerator.newPublicClass("Pop") //
        .publicStaticMethod("pop", "()D") //
        .with(e -> e.dconst_0() //
            .iconst_1() //
            .pop() //
            .dreturn()) //
        .build();
  }

  // 0x58
  public static ClassFile pop2() {
    return ClassFileGenerator.newPublicClass("Pop2") //
        .publicStaticMethod("pop2", "()I") //
        .with(e -> e.iconst_3() //
            .iconst_1() //
            .iconst_1() //
            .pop2() //
            .ireturn()) //
        .build();
  }

  // 0x59
  public static ClassFile dup() {
    return ClassFileGenerator.newPublicClass("Dup") //
        .publicStaticMethod("dup", "()I") //
        .with(e -> e.iconst_2() //
            .iconst_1() //
            .dup() //
            .pop2() //
            .ireturn()) //
        .build();
  }

  // 0x5A
  public static ClassFile dup_x1() {
    return ClassFileGenerator.newPublicClass("DupX1") //
        .publicStaticMethod("dup_x1", "()I") //
        .with(e -> e.iconst_2() //
            .iconst_1() //
            .dup_x1() //
            .pop2() //
            .ireturn()) //
        .build();
  }

  // 0x5B
  public static ClassFile dup_x2() {
    return ClassFileGenerator.newPublicClass("DupX2") //
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
  public static ClassFile dup2() {
    return ClassFileGenerator.newPublicClass("Dup2") //
        .publicStaticMethod("dup2", "()D") //
        .with(e -> e.dconst_1() //
            .dup2() //
            .dadd() //
            .dreturn()) //
        .build();
  }

  // 0x5D
  public static ClassFile dup2_x1() {
    return ClassFileGenerator.newPublicClass("Dup2X1") //
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
  public static ClassFile dup2_x2() {
    return ClassFileGenerator.newPublicClass("Dup2X2") //
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
  public static ClassFile swap() {
    return ClassFileGenerator.newPublicClass("Swap") //
        .publicStaticMethod("swap", "()I") //
        .with(e -> e.iconst_1() //
            .iconst_2() //
            .swap() //
            .ireturn()) //
        .build();
  }

  // 0x60
  public static ClassFile iadd() {
    return ClassFileGenerator.newPublicClass("Iadd") //
        .publicStaticMethod("iadd", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .iadd() //
            .ireturn()) //
        .build();
  }

  // 0x61
  public static ClassFile ladd() {
    return ClassFileGenerator.newPublicClass("Ladd") //
        .publicStaticMethod("ladd", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .ladd() //
            .lreturn()) //
        .build();
  }

  // 0x62
  public static ClassFile fadd() {
    return ClassFileGenerator.newPublicClass("Fadd") //
        .publicStaticMethod("fadd", "(FF)F") //
        .with(e -> e.fload_0() //
            .fload_1() //
            .fadd() //
            .freturn()) //
        .build();
  }

  // 0x63
  public static ClassFile dadd() {
    return ClassFileGenerator.newPublicClass("Dadd") //
        .publicStaticMethod("dadd", "(DD)D") //
        .with(e -> e.dload_0() //
            .dload_2() //
            .dadd() //
            .dreturn()) //
        .build();
  }

  // 0x64
  public static ClassFile isub() {
    return ClassFileGenerator.newPublicClass("Isub") //
        .publicStaticMethod("isub", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .isub() //
            .ireturn()) //
        .build();
  }

  // 0x65
  public static ClassFile lsub() {
    return ClassFileGenerator.newPublicClass("Lsub") //
        .publicStaticMethod("lsub", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .lsub() //
            .lreturn()) //
        .build();
  }

  // 0x66
  public static ClassFile fsub() {
    return ClassFileGenerator.newPublicClass("Fsub") //
        .publicStaticMethod("fsub", "(FF)F") //
        .with(e -> e.fload_0() //
            .fload_1() //
            .fsub() //
            .freturn()) //
        .build();
  }

  // 0x67
  public static ClassFile dsub() {
    return ClassFileGenerator.newPublicClass("Dsub") //
        .publicStaticMethod("dsub", "(DD)D") //
        .with(e -> e.dload_0() //
            .dload_2() //
            .dsub() //
            .dreturn()) //
        .build();
  }

  // 0x68
  public static ClassFile imul() {
    return ClassFileGenerator.newPublicClass("Imul") //
        .publicStaticMethod("imul", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .imul() //
            .ireturn()) //
        .build();
  }

  // 0x69
  public static ClassFile lmul() {
    return ClassFileGenerator.newPublicClass("Lmul") //
        .publicStaticMethod("lmul", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .lmul() //
            .lreturn()) //
        .build();
  }

  // 0x6A
  public static ClassFile fmul() {
    return ClassFileGenerator.newPublicClass("Fmul") //
        .publicStaticMethod("fmul", "(FF)F") //
        .with(e -> e.fload_0() //
            .fload_1() //
            .fmul() //
            .freturn()) //
        .build();
  }

  // 0x6B
  public static ClassFile dmul() {
    return ClassFileGenerator.newPublicClass("Dmul") //
        .publicStaticMethod("dmul", "(DD)D") //
        .with(e -> e.dload_0() //
            .dload_2() //
            .dmul() //
            .dreturn()) //
        .build();
  }

  // 0x6C
  public static ClassFile idiv() {
    return ClassFileGenerator.newPublicClass("Idiv") //
        .publicStaticMethod("idiv", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .idiv() //
            .ireturn()) //
        .build();
  }

  // 0x6D
  public static ClassFile ldiv() {
    return ClassFileGenerator.newPublicClass("Ldiv") //
        .publicStaticMethod("ldiv", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .ldiv() //
            .lreturn()) //
        .build();
  }

  // 0x6E
  public static ClassFile fdiv() {
    return ClassFileGenerator.newPublicClass("Fdiv") //
        .publicStaticMethod("fdiv", "(FF)F") //
        .with(e -> e.fload_0() //
            .fload_1() //
            .fdiv() //
            .freturn()) //
        .build();
  }

  // 0x6F
  public static ClassFile ddiv() {
    return ClassFileGenerator.newPublicClass("Ddiv") //
        .publicStaticMethod("ddiv", "(DD)D") //
        .with(e -> e.dload_0() //
            .dload_2() //
            .ddiv() //
            .dreturn()) //
        .build();
  }

  // 0x70
  public static ClassFile irem() {
    return ClassFileGenerator.newPublicClass("Irem") //
        .publicStaticMethod("irem", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .irem() //
            .ireturn()) //
        .build();
  }

  // 0x71
  public static ClassFile lrem() {
    return ClassFileGenerator.newPublicClass("Lrem") //
        .publicStaticMethod("lrem", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .lrem() //
            .lreturn()) //
        .build();
  }

  // 0x72
  public static ClassFile frem() {
    return ClassFileGenerator.newPublicClass("Frem") //
        .publicStaticMethod("frem", "(FF)F") //
        .with(e -> e.fload_0() //
            .fload_1() //
            .frem() //
            .freturn()) //
        .build();
  }

  // 0x73
  public static ClassFile drem() {
    return ClassFileGenerator.newPublicClass("Drem") //
        .publicStaticMethod("drem", "(DD)D") //
        .with(e -> e.dload_0() //
            .dload_2() //
            .drem() //
            .dreturn()) //
        .build();
  }

  // 0x74
  public static ClassFile ineg() {
    return ClassFileGenerator.newPublicClass("Ineg") //
        .publicStaticMethod("ineg", "(I)I") //
        .with(e -> e.iload_0() //
            .ineg() //
            .ireturn()) //
        .build();
  }

  // 0x75
  public static ClassFile lneg() {
    return ClassFileGenerator.newPublicClass("Lneg") //
        .publicStaticMethod("lneg", "(J)J") //
        .with(e -> e.lload_0() //
            .lneg() //
            .lreturn()) //
        .build();
  }

  // 0x76
  public static ClassFile fneg() {
    return ClassFileGenerator.newPublicClass("Fneg") //
        .publicStaticMethod("fneg", "(F)F") //
        .with(e -> e.fload_0() //
            .fneg() //
            .freturn()) //
        .build();
  }

  // 0x77
  public static ClassFile dneg() {
    return ClassFileGenerator.newPublicClass("Dneg") //
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
  public static ClassFile ishl() {
    return ClassFileGenerator.newPublicClass("Ishl") //
        .publicStaticMethod("ishl", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .ishl() //
            .ireturn()) //
        .build();
  }

  // 0x79
  public static ClassFile lshl() {
    return ClassFileGenerator.newPublicClass("Lshl") //
        .publicStaticMethod("lshl", "(JI)J") //
        .with(e -> e.lload_0() //
            .iload_2() //
            .lshl() //
            .lreturn()) //
        .build();
  }

  // 0x7A
  public static ClassFile ishr() {
    return ClassFileGenerator.newPublicClass("Ishr") //
        .publicStaticMethod("ishr", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .ishr() //
            .ireturn()) //
        .build();
  }

  // 0x7B
  public static ClassFile lshr() {
    return ClassFileGenerator.newPublicClass("Lshr") //
        .publicStaticMethod("lshr", "(JI)J") //
        .with(e -> e.lload_0() //
            .iload_2() //
            .lshr() //
            .lreturn()) //
        .build();
  }

  // 0x7C
  public static ClassFile iushr() {
    return ClassFileGenerator.newPublicClass("Iushr") //
        .publicStaticMethod("iushr", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .iushr() //
            .ireturn()) //
        .build();
  }

  // 0x7D
  public static ClassFile lushr() {
    return ClassFileGenerator.newPublicClass("Lushr") //
        .publicStaticMethod("lushr", "(JI)J") //
        .with(e -> e.lload_0() //
            .iload_2() //
            .lushr() //
            .lreturn()) //
        .build();
  }

  // 0x7E
  public static ClassFile iand() {
    return ClassFileGenerator.newPublicClass("Iand") //
        .publicStaticMethod("iand", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .iand() //
            .ireturn()) //
        .build();
  }

  // 0x7F
  public static ClassFile land() {
    return ClassFileGenerator.newPublicClass("Land") //
        .publicStaticMethod("land", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .land() //
            .lreturn()) //
        .build();
  }

  // 0x80
  public static ClassFile ior() {
    return ClassFileGenerator.newPublicClass("Ior") //
        .publicStaticMethod("ior", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .ior() //
            .ireturn()) //
        .build();
  }

  // 0x81
  public static ClassFile lor() {
    return ClassFileGenerator.newPublicClass("Lor") //
        .publicStaticMethod("lor", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .lor() //
            .lreturn()) //
        .build();
  }

  // 0x82
  public static ClassFile ixor() {
    return ClassFileGenerator.newPublicClass("Ixor") //
        .publicStaticMethod("ixor", "(II)I") //
        .with(e -> e.iload_0() //
            .iload_1() //
            .ixor() //
            .ireturn()) //
        .build();
  }

  // 0x83
  public static ClassFile lxor() {
    return ClassFileGenerator.newPublicClass("Lxor") //
        .publicStaticMethod("lxor", "(JJ)J") //
        .with(e -> e.lload_0() //
            .lload_2() //
            .lxor() //
            .lreturn()) //
        .build();
  }

  // TODO: 0x84 (iinc)

  // 0x85
  public static ClassFile i2l() {
    return ClassFileGenerator.newPublicClass("I2L") //
        .publicStaticMethod("i2l", "(IJ)J") //
        .with(e -> e.iload_0() //
            .i2l() //
            .lload_1() //
            .ladd() //
            .lreturn()) //
        .build();
  }

  // 0x86
  public static ClassFile i2f() {
    return ClassFileGenerator.newPublicClass("I2F") //
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
  public static ClassFile i2d() {
    return ClassFileGenerator.newPublicClass("I2D") //
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
  public static ClassFile l2i() {
    return ClassFileGenerator.newPublicClass("L2I") //
        .publicStaticMethod("l2i", "(J)I") //
        .with(e -> e.lload_0() //
            .l2i() //
            .ireturn()) //
        .build();
  }

  // 0x89
  public static ClassFile l2f() {
    return ClassFileGenerator.newPublicClass("L2F") //
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
  public static ClassFile l2d() {
    return ClassFileGenerator.newPublicClass("L2D") //
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
  public static ClassFile f2i() {
    return ClassFileGenerator.newPublicClass("F2I") //
        .publicStaticMethod("f2i", "(F)I") //
        .with(e -> e.fload_0() //
            .f2i() //
            .ireturn()) //
        .build();
  }

  // 0x8C
  public static ClassFile f2l() {
    return ClassFileGenerator.newPublicClass("F2L") //
        .publicStaticMethod("f2l", "(F)J") //
        .with(e -> e.fload_0() //
            .f2l() //
            .lreturn()) //
        .build();
  }

  // 0x8D
  public static ClassFile f2d() {
    return ClassFileGenerator.newPublicClass("F2D") //
        .publicStaticMethod("f2d", "(FD)D") //
        .with(e -> e.fload_0() //
            .f2d() //
            .dload_1() //
            .dadd() //
            .dreturn()) //
        .build();
  }

  // 0x8E
  public static ClassFile d2i() {
    return ClassFileGenerator.newPublicClass("D2I") //
        .publicStaticMethod("d2i", "(D)I") //
        .with(e -> e.dload_0() //
            .d2i() //
            .ireturn()) //
        .build();
  }

  // 0x8F
  public static ClassFile d2l() {
    return ClassFileGenerator.newPublicClass("D2L") //
        .publicStaticMethod("d2l", "(D)J") //
        .with(e -> e.dload_0() //
            .d2l() //
            .lreturn()) //
        .build();
  }

  // 0x90
  public static ClassFile d2f() {
    return ClassFileGenerator.newPublicClass("D2F") //
        .publicStaticMethod("d2f", "(DF)F") //
        .with(e -> e.dload_0() //
            .d2f() //
            .fload_2() //
            .fadd() //
            .freturn()) //
        .build();
  }

  // 0x91
  public static ClassFile i2b() {
    return ClassFileGenerator.newPublicClass("I2B") //
        .publicStaticMethod("i2b", "(I)I") //
        .with(e -> e.iload_0() //
            .i2b() //
            .ireturn()) //
        .build();
  }

  // 0x92
  public static ClassFile i2c() {
    return ClassFileGenerator.newPublicClass("I2C") //
        .publicStaticMethod("i2c", "(I)I") //
        .with(e -> e.iload_0() //
            .i2c() //
            .ireturn()) //
        .build();
  }

  // 0x93
  public static ClassFile i2s() {
    return ClassFileGenerator.newPublicClass("I2S") //
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

  public static ClassFile istore_load_unsigned() {
    return ClassFileGenerator.newPublicClass("IstoreLoadUnsigned") //
        .publicStaticMethod("istore_load_unsigned", "()I") //
        .with(e -> e.sipush(7_687) //
            .istore(230) //
            .iload(0xe6) // 230
            .ireturn()) //
        .build();
  }

  public static ClassFile lstore_load_unsigned() {
    return ClassFileGenerator.newPublicClass("LstoreLoadUnsigned") //
        .publicStaticMethod("lstore_load_unsigned", "()J") //
        .with(e -> e.ldc(7_687_000l) //
            .lstore(199) //
            .lload(199) //
            .lreturn()) //
        .build();
  }

  public static ClassFile fstore_load_unsigned() {
    return ClassFileGenerator.newPublicClass("FstoreLoadUnsigned") //
        .publicStaticMethod("fstore_load_unsigned", "()F") //
        .with(e -> e.ldc(134.89f) //
            .fstore(142) //
            .fload(142) //
            .freturn()) //
        .build();
  }

  public static ClassFile dstore_load_unsigned() {
    return ClassFileGenerator.newPublicClass("DstoreLoadUnsigned") //
        .publicStaticMethod("dstore_load_unsigned", "()D") //
        .with(e -> e.ldc(33.33) //
            .dstore(210) //
            .dload(210) //
            .dreturn()) //
        .build();
  }

  public static ClassFile astore_load_unsigned() {
    return ClassFileGenerator.newPublicClass("AstoreLoadUnsigned") //
        .publicStaticMethod("astore_load_unsigned", "()Ljava/lang/Object;") //
        .with(e -> e.aconst_null() //
            .astore(175) //
            .aload(175) //
            .areturn()) //
        .build();
  }
}
