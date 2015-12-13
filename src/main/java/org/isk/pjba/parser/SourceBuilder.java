package org.isk.pjba.parser;

import java.util.ArrayList;
import java.util.List;

import org.isk.pjba.builder.ClassFileBuilder;
import org.isk.pjba.builder.MethodBuilder;
import org.isk.pjba.instruction.meta.ByteArgMetaInstruction;
import org.isk.pjba.instruction.meta.MetaInstruction;
import org.isk.pjba.instruction.meta.NoArgMetaInstruction;
import org.isk.pjba.instruction.meta.ShortArgMetaInstruction;
import org.isk.pjba.parser.core.AstBuilder;
import org.isk.pjba.structure.ClassFile;
import org.isk.pjba.structure.Instruction;
import org.isk.pjba.structure.Method;
import org.isk.pjba.tokenizer.PjbaTokenType;
import org.isk.pjba.tokenizer.core.token.Token;
import org.isk.pjba.tokenizer.core.token.TokenType;
import org.isk.pjba.unicode.CodePoints;

// TODO: Check if class exist
// TODO: Check if field exist
// TODO: Check if method exist
public class SourceBuilder implements AstBuilder {

  private final List<ClassFile> classFiles = new ArrayList<>();

  private int classModifiers = 1;
  private int methodModifiers = 1;
  private ClassFileBuilder currentClassFileBuilder = null;
  private MethodBuilder currentMethodBuilder = null;
  private String currentMethodName;
  private String currentParametersDescriptor;
  private String currentReturnDescriptor;
  private MetaInstruction currentMetaInstruction;

  @SuppressWarnings("unchecked")
  @Override
  public List<ClassFile> getBuiltObject() {
    return this.classFiles;
  }

  @Override
  public void endStream() {
    // Do nothing
  }

  public void endClazz() {
    this.classFiles.add(this.currentClassFileBuilder.build());
    this.currentClassFileBuilder = null;
  }

  public void addClazzModifier(final Token token) {
    final TokenType tokenType = token.type();
    if (tokenType == PjbaTokenType.PUBLIC) {
      this.classModifiers |= ClassFile.MODIFIER_PUBLIC;
    } else if (tokenType == PjbaTokenType.FINAL) {
      this.classModifiers |= ClassFile.MODIFIER_FINAL;
    } else if (tokenType == PjbaTokenType.ABSTRACT) {
      this.classModifiers |= ClassFile.MODIFIER_ABSTRACT;
    } else if (tokenType == PjbaTokenType.INTERFACE) {
      this.classModifiers |= ClassFile.MODIFIER_INTERFACE;
    } else if (tokenType == PjbaTokenType.SUPER) {
      this.classModifiers |= ClassFile.MODIFIER_SUPER;
    }
  }

  public void addClassName(final Token token) {
    this.currentClassFileBuilder = new ClassFileBuilder(this.classModifiers, CodePoints.toString(token.value()));
  }

  public void classContentStart() {
    // TODO: Remove ??
  }

  public void parameterStart(final Token token) {
    // Remove ??
  }

  public void parameterEnd(final Token token) {
    // Remove ??
  }

  public void addMethodModifier(final Token token) {
    final TokenType tokenType = token.type();
    if (tokenType == PjbaTokenType.PUBLIC) {
      this.methodModifiers |= Method.MODIFIER_PUBLIC;
    } else if (tokenType == PjbaTokenType.PROTECTED) {
      this.methodModifiers |= Method.MODIFIER_PROTECTED;
    } else if (tokenType == PjbaTokenType.PRIVATE) {
      this.methodModifiers |= Method.MODIFIER_PRIVATE;
    } else if (tokenType == PjbaTokenType.STATIC) {
      this.methodModifiers |= Method.MODIFIER_STATIC;
    } else if (tokenType == PjbaTokenType.FINAL) {
      this.methodModifiers |= Method.MODIFIER_FINAL;
    } else if (tokenType == PjbaTokenType.STRICTFP) {
      this.methodModifiers |= Method.MODIFIER_STATIC;
    } else if (tokenType == PjbaTokenType.SYNCHRONIZED) {
      this.methodModifiers |= Method.MODIFIER_SYNCHRONIZED;
    } else if (tokenType == PjbaTokenType.ABSTRACT) {
      this.methodModifiers |= Method.MODIFIER_ABSTRACT;
    } else if (tokenType == PjbaTokenType.NATIVE) {
      this.methodModifiers |= Method.MODIFIER_NATIVE;
    }
  }

  public void addMethodName(final Token token) {
    this.currentMethodName = CodePoints.toString(token.value());
  }

  public void addParametersDescriptor(final Token token) {
    this.currentParametersDescriptor = CodePoints.toString(token.value());
  }

  public void addReturnDescriptor(final Token token) {
    this.currentReturnDescriptor = CodePoints.toString(token.value());
  }

  public void methodContentStart() {
    final String parametersDescriptor = this.currentParametersDescriptor != null ? this.currentParametersDescriptor
        : "";
    final String methodDescriptor = "(" + parametersDescriptor + ")" + this.currentReturnDescriptor;
    this.currentMethodBuilder = this.currentClassFileBuilder.newMethod(this.methodModifiers, this.currentMethodName,
        methodDescriptor);

    this.methodModifiers = 0;
    this.currentMethodName = null;
    this.currentParametersDescriptor = null;
    this.currentReturnDescriptor = null;
  }

  public void endMethod() {
    this.currentMethodBuilder = null;
  }

  public boolean isClazzModifier(final Token token) {
    final TokenType tokenType = token.type();
    return tokenType == PjbaTokenType.PUBLIC //
        || tokenType == PjbaTokenType.FINAL //
        || tokenType == PjbaTokenType.ABSTRACT //
        || tokenType == PjbaTokenType.INTERFACE //
        || tokenType == PjbaTokenType.SUPER;
  }

  public boolean isMethodModifier(final Token token) {
    final TokenType tokenType = token.type();
    return tokenType == PjbaTokenType.PUBLIC //
        || tokenType == PjbaTokenType.PROTECTED //
        || tokenType == PjbaTokenType.PRIVATE //
        || tokenType == PjbaTokenType.STATIC //
        || tokenType == PjbaTokenType.FINAL //
        || tokenType == PjbaTokenType.STRICTFP //
        || tokenType == PjbaTokenType.SYNCHRONIZED //
        || tokenType == PjbaTokenType.ABSTRACT //
        || tokenType == PjbaTokenType.NATIVE;
  }

  public void prepareInstruction(final MetaInstruction metaInstruction) {
    this.currentMetaInstruction = metaInstruction;
  }

  public void addNoArgInstruction() {
    final Instruction instruction = ((NoArgMetaInstruction) this.currentMetaInstruction).buildInstruction();
    this.currentMethodBuilder.instruction(instruction);
  }

  public void addByteArgInstruction(final Token token) {
    final int value = Integer.parseInt(CodePoints.toString(token.formattedValue()));
    final Instruction instruction = ((ByteArgMetaInstruction) this.currentMetaInstruction).buildInstruction(value);
    this.currentMethodBuilder.instruction(instruction);
  }

  public void addShortArgInstruction(final Token token) {
    final int value = Integer.parseInt(CodePoints.toString(token.formattedValue()));
    final Instruction instruction = ((ShortArgMetaInstruction) this.currentMetaInstruction).buildInstruction(value);
    this.currentMethodBuilder.instruction(instruction);
  }

  public void addLdcIntInstruction(final Token token) {
    final int value = Integer.parseInt(CodePoints.toString(token.formattedValue()));
    this.currentMethodBuilder.ldc(value);
  }

  public void addLdcFloatInstruction(final Token token) {
    final float value = Float.parseFloat(CodePoints.toString(token.formattedValue()));
    this.currentMethodBuilder.ldc(value);
  }

  public void addLdcStringInstruction(final Token token) {
    final String value = CodePoints.toString(token.formattedValue());
    this.currentMethodBuilder.ldc(value);
  }

  public void addLdcLongInstruction(final Token token) {
    final long value = Long.parseLong(CodePoints.toString(token.formattedValue()));
    this.currentMethodBuilder.ldc(value);
  }

  public void addLdcDoubleInstruction(final Token token) {
    final double value = Double.parseDouble(CodePoints.toString(token.formattedValue()));
    this.currentMethodBuilder.ldc(value);
  }
}
