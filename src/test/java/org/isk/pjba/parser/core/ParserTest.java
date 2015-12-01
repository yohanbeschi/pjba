package org.isk.pjba.parser.core;

import org.isk.pjba.tokenizer.PjbaTokenType;
import org.isk.pjba.tokenizer.core.token.CoreTokenType;
import org.junit.Test;

public class ParserTest {
  @Test
  public void parse_simple() {
    ParserTester.fromString("'string' 1 5.3") //;
        .nextToken().assertTokenType(PjbaTokenType.STRING).assertNoErrors() //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        .nextToken().assertTokenType(PjbaTokenType.INTEGER).assertNoErrors() //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        .nextToken().assertTokenType(PjbaTokenType.DOUBLE).assertNoErrors() //
        .nextNoMoreToken() //
        .assertNextString("string") //
        .assertNextString("1") //
        .assertNextString("5.3") //
        .assertNoMoreString();
  }

  @Test
  public void parse_repeat() {
    ParserTester.fromString("'string_1' 1 5.3 'string_2' 2 6.4") //;
        .nextToken().assertTokenType(PjbaTokenType.STRING).assertNoErrors() //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        .nextToken().assertTokenType(PjbaTokenType.INTEGER).assertNoErrors() //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        .nextToken().assertTokenType(PjbaTokenType.DOUBLE).assertNoErrors() //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        // Repeat
        .nextToken().assertTokenType(PjbaTokenType.STRING).assertNoErrors() //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        .nextToken().assertTokenType(PjbaTokenType.INTEGER).assertNoErrors() //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        .nextToken().assertTokenType(PjbaTokenType.DOUBLE).assertNoErrors() //
        .nextNoMoreToken() //
        .assertNextString("string_1") //
        .assertNextString("1") //
        .assertNextString("5.3") //
        .assertNextString("string_2") //
        .assertNextString("2") //
        .assertNextString("6.4") //
        .assertNoMoreString();
  }

  @Test
  public void parse_skipToBefore_wrongFirstToken() {
    ParserTester.fromString("1 5.3 'string_2' 2 6.4") //;
        .nextToken().assertTokenType(PjbaTokenType.INTEGER).assertErrors(1) //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        .nextToken().assertTokenType(PjbaTokenType.DOUBLE).assertNoErrors() //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        // Repeat
        .nextToken().assertTokenType(PjbaTokenType.STRING).assertNoErrors() //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        .nextToken().assertTokenType(PjbaTokenType.INTEGER).assertNoErrors() //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        .nextToken().assertTokenType(PjbaTokenType.DOUBLE).assertNoErrors() //
        .nextNoMoreToken() //
        .assertNextString("string_2") //
        .assertNextString("2") //
        .assertNextString("6.4") //
        .assertNoMoreString();
  }

  @Test
  public void parse_skipToBefore_wrongFirstTokenRepeat() {
    ParserTester.fromString("'string_1' 1 5.3 2 6.4") //;
        .nextToken().assertTokenType(PjbaTokenType.STRING).assertNoErrors() //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        .nextToken().assertTokenType(PjbaTokenType.INTEGER).assertNoErrors() //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        .nextToken().assertTokenType(PjbaTokenType.DOUBLE).assertNoErrors() //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        // Repeat
        .nextToken().assertTokenType(PjbaTokenType.INTEGER).assertErrors(1) //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        .nextToken().assertTokenType(PjbaTokenType.DOUBLE).assertNoErrors() //
        .nextNoMoreToken() //
        .assertNextString("string_1") //
        .assertNextString("1") //
        .assertNextString("5.3") //
        .assertNoMoreString();
  }

  @Test
  public void parse_skipTo_invalidInteger() {
    ParserTester.fromString("'string_1' 5.3 'string_2' 2 6.4") //;
        .nextToken().assertTokenType(PjbaTokenType.STRING).assertNoErrors() //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        .nextToken().assertTokenType(PjbaTokenType.DOUBLE).assertErrors(1) //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        // Repeat
        .nextToken().assertTokenType(PjbaTokenType.STRING).assertNoErrors() //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        .nextToken().assertTokenType(PjbaTokenType.INTEGER).assertNoErrors() //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        .nextToken().assertTokenType(PjbaTokenType.DOUBLE).assertNoErrors() //
        .nextNoMoreToken() //
        .assertNextString("string_1") //
        .assertNoMoreString();
  }

  @Test
  public void parse_skipTo_invalidDouble() {
    ParserTester.fromString("'string_1' 1 'string_2' 2 6.4") //;
        .nextToken().assertTokenType(PjbaTokenType.STRING).assertNoErrors() //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        .nextToken().assertTokenType(PjbaTokenType.INTEGER).assertNoErrors() //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        // Repeat
        .nextToken().assertTokenType(PjbaTokenType.STRING).assertErrors(1) //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        .nextToken().assertTokenType(PjbaTokenType.INTEGER).assertNoErrors() //
        .nextToken().assertTokenType(CoreTokenType.WHITESPACE).assertNoErrors() //
        .nextToken().assertTokenType(PjbaTokenType.DOUBLE).assertNoErrors() //
        .nextNoMoreToken() //
        .assertNextString("string_1") //
        .assertNextString("1") //
        .assertNextString("6.4") //
        .assertNoMoreString();
  }
}
