package ru.geekbrains.java3.l4;

import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MyWriter implements Closeable {

    private static int count = 0;
    private Random random = new Random();
    private FileWriter fw;

    public MyWriter(String fileName) throws IOException {
        fw = new FileWriter(fileName);
    }

    public void write(int countLines) {
        synchronized (this) {
            count++;
            int threadNumber = count;
            for (int i = 0; i < countLines; i++) {
                try {
                    fw.write(threadNumber + "-> " + random.nextInt() + "\n");
                    fw.flush();
                    wait(200);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    @Override
    public void close() throws IOException {
        fw.close();
    }
}
