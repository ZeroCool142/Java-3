package ru.geekbrains.java3.l4;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MFC extends Thread {

    private boolean power = true;

    private BlockingQueue<Document> printerQueue = new LinkedBlockingQueue<>();
    private BlockingQueue<Document> scannerQueue = new LinkedBlockingQueue<>();


    @Override
    public void run() {
        Thread printerThread = new Thread(new Printer());
        Thread scannerThread = new Thread(new Scanner());
        printerThread.start();
        scannerThread.start();
    }

    public void print(Document doc) {
        try {
            printerQueue.put(doc);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void scan(Document doc) {
        try {
            scannerQueue.put(doc);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void powerOff() {
        System.out.println("Power OFF!\n");
        power = false;
        interrupt();
    }

    private class Printer implements Runnable {

        @Override
        public void run() {
            int printed = 0;
            Document document;
            while (power) {
                if (!printerQueue.isEmpty()) {
                    try {
                        document = printerQueue.take();
                        for (int i = 0; i < document.getPages(); i++) {
                            System.out.format("Printer-> Document #%d: Printed %d pages\n", printed+1,i+1);
                            sleep(200);
                        }
                        printed++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    private class Scanner implements Runnable {

        @Override
        public void run() {
            int scanned = 0;
            Document document;
            while (power) {
                if (!scannerQueue.isEmpty()) {
                    try {
                        document = scannerQueue.take();
                        for (int i = 0; i < document.getPages(); i++) {
                            System.out.format("Scanner-> Document #%d: Scanned %d pages\n", scanned+1,i+1);
                            sleep(200);
                        }
                        scanned++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
