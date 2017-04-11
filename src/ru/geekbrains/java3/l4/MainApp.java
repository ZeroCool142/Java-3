package ru.geekbrains.java3.l4;


import java.io.IOException;
import java.util.LinkedList;

import static java.lang.Thread.sleep;

/**
        Создать три потока, каждый из которых выводит определенную букву(A, B и C) 5 раз, порядок
        должен быть именно ABСABСABС. Используйте wait/notify/notifyAll.
         Написать совсем небольшой метод, в котором 3 потока построчно пишут данные в файл (штук
        по 10 записей, с периодом в 20 мс)
         Написать класс МФУ на котором возможны одновременная печать и сканирование
        документов, при этом нельзя одновременно печатать два документа или сканировать (при
        печати в консоль выводится сообщения "отпечатано 1, 2, 3,... страницы", при сканировании то
        же самое только "отсканировано...", вывод в консоль все также с периодом в 50 мс.)
*/
public class MainApp {


    public static void main(String[] args) throws InterruptedException {
        int abcCount = 5;
        ABCPrinter printer = new ABCPrinter();
        Thread t1 = new Thread(()->printer.printA(abcCount));
        Thread t2 = new Thread(()->printer.printB(abcCount));
        Thread t3 = new Thread(()->printer.printC(abcCount));
        LinkedList<String> a = new LinkedList<>();

        t1.start();
        t2.start();
        t3.start();

        try (MyWriter mw = new MyWriter("MyWriter.txt")){
            Thread t4 = new Thread(()->mw.write(10));
            Thread t5 = new Thread(()->mw.write(10));
            Thread t6 = new Thread(()->mw.write(10));
            t4.start();
            t5.start();
            t6.start();
            t4.join();
            t5.join();
            t6.join();
        } catch (IOException e){
            e.printStackTrace();
        }


        System.out.println();
        MFC mfc = new MFC();
        mfc.start();
        Document doc = new Document(4);
        Document doc2 = new Document(3);
        Document doc3 = new Document(3);
        mfc.print(doc);
        mfc.scan(doc);
        mfc.print(doc2);
        mfc.print(doc3);
        try {sleep(5000);} catch (InterruptedException e){}
        mfc.powerOff();
        mfc.print(doc);
    }

}

