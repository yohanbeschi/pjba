package org.isk.pjba.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ArraysUtilTest {

  // -------------------------------------------------------------------------------------------------------------------
  // clone()
  // -------------------------------------------------------------------------------------------------------------------

  // -- int

  @Test
  public void clone_intArray_null() {
    assertThat(ArraysUtil.clone((String[]) null)).isNull();
  }

  @Test
  public void clone_intArray() {
    final int[] array = new int[] { 1, 2, 3, 4, 5 };
    final int[] clonedArray = ArraysUtil.clone(array);
    assertThat(clonedArray).isEqualTo(array);
    assertThat(array == clonedArray).isFalse();
  }

  // -- String

  @Test
  public void clone_stringArray_null() {
    assertThat(ArraysUtil.clone((int[]) null)).isNull();
  }

  @Test
  public void clone_stringArray() {
    final String[] array = new String[] { "1", "2", "3", "4", "5" };
    final String[] clonedArray = ArraysUtil.clone(array);
    assertThat(clonedArray).isEqualTo(array);
    assertThat(array == clonedArray).isFalse();
  }

  // -------------------------------------------------------------------------------------------------------------------
  // concat()
  // -------------------------------------------------------------------------------------------------------------------

  // -- int

  @Test
  public void concat_intArray_null_empty() {
    final int[] array = ArraysUtil.concat((int[]) null);
    assertThat(array).isEqualTo(new int[0]);
  }

  @Test
  public void concat_intArray_null_null() {
    final int[] array = ArraysUtil.concat((int[]) null, (int[]) null);
    assertThat(array).isNull();
  }

  @Test
  public void concat_intArray_notEmpty_empty() {
    final int[] array = new int[] { 1, 2, 3, 4, 5 };
    final int[] concatArray = ArraysUtil.concat(array);
    assertThat(concatArray).isEqualTo(array);
    assertThat(array == concatArray).isFalse();
  }

  @Test
  public void concat_intArray_notEmpty_null() {
    final int[] array = new int[] { 1, 2, 3, 4, 5 };
    final int[] concatArray = ArraysUtil.concat(array, (int[]) null);
    assertThat(concatArray).isEqualTo(array);
    assertThat(array == concatArray).isFalse();
  }

  @Test
  public void concat_intArray_notEmpty_notEmpty() {
    final int[] array1 = new int[] { 1, 2, 3, 4, 5 };
    final int[] array2 = new int[] { 6, 7, 8, 9, 10 };
    final int[] expectedArray = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
    final int[] concatArray = ArraysUtil.concat(array1, array2);
    assertThat(concatArray).isEqualTo(expectedArray);
    assertThat(array1 == concatArray).isFalse();
    assertThat(array2 == concatArray).isFalse();
  }

  // -- String

  @Test
  public void concat_stringArray_null_empty() {
    final String[] array = ArraysUtil.concat((String[]) null);
    assertThat(array).isEqualTo(new String[0]);
  }

  @Test
  public void concat_stringArray_null_null() {
    final String[] array = ArraysUtil.concat((String[]) null, (String[]) null);
    assertThat(array).isNull();
  }

  @Test
  public void concat_stringArray_notEmpty_empty() {
    final String[] array = new String[] { "1", "2", "3", "4", "5" };
    final String[] concatArray = ArraysUtil.concat(array);
    assertThat(concatArray).isEqualTo(array);
    assertThat(array == concatArray).isFalse();
  }

  @Test
  public void concat_stringArray_notEmpty_null() {
    final String[] array = new String[] { "1", "2", "3", "4", "5" };
    final String[] concatArray = ArraysUtil.concat(array, (String[]) null);
    assertThat(concatArray).isEqualTo(array);
    assertThat(array == concatArray).isFalse();
  }

  @Test
  public void concat_stringArray_notEmpty_notEmpty() {
    final String[] array1 = new String[] { "1", "2", "3", "4", "5" };
    final String[] array2 = new String[] { "6", "7", "8", "9", "10" };
    final String[] expectedArray = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
    final String[] concatArray = ArraysUtil.concat(array1, array2);
    assertThat(concatArray).isEqualTo(expectedArray);
    assertThat(array1 == concatArray).isFalse();
    assertThat(array2 == concatArray).isFalse();
  }

  // -------------------------------------------------------------------------------------------------------------------
  // resizeArray()
  // -------------------------------------------------------------------------------------------------------------------

  @Test
  public void resizeArray_zero() {
    final String[] array = new String[] { "1", "2", "3", "4", "5" };
    final String[] resizedArray = ArraysUtil.resizeArray(array, 0);
    assertThat(resizedArray).isEqualTo(array);
    assertThat(array == resizedArray).isFalse();
  }

  @Test
  public void resizeArray_positiveNumber() {
    final String[] array = new String[] { "1", "2", "3", "4", "5" };
    final String[] expectedArray = new String[] { "1", "2", "3", "4", "5", null, null, null, null, null };
    final String[] resizedArray = ArraysUtil.resizeArray(array, 5);
    assertThat(resizedArray).isEqualTo(expectedArray);
    assertThat(array == resizedArray).isFalse();
  }

  @Test
  public void resizeArray_negativeNumber() {
    final String[] array = new String[] { "1", "2", "3", "4", "5" };
    final String[] resizedArray = ArraysUtil.resizeArray(array, -2);
    assertThat(resizedArray).isEqualTo(array);
    assertThat(array == resizedArray).isFalse();
  }

  // -------------------------------------------------------------------------------------------------------------------
  // contains()
  // -------------------------------------------------------------------------------------------------------------------

  @Test
  public void contains_nullInNull() {
    final boolean contains = ArraysUtil.contains(null, null);
    assertThat(contains).isFalse();
  }

  @Test
  public void contains_notNullInNull() {
    final boolean contains = ArraysUtil.contains(null, "string");
    assertThat(contains).isFalse();
  }

  @Test
  public void contains_nullInEmpty() {
    final boolean contains = ArraysUtil.contains(new String[] {}, null);
    assertThat(contains).isFalse();
  }

  @Test
  public void contains_nullInNotEmpty() {
    final boolean contains = ArraysUtil.contains(new String[] { "string" }, null);
    assertThat(contains).isFalse();
  }

  @Test
  public void contains_nullInNotEmptyWithNull() {
    final boolean contains = ArraysUtil.contains(new String[] { "string", null }, null);
    assertThat(contains).isFalse();
  }

  @Test
  public void contains_notNullInNotEmpty() {
    final boolean contains = ArraysUtil.contains(new String[] { "string1", "string2", "String3" }, "string2");
    assertThat(contains).isTrue();
  }
}
