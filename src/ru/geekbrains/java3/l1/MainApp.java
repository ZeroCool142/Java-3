package ru.geekbrains.java3.l1;

import ru.geekbrains.java3.l1.fruits.Apple;
import ru.geekbrains.java3.l1.fruits.Orange;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Написать метод {@link #swap(Object[], int, int)}, который меняет два элемента массива местами
 * (массив может быть любого ссылочного типа);
 * <p>
 * Написать метод {@link #convertToArrayList(Object[])}, который преобразует массив в ArrayList;
 * <p>
 * Большая задача: {@link Box}
 */

public class MainApp {
    public static void main(String[] args) {

        Integer[] arrInteger = {1, 2, 3, 4, 5};
        String[] arrString = {"a", "b", "c", "d"};
        swap(arrInteger, 0, arrInteger.length - 1);
        swap(arrString, 0, arrString.length - 1);
        System.out.println(Arrays.toString(arrInteger));
        System.out.println(Arrays.toString(arrString));

        ArrayList<Integer> integerArrayList = convertToArrayList(arrInteger);
        integerArrayList.add(100);
        System.out.println(integerArrayList.toString());
        System.out.println();

        Box<Apple> appleBox = new Box<>();
        for (int i = 0; i < 12; i++) {
            appleBox.put(new Apple());
        }

        Box<Apple> appleBox2 = new Box<>();
        for (int i = 0; i < 12; i++) {
            appleBox2.put(new Apple());
        }

        Box<Orange> orangeBox = new Box<>();
        orangeBox.put(new Orange());

        System.out.println("Apple box #1: " + appleBox.getWeight());
        System.out.println("Apple box #2: " + appleBox2.getWeight());
        if (appleBox.compare(appleBox2)) System.out.println("Boxes weigh the same");
        else System.out.println("Boxes weigh differently");
        System.out.println("Orange box #1: " + orangeBox.getWeight());
        System.out.println();
        appleBox.shift(appleBox2);

        System.out.println("Apple box #1: " + appleBox.getWeight());
        System.out.println("Apple box #2: " + appleBox2.getWeight());
        if (appleBox.compare(appleBox2)) System.out.println("Boxes weigh the same");
        else System.out.println("Boxes weigh differently");
        System.out.println("Orange box #1: " + orangeBox.getWeight());
    }


    /**
     * Swap two elements of array
     *
     * @param <T>         array of any reference type
     * @param array       the array to be swapped
     * @param indexFirst  index of the first value
     * @param indexSecond index of the second value
     */
    public static <T> void swap(T[] array, int indexFirst, int indexSecond) {
        T temp = array[indexFirst];
        array[indexFirst] = array[indexSecond];
        array[indexSecond] = temp;
    }

    /**
     * Convert array to ArrayList
     *
     * @param <T>   array of any reference type
     * @param array the array to convert
     * @return new ArrayList
     */
    public static <T> ArrayList<T> convertToArrayList(T[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }
}


