package ru.geekbrains.java3.l7;

public class MyFancyClass {

    public int sum(int a, int b) {
        return a + b;
    }

    public double div(double a, double b) {
        return a / b;
    }

    public boolean arrIsZero(int[] arr) {
        for (int i :
                arr) {
            if (i != 0) throw new RuntimeException();
        }
        return true;
    }

}
