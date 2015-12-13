package org.isk.pjba.assembler;

import static org.isk.pjba.assembler.ClassFileTestData.*;
import static org.isk.pjba.assembler.MethodTester.$;
// import static org.isk.pjba.assembler.ClassFileTestData.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.function.Function;

import org.isk.pjba.Assembler;
import org.isk.pjba.PjbaClassLoader;
import org.isk.pjba.structure.ClassFile;
import org.junit.runners.Parameterized.Parameters;

public abstract class ParameterizedClassFile {
  @Parameters(name = "{index}: {0}")
  public static Iterable<Object[]> data1() {
    return Arrays
        .asList(new Object[][] { //

        { "nop", nop(), $("nop").assertNull() }, // 0x00
        { "aconst_null", aconst_null(), $("aconst_null").assertNull() }, // 0x01
        { "iconst_m1", iconst_m1(), $("iconst_m1").assertInteger(-1) }, // 0x02
        { "iconst_0", iconst_0(), $("iconst_0").assertInteger(0) }, // 0x03
        { "iconst_1", iconst_1(), $("iconst_1").assertInteger(1) }, // 0x04
        { "iconst_2", iconst_2(), $("iconst_2").assertInteger(2) }, // 0x05
        { "iconst_3", iconst_3(), $("iconst_3").assertInteger(3) }, // 0x06
        { "iconst_4", iconst_4(), $("iconst_4").assertInteger(4) }, // 0x07
        { "iconst_5", iconst_5(), $("iconst_5").assertInteger(5) }, // 0x08
        { "lconst_0", lconst_0(), $("lconst_0").assertLong(0) }, // 0x09
        { "lconst_1", lconst_1(), $("lconst_1").assertLong(1) }, // 0x0A
        { "fconst_0", fconst_0(), $("fconst_0").assertFloat(0) }, // 0x0B
        { "fconst_1", fconst_1(), $("fconst_1").assertFloat(1) }, // 0x0C
        { "dconst_0", dconst_0(), $("dconst_0").assertDouble(0) }, // 0xD
        { "dconst_1", dconst_1(), $("dconst_1").assertDouble(1) }, // 0xE
        { "bipush", bipush(), $("bipush").assertInteger(125) }, // 0x0F
        { "sipush", sipush(), $("sipush").assertInteger(5_396) }, // 0x11
        { "ldc_string", ldc_string(), $("ldc_string").assertString("Hello World") }, // 0x12
        { "ldc_int", ldc_int(), $("ldc_int") }, // 0x12
        { "ldc_float", ldc_float(), $("ldc_float").assertFloat(3.5f) }, // 0x12
        // TODO: 0x13 (ldc_w)
        { "ldc2_w_long", ldc2_w_long(), $("ldc2_w_long").assertLong(167_980_564_900l) }, // 0x14
        { "ldc2_w_double", ldc2_w_double(), $("ldc2_w_double").assertDouble(3.578_978_979) }, // 0x14
        { "iload", iload(), // 0x15
            $("iload").call(true, true, true, true, true, true, true, true, true, true, 5).assertInteger(5) }, //
        { "lload", lload(), // 0x16
            $("lload").call(true, true, true, true, true, true, true, true, true, true, 15l).assertLong(15) }, //
        { "fload", fload(), // 0x17
            $("fload").call(true, true, true, true, true, true, true, true, true, true, 5.5f).assertFloat(5.5f) }, //
        { "dload", dload(), // 0x18
            $("dload").call(true, true, true, true, true, true, true, true, true, true, 5.9).assertDouble(5.9) }, //
        { "aload", aload(), // 0x19
            $("aload").call(true, true, true, true, true, true, true, true, true, true, 89).assertInteger(89) }, //
        { "iload_0", iload_0(), $("iload_0").call(5).assertInteger(5) }, // 0x1A
        { "iload_1", iload_1(), $("iload_1").call(true, 5).assertInteger(5) }, // 0x1B
        { "iload_2", iload_2(), $("iload_2").call(true, true, 5).assertInteger(5) }, // 0x1C
        { "iload_3", iload_3(), $("iload_3").call(true, true, true, 5).assertInteger(5) }, // 0x1D
        { "lload_0", lload_0(), $("lload_0").call(15).assertLong(15) }, // 0x1E
        { "lload_1", lload_1(), $("lload_1").call(true, 15).assertLong(15) }, // 0x1F
        { "lload_2", lload_2(), $("lload_2").call(true, true, 15).assertLong(15) }, // 0x20
        { "lload_3", lload_3(), $("lload_3").call(true, true, true, 15).assertLong(15) }, // 0x21
        { "fload_0", fload_0(), $("fload_0").call(5.5f).assertFloat(5.5f) }, // 0x22
        { "fload_1", fload_1(), $("fload_1").call(true, 5.5f).assertFloat(5.5f) }, // 0x23
        { "fload_2", fload_2(), $("fload_2").call(true, true, 5.5f).assertFloat(5.5f) }, // 0x24
        { "fload_3", fload_3(), $("fload_3").call(true, true, true, 5.5f).assertFloat(5.5f) }, // 0x25
        { "dload_0", dload_0(), $("dload_0").call(5.5f).assertDouble(5.5f) }, // 0x26
        { "dload_1", dload_1(), $("dload_1").call(true, 5.5f).assertDouble(5.5f) }, // 0x27
        { "dload_2", dload_2(), $("dload_2").call(true, true, 5.5f).assertDouble(5.5f) }, // 0x28
        { "dload_3", dload_3(), $("dload_3").call(true, true, true, 5.5f).assertDouble(5.5f) }, // 0x29
        { "aload_0", aload_0(), $("aload_0").call(5000).assertInteger(5000) }, // 0x2A
        { "aload_1", aload_1(), $("aload_1").call(true, 5000).assertInteger(5000) }, // 0x2B
        { "aload_2", aload_2(), $("aload_2").call(true, true, 5000).assertInteger(5000) }, // 0x2C
        { "aload_3", aload_3(), $("aload_3").call(true, true, true, 5000).assertInteger(5000) }, // 0x2D
        // TODO: 0x2E to 0x35 (Load arrays)
        { "istore", istore(), // 0x36
            $("istore").call(2, true, true, true, true, true, true, true, true, true, 4).assertInteger(2) }, //
        { "lstore", lstore(), // 0x37
            $("lstore").call(2l, true, true, true, true, true, true, true, true, true, 4l).assertLong(2l) }, //
        { "fstore", fstore(), // 0x38
            $("fstore").call(2.5f, true, true, true, true, true, true, true, true, true, 4.5f).assertFloat(2.5f) }, //
        { "dstore", dstore(), //  0x39
            $("dstore").call(2.98, true, true, true, true, true, true, true, true, true, 40.56).assertDouble(2.98) }, //
        { "astore", astore(), //  0x3A
            $("astore").call(2, true, true, true, true, true, true, true, true, true, 4).assertInteger(2) }, //
        { "istore_0", istore_0(), $("istore_0").call(2, 4).assertInteger(11) }, // 0x3B
        { "istore_1", istore_1(), $("istore_1").call(2, 4).assertInteger(11) }, // 0x3C
        { "istore_2", istore_2(), $("istore_2").call(2, 5, 6).assertInteger(7) }, // 0x3D
        { "istore_3", istore_3(), $("istore_3").call(2, 5, 6, 7).assertInteger(8) }, // 0x3E
        { "lstore_0", lstore_0(), $("lstore_0").call(2, 4l).assertLong(11) }, // 0x3F
        { "lstore_1", lstore_1(), $("lstore_1").call(2, 4l).assertLong(11) }, // 0x40
        { "lstore_2", lstore_2(), $("lstore_2").call(2l, 5l, 6l).assertLong(8) }, // 0x41
        { "lstore_3", lstore_3(), $("lstore_3").call(2l, 5).assertLong(7) }, // 0x42
        { "fstore_0", fstore_0(), $("fstore_0").call(2f, 4f).assertFloat(11) }, // 0x43
        { "fstore_1", fstore_1(), $("fstore_1").call(2f, 4f).assertFloat(11) }, // 0x44
        { "fstore_2", fstore_2(), $("fstore_2").call(2f, 5f, 6f).assertFloat(7) }, // 0x45
        { "fstore_3", fstore_3(), $("fstore_3").call(2f, 5f, 6f, 7f).assertFloat(8) }, // 0x46
        { "dstore_0", dstore_0(), $("dstore_0").call(2., 4.).assertDouble(11) }, // 0x47
        { "dstore_1", dstore_1(), $("dstore_1").call(2f, 4.).assertDouble(11) }, // 0x48
        { "dstore_2", dstore_2(), $("dstore_2").call(2., 5., 6.).assertDouble(8) }, // 0x49
        { "dstore_3", dstore_3(), $("dstore_3").call(2., 5f).assertDouble(7) }, // 0x4A
        { "astore_0", astore_0(), $("astore_0").call(2).assertInteger(2) }, // 0x4B
        { "astore_1", astore_1(), $("astore_1").call(2, 5).assertInteger(2) }, // 0x4C
        { "astore_2", astore_2(), $("astore_2").call(2, 5, 4).assertInteger(2) }, // 0x4D
        { "astore_3", astore_3(), $("astore_3").call(2, 5, 4, 7).assertInteger(2) }, // 0x4E
        // TODO: 0x4F to 0x56 (Store arrays)
        { "pop", pop(), $("pop").assertDouble(0) }, // 0x57
        { "pop2", pop2(), $("pop2").assertInteger(3) }, // 0x58
        { "dup", dup(), $("dup").assertInteger(2) }, // 0x59
        { "dup_x1", dup_x1(), $("dup_x1").assertInteger(1) }, // 0x5A
        { "dup_x2", dup_x2(), $("dup_x2").assertInteger(1) }, // 0x5B
        { "dup2", dup2(), $("dup2").assertDouble(2) }, // 0x5C
        { "dup2_x1", dup2_x1(), $("dup2_x1").assertDouble(1) }, // 0x5D
        { "dup2_x2", dup2_x2(), $("dup2_x2").assertDouble(1) }, // 0x5E
        { "swap", swap(), $("swap").assertInteger(1) }, // 0x5F
        { "iadd", iadd(), $("iadd").call(5, 10).assertInteger(15) }, // 0x60
        { "ladd", ladd(), $("ladd").call(15l, 100l).assertLong(115) }, // 0x61
        { "fadd", fadd(), $("fadd").call(5.5f, 10.5f).assertFloat(16) }, // 0x62
        { "dadd", dadd(), $("dadd").call(5.9, 10.9).assertDouble(16.8) }, // 0x63
        { "isub", isub(), $("isub").call(5, 10).assertInteger(-5) }, // 0x64
        { "lsub", lsub(), $("lsub").call(15, 100).assertLong(-85) }, // 0x65
        { "fsub", fsub(), $("fsub").call(5.5f, 10.5f).assertFloat(-5) }, // 0x66
        { "dsub", dsub(), $("dsub").call(5.9, 10.9).assertDouble(-5) }, // 0x67
        { "imul", imul(), $("imul").call(5, 10).assertInteger(50) }, // 0x68
        { "lmul", lmul(), $("lmul").call(15l, 100l).assertLong(1500) }, // 0x69
        { "fmul", fmul(), $("fmul").call(5.2f, 10.5f).assertFloat(54.6f) }, // 0x6A
        { "dmul", dmul(), $("dmul").call(5.9, 10.9).assertDouble(64.31) }, // 0x6B
        { "idiv", idiv(), $("idiv").call(10, 5).assertInteger(2) }, // 0x6C
        { "ldiv", ldiv(), $("ldiv").call(150l, 10l).assertLong(15) }, // 0x6D
        { "fdiv", fdiv(), $("fdiv").call(3.8f, 2.f).assertFloat(1.9f) }, // 0x6E
        { "ddiv", ddiv(), $("ddiv").call(10.0, 2.0).assertDouble(5) }, // 0x6F
        { "irem", irem(), $("irem").call(7, 2).assertInteger(1) }, // 0x70
        { "lrem", lrem(), $("lrem").call(155l, 10l).assertLong(5) }, // 0x71
        { "frem", frem(), $("frem").call(0.5f, 0.3f).assertFloat(0.2f) }, // 0x72
        { "drem", drem(), $("drem").call(17.5, 15.3).assertDouble(2.2) }, // 0x73
        { "ineg", ineg(), $("ineg").call(7).assertInteger(-7) }, // 0x74
        { "lneg", lneg(), $("lneg").call(-155l).assertLong(155) }, // 0x75
        { "fneg", fneg(), $("fneg").call(0.5f).assertFloat(-0.5f) }, // 0x76
        { "dneg", dneg(), $("dneg").call(-17.5).assertDouble(17.5) }, // 0x77
        { "ishlA", ishl(), $("ishl").call(10, 32).assertInteger(10) }, // 0x78
        { "ishlB", ishl(), $("ishl").call(1, 31).assertInteger(Integer.MIN_VALUE) }, // 0x78
        { "lshlA", lshl(), $("lshl").call(10, 64).assertLong(10) }, // 0x79
        { "lshlB", lshl(), $("lshl").call(1, 63).assertLong(Long.MIN_VALUE) }, // 0x79
        { "ishr", ishr(), $("ishr").call(10, 32).assertInteger(10) }, // 0x7A
        { "ishr", ishr(), $("ishr").call(Integer.MIN_VALUE, 31).assertInteger(-1) }, // 0x7A
        { "lshr", lshr(), $("lshr").call(10, 64).assertLong(10) }, // 0x7B
        { "lshr", lshr(), $("lshr").call(Long.MIN_VALUE, 63).assertLong(-1) }, // 0x7B
        { "iushr", iushr(), $("iushr").call(Integer.MIN_VALUE, 31).assertInteger(1) }, // 0x7C
        { "lushr", lushr(), $("lushr").call(Long.MIN_VALUE, 63).assertLong(1) }, // 0x7D
        { "iand", iand(), $("iand").call(5, 4).assertInteger(4) }, // 0x7E
        { "land", land(), $("land").call(Long.MAX_VALUE, Long.MIN_VALUE).assertLong(0) }, // 0x7F
        { "ior", ior(), $("ior").call(5, 4).assertInteger(5) }, // 0x80
        { "lor", lor(), $("lor").call(Long.MAX_VALUE, Long.MIN_VALUE).assertLong(-1) }, // 0x81
        { "ixor", ixor(), $("ixor").call(5, 4).assertInteger(1) }, // 0x82
        { "lxor", lxor(), $("lxor").call(Long.MAX_VALUE, Long.MIN_VALUE).assertLong(-1) }, // 0x83
        // TODO: 0x84 (iinc)
        { "i2l", i2l(), $("i2l").call(5, 4l).assertLong(9) }, // 0x85
        { "i2f", i2f(), $("i2f").call(5, 2).assertFloat(2.5f) }, // 0x86
        { "i2d", i2d(), $("i2d").call(10, 3).assertDouble(3.3333) }, // 0x87
        { "l2i", l2i(), $("l2i").call(Long.MAX_VALUE).assertInteger(-1) }, // 0x88
        { "l2f", l2f(), $("l2f").call(5l, 2l).assertFloat(2.5f) }, // 0x89
        { "l2d", l2d(), $("l2d").call(10l, 3l).assertDouble(3.3333) }, // 0x8A
        { "f2i", f2i(), $("f2i").call(4.56f).assertInteger(4) }, // 0x8B
        { "f2l", f2l(), $("f2l").call(45.908f).assertLong(45) }, // 0x8C
        { "f2d", f2d(), $("f2d").call(10.5f, 5.6f).assertDouble(16.1) }, // 0x8D
        { "d2i", d2i(), $("d2i").call(4.56f).assertInteger(4) }, // 0x8E
        { "d2l", d2l(), $("d2l").call(45.908f).assertLong(45) }, // 0x8F
        { "d2f", d2f(), $("d2f").call(10.5f, 5.6f).assertFloat(16.1f) }, // 0x90
        { "i2b", i2b(), $("i2b").call(Integer.MAX_VALUE).assertInteger(-1) }, // 0x91
        { "i2c", i2c(), $("i2c").call(Integer.MAX_VALUE).assertInteger(65_535) }, // 0x92
        { "i2s", i2s(), $("i2s").call(Integer.MAX_VALUE).assertInteger(-1) }, // 0x93
        // TODO: 0x94 to 0xAB
        // Already tested throughout this file
        // 0xAC: ireturn
        // 0xAD: lreturn
        // 0xAE: freturn
        // 0xAF: dreturn
        // 0xB0: areturn
        // 0xB1: return
        // TODO: 0xB2 to CA
        { "istore_load_unsigned", istore_load_unsigned(), //
            $("istore_load_unsigned").call().assertInteger(7_687) }, //
        { "lstore_load_unsigned", lstore_load_unsigned(), $("lstore_load_unsigned").call().assertLong(7_687_000) }, //
        { "fstore_load_unsigned", fstore_load_unsigned(), $("fstore_load_unsigned").call().assertFloat(134.89f) }, //
        { "dstore_load_unsigned", dstore_load_unsigned(), $("dstore_load_unsigned").call().assertDouble(33.33) }, //
        { "astore_load_unsigned", astore_load_unsigned(), $("astore_load_unsigned").call().assertNull() }, //
        // TODO: Limits bipush/sipush/ldc
    });
  }

  protected final Function<String, ClassFile> classFileGenerator;
  protected final MethodTester methodTester;

  public ParameterizedClassFile(final String testName, final Function<String, ClassFile> classFileGenerator,
      final MethodTester methodTester) {
    super();
    this.classFileGenerator = classFileGenerator;
    this.methodTester = methodTester;
    PjbaClassLoader.reset();
  }

  protected ByteArrayOutputStream assemble(final ClassFile classFile) {
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    final DataOutput bytecode = new DataOutputStream(baos);
    Assembler.assemble(classFile, bytecode);
    return baos;
  }

  protected InputStream getReferenceClass(final String javaClass) {
    return Thread.currentThread().getContextClassLoader()
        .getResourceAsStream("reference/class/" + javaClass + ".class");
  }

  protected byte[] toBytes(final InputStream inputStream) {
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    final byte[] buffer = new byte[1024];
    int read = 0;
    try {
      while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
        baos.write(buffer, 0, read);
      }
      baos.flush();
    } catch (final Exception e) {
      throw new RuntimeException("Something went wrong!", e);
    }
    return baos.toByteArray();
  }
}
