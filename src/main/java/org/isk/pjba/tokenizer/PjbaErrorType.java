package org.isk.pjba.tokenizer;

import org.isk.pjba.tokenizer.core.error.ErrorType;

public enum PjbaErrorType implements ErrorType {

  // -------------------------------------------------------------------------------------------------------------------
  // Tokenizer
  // -------------------------------------------------------------------------------------------------------------------

  // uXXXX (XXXX not in the range)
  INVALID_CODEPOINT,

  // \\, \b, \f, \n, \r, \s, \t, \\uXXXX
  INVALID_ESCAPE_SEQUENCE,

  // Missing ending single quote
  NOT_ENDING_STRING,

  // Integer, Long, Float or Double
  INVALID_NUMBER,

  // brxxxx (eg. 2r20fe)
  INVALID_INTEGER_WITH_RADIX,

  //
  INVALID_LONG_WITH_RADIX,

  // Radix must be between 2 and 16
  INVALID_RADIX_VALUE,

  // 32-bit integer
  INVALID_INTEGER_VALUE,

  // 64-bit integer
  INVALID_LONG_VALUE,

  // 32-bit float: 10.0, +1., .3, -5.0e-10, +6.9e+5 (we can use 'E' instead of 'e' too)
  INVALID_FLOAT_VALUE,

  // Same as float but 64-bit and ending with the character 'd'
  INVALID_DOUBLE_VALUE,

  //-------------------------------------------------------------------------------------------------------------------
  // Parser
  // -------------------------------------------------------------------------------------------------------------------

  // .class
  EXPECTED_CLASS_START_DIR,

  //.classend
  EXPECTED_CLASS_END_DIR,

  // .method
  EXPECTED_METHOD_START_DIR,

  // .methodend
  EXPECTED_METHOD_END_DIR,

  // Valid Java identifier
  EXPECTED_CLASS_INDENTIFIER,

  // Fully qualified class name (org/isk/MyClass)
  EXPECTED_CLASS_NAME,

  // Valid Java identifier
  EXPECTED_METHOD_NAME,

  // (
  EXPECTED_METHOD_PARAMETERS_START,

  // )
  EXPECTED_METHOD_PARAMETERS_END,

  // B, C, D, F, I, J, S, Z, [<type>, L<type;
  EXPECTED_METHOD_DESCRIPTOR,

  // Same as method descriptor + V
  EXPECTED_METHOD_RETURN_DESCRIPTOR,

  // (see. MetaInstructions)
  UNKNOWN_MNEMONIC,

  //
  EXPECTED_INTEGER,

  // Number or String
  EXPECTED_LDC_VALUE,
}
