package ru.geekbrains.java3.l7;

import ru.geekbrains.java3.l7.testengine.TestEngine;
import ru.geekbrains.java3.l7.testengine.tests.SecondTest;

/**
 * Создать класс, который может выполнять «тесты», в качестве тестов выступают классы с наборами
 * методов с аннотациями @Test. Для этого у него должен быть статический метод start(), которому в
 * качестве параметра передается или объект типа Class, или имя класса. Из «класса-теста» вначале
 * должен быть запущен метод с аннотацией @BeforeSuite если такой имеется, далее запущены методы
 * с аннотациями @Test, а по завершению всех тестов – метод с аннотацией @AfterSuite. К каждому
 * тесту необходимо также добавить приоритеты (int числа от 1 до 10), в соответствии с которыми будет
 * выбираться порядок их выполнения, если приоритет одинаковый то порядок не имеет значения.
 * Методы с аннотациями @BeforeSuite и @AfterSuite должны присутствовать в единственном
 * экземпляре, иначе необходимо бросить RuntimeException при запуске «тестирования».
 */
public class MainApp {
    public static void main(String[] args) {
        System.out.println("Parametrized test:");
        TestEngine.start("ru.geekbrains.java3.l7.testengine.tests.ParametrizedTest");
        System.out.println("Second test:");
        TestEngine.start(SecondTest.class);
    }
}
