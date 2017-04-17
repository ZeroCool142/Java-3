package ru.geekbrains.java3.l6;


/**
 * Написать метод, которому в качестве аргумента передается не пустой одномерный
 * целочисленный массив, метод должен вернуть новый массив, который получен путем
 * вытаскивания элементов из исходного массива, идущих после последней четверки. Входной
 * массив должен содержать хотя бы одну четверку, в противном случае в методе необходимо
 * выбросить RuntimeException.
 * Написать набор тестов для этого метода (варианта 3-4 входных данных)
 * вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ]
 *  Написать метод, который проверяет что массив состоит только из чисел 1 и 4. Если в массиве
 * нет хоть одной 4 или 1, то метод вернет false;
 * Написать набор тестов для этого метода (варианта 3-4 входных данных)
 */
public class ArrayUtils {

    public static int[] trimArray(int[] array) {
        if (array.length == 0) throw new RuntimeException("Array is empty!");
        int index = 0;
        boolean found = false;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == 4) {
                index = i + 1;
                found = true;
            }
        }
        if (!found) throw new RuntimeException("Array doesn't contain 4");
        int i, j;
        int[] n = new int[array.length - index];
        for (i = index, j = 0; i < array.length; i++, j++) {
            n[j] = array[i];
        }

        return n;
    }

    public static boolean checkArray(int[] array) {
        if (array.length == 0) throw new RuntimeException("Array is empty");
        boolean one = false, four = false;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1) one = true;
            else if (array[i] == 4) four = true;
            else return false;
        }

        return one & four;
    }
}
