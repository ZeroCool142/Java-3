package ru.geekbrains.java3.l4;


public class ABCPrinter {
    private volatile char currentLetter = 'A';

    public void printA(int count) {
        synchronized (this) {
            try {
                for (int i = 0; i < count; i++) {
                    while (currentLetter != 'A') {
                        this.wait();
                    }
                    System.out.print("A");
                    currentLetter = 'B';
                    this.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printB(int count) {
        synchronized (this) {
            try {
                for (int i = 0; i < count; i++) {
                    while (currentLetter != 'B') {
                        this.wait();
                    }
                    System.out.print("B");
                    currentLetter = 'C';
                    this.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printC(int count) {
        synchronized (this) {
            try {
                for (int i = 0; i < count; i++) {
                    while (currentLetter != 'C') {
                        this.wait();
                    }
                    System.out.print("C");
                    currentLetter = 'A';
                    this.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
