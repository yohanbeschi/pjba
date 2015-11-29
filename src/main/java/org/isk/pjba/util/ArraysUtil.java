package org.isk.pjba.util;

/**
 * An <code>ArraysUtil</code> offers the ability to easily manipulate arrays.
 */
public interface ArraysUtil {

  /**
   * <p>
   * Clones an <code>int</code> array.
   * <p>
   * This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *          is the array to clone, may be {@code null}.
   * @return the cloned array, {@code null} if {@code null} input.
   * @author Apache commons-lang.
   */
  public static int[] clone(final int[] array) {
    if (array == null) {
      return null;
    }
    return array.clone();
  }

  /**
   * Clones a <code>String</code> array.
   *
   * @param array
   *          is the array to clone, may be {@code null}.
   * @return the cloned array, {@code null} if {@code null} input.
   */
  public static String[] clone(final String[] array) {
    if (array == null) {
      return null;
    }
    return array.clone();
  }

  /**
   * <p>
   * Adds all the elements of the given arrays into a new array.
   * <p>
   * The new array contains all of the element of {@code array1} followed by all of the elements {@code array2}. When an
   * array is returned, it is always a new array.
   *
   * @param array1
   *          is the first array whose elements are added to the new array.
   * @param array2
   *          is the second array whose elements are added to the new array.
   * @return the new int[] array.
   * @author Apache commons-lang.
   */
  public static int[] concat(final int[] array1, final int... array2) {
    if (array1 == null) {
      return ArraysUtil.clone(array2);
    } else if (array2 == null) {
      return ArraysUtil.clone(array1);
    }

    final int[] joinedArray = new int[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  /**
   * <p>
   * Adds all the elements of the given arrays into a new array.
   *
   * @param array1
   *          is the first array whose elements are added to the new array.
   * @param array2
   *          is the second array whose elements are added to the new array.
   * @return the new int[] array.
   */
  public static String[] concat(final String[] array1, final String... array2) {
    if (array1 == null) {
      return ArraysUtil.clone(array2);
    } else if (array2 == null) {
      return ArraysUtil.clone(array1);
    }

    final String[] joinedArray = new String[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  /**
   * <p>
   * Increases the size of an array.
   * <p>
   * If the size is 0 or a negative number the array is cloned only.
   *
   * @param array
   *          is the array to be increased.
   * @param increaseSize
   *          is the size to be added to the current size.
   * @return the new int[] array.
   */
  public static String[] resizeArray(final String[] array, final int increaseSize) {
    if (increaseSize <= 0) {
      return ArraysUtil.clone(array);
    }
    final String[] newArray = new String[array.length + increaseSize];
    System.arraycopy(array, 0, newArray, 0, array.length);
    return newArray;
  }

  /**
   * Is the arrays contains the value to find ?
   *
   * @param array
   *          is the array to look into.
   * @param toFind
   *          is the value to find.
   * @return <code>true</code> if the value was found in the array, <code>false</code> otherwise.
   */
  public static boolean contains(final String[] array, final String toFind) {
    if (array == null || toFind == null) {
      return false;
    }

    for (final String s : array) {
      if (s.equals(toFind)) {
        return true;
      }
    }

    return false;
  }
}
