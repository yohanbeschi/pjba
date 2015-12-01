package org.isk.pjba.parser.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.isk.pjba.tokenizer.PjbaTokenType;
import org.isk.pjba.tokenizer.PjbaTokenizer;
import org.isk.pjba.tokenizer.core.error.CoreErrorType;
import org.isk.pjba.tokenizer.core.reader.CleanTokenReader;
import org.isk.pjba.tokenizer.core.token.GenericTokenTester;
import org.isk.pjba.tokenizer.core.token.Token;
import org.isk.pjba.unicode.CodePoints;
import org.isk.pjba.unicode.CodePoints.Charset;
import org.isk.pjba.unicode.CodePoints.Reader;
import org.isk.pjba.unicode.UnicodeInputStream;

public class ParserTester extends GenericTokenTester<ParserTester> {

  private final List<Token> tokens;
  private final List<String> ast;

  private int tokensIndex;
  private int astIndex;

  public ParserTester(final List<Token> tokens, final List<String> ast) {
    this.tokens = tokens;
    this.ast = ast;
  }

  public static ParserTester fromString(final String source) {
    final CleanTokenReader tokenReader = getReader(source);
    final FakeProductionStack productionStack = new FakeProductionStack(FakeSymbols.Stream.INSTANCE);
    final FakeAstBuilder builder = new FakeAstBuilder();
    final List<String> ast = Parser.parse(tokenReader, productionStack, builder);

    return new ParserTester(tokenReader.tokens(), ast);
  }

  private static CleanTokenReader getReader(final String string) {
    final Reader reader = new Reader(Charset.UTF8, new UnicodeInputStream(string.getBytes(StandardCharsets.UTF_8)));
    return new CleanTokenReader(new PjbaTokenizer(reader));
  }

  @Override
  public ParserTester getThis() {
    return this;
  }

  public ParserTester nextToken() {
    this.actualToken = this.tokens.get(this.tokensIndex++);
    return this;
  }

  public ParserTester assertNextString(final String expectedValue) {
    assertThat(this.ast.get(this.astIndex++)).isEqualTo(expectedValue);
    return this;
  }

  public ParserTester nextNoMoreToken() {
    assertThat(this.tokens).hasSize(this.tokensIndex);
    return this;
  }

  public ParserTester assertNoMoreString() {
    assertThat(this.ast).hasSize(this.astIndex);
    return this;
  }

  // -------------------------------------------------------------------------------------------------------------------
  // ProductionStack
  // -------------------------------------------------------------------------------------------------------------------

  private static class FakeProductionStack
      extends ProductionStack<CleanTokenReader, FakeProductionStack, FakeAstBuilder> {

    public FakeProductionStack(final Symbol<CleanTokenReader, FakeProductionStack, FakeAstBuilder> startSymbol) {
      super(startSymbol);
    }
  }

  // -------------------------------------------------------------------------------------------------------------------
  // AstBuilder
  // -------------------------------------------------------------------------------------------------------------------

  private static class FakeAstBuilder implements AstBuilder {

    private final List<String> ast = new ArrayList<>();

    @Override
    public void endStream() {
      // Do nothing
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> R getBuiltObject() {
      return (R) this.ast;
    }

    public void addString(final Token token) {
      this.ast.add(CodePoints.toString(token.formattedValue()));
    }

    public void addInteger(final Token token) {
      this.ast.add(CodePoints.toString(token.formattedValue()));
    }

    public void addDouble(final Token token) {
      this.ast.add(CodePoints.toString(token.formattedValue()));
    }
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Symbols
  // -------------------------------------------------------------------------------------------------------------------

  public static class FakeSymbols {

    // Stream = Sample EndOfStream
    public static class Stream extends SkipWSSymbol<CleanTokenReader, FakeProductionStack, FakeAstBuilder> {
      final public static Stream INSTANCE = new Stream();

      private Stream() {
        super((reader, productionStack, builder) -> {
          productionStack.push(Sample.INSTANCE);
        });
      }
    }

    // Sample = {string integer double}
    public static class Sample extends SkipWSSymbol<CleanTokenReader, FakeProductionStack, FakeAstBuilder> {
      final public static Sample INSTANCE = new Sample();

      private Sample() {
        super((reader, productionStack, builder) -> {
          final Token token = reader.next();

          productionStack.push(Sample.INSTANCE);

          if (token.type() == PjbaTokenType.STRING) {
            builder.addString(token);
            productionStack.push(DoubleValue.INSTANCE);
            productionStack.push(IntegerValue.INSTANCE);
          } else {
            token.addError(CoreErrorType.INVALID_TOKEN);
            reader.skipToBefore(PjbaTokenType.STRING);
            productionStack.skipToBefore(Sample.INSTANCE);
          }
        });
      }
    }

    public static class IntegerValue extends SkipWSSymbol<CleanTokenReader, FakeProductionStack, FakeAstBuilder> {
      final public static IntegerValue INSTANCE = new IntegerValue();

      private IntegerValue() {
        super((reader, productionStack, builder) -> {
          final Token token = reader.next();

          if (token.type() == PjbaTokenType.INTEGER) {
            builder.addInteger(token);
          } else {
            token.addError(CoreErrorType.INVALID_TOKEN);
            reader.skipTo(PjbaTokenType.STRING);
            productionStack.skipTo(Sample.INSTANCE); // Here the production stack becomes empty
          }
        });
      }
    }

    public static class DoubleValue extends SkipWSSymbol<CleanTokenReader, FakeProductionStack, FakeAstBuilder> {
      final public static DoubleValue INSTANCE = new DoubleValue();

      private DoubleValue() {
        super((reader, productionStack, builder) -> {
          final Token token = reader.next();

          if (token.type() == PjbaTokenType.DOUBLE) {
            builder.addDouble(token);
          } else {
            // For testing purpose
            productionStack.pop(); // Pop Sample.INSTANCE
            productionStack.push(DoubleValue.INSTANCE);
            productionStack.push(IntegerValue.INSTANCE);
            // end
            token.addError(CoreErrorType.INVALID_TOKEN);
            reader.skipTo(PjbaTokenType.INTEGER);
            productionStack.skipTo(IntegerValue.INSTANCE);
          }
        });
      }
    }
  }
}
