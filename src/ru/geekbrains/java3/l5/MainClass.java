package ru.geekbrains.java3.l5;

// Домашнее задание
// Организуем гонки
// Есть набор правил:
// Все участники должны стартовать одновременно, несмотря на то что на подготовку у каждого уходит разное время
// В туннель не может заехать одновременно больше половины участников(условность)
// Попробуйте все это синхронизировать
// Только после того как все завершат гонку нужно выдать объявление об окончании
// Можете корректировать классы(в т.ч. конструктор машин)
// и добавлять объекты классов из пакета util.concurrent

import java.util.concurrent.*;

public class MainClass {
    public static final int CARS_COUNT = 4;

    private static final ExecutorService es = Executors.newFixedThreadPool(CARS_COUNT);
    private static CountDownLatch cdl = new CountDownLatch(CARS_COUNT);
    private static LinkedBlockingQueue<Car> resultTable = new LinkedBlockingQueue<>(CARS_COUNT);

    public static void main(String[] args) throws InterruptedException {

        System.out.println("ВАЖНОЕ ОБЪЯВЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(CARS_COUNT), new Road(40));
        for (int i = 0; i < CARS_COUNT; i++) {
            es.execute(new Car(race, 20 + (int) (Math.random() * 10), cdl));
        }
        es.shutdown(); // No need new threads
        cdl.await();

        System.out.println("ВАЖНОЕ ОБЪЯВЕНИЕ >>> Гонка началась!!!");

        while (!es.isTerminated()); // waiting all cars finished race

        System.out.println("ВАЖНОЕ ОБЪЯВЕНИЕ >>> Гонка закончилась!!!");

        System.out.println("\nРЕЗУЛЬТАТЫ:");
        int tbSize = resultTable.size();
        for (int i = 0; i < tbSize; i++){
            System.out.printf("%d: %s\n", i+1, resultTable.take().getName());
        }
    }

    public static void finish(Car car) {
        System.out.println(car.getName() + " финишировал!");
        try {
            resultTable.put(car);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
