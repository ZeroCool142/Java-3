package ru.geekbrains.java3.l6;


import java.util.Arrays;

public class MainApp {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6};
        int[] arr2 = ArrayUtils.trimArray(arr);
        System.out.println(Arrays.toString(arr2));
        System.out.println(ArrayUtils.checkArray(arr));
    }
}
