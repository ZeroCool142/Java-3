package ru.geekbrains.java3.l7.testengine.tests;

import org.junit.Assert;
import ru.geekbrains.java3.l7.MyFancyClass;
import ru.geekbrains.java3.l7.annotation.BeforeSuite;
import ru.geekbrains.java3.l7.annotation.MyParams;
import ru.geekbrains.java3.l7.annotation.MyTest;

public class ParametrizedTest {

    @MyParams
    public static Object[][] data() {
        return new Object[][]{
                {1, 2, 3},
                {0, 2, 2},
                {10, 10, 20},
                {0, 0, 0},
                {-1, -1, -2},
                {1, -1, 0}
        };
    }

    MyFancyClass mfc;

    int x;
    int y;
    int res;

    public ParametrizedTest(Integer x, Integer y, Integer res) {
        this.x = x;
        this.y = y;
        this.res = res;
    }

    @BeforeSuite
    public void before() {
        mfc = new MyFancyClass();
    }

    @MyTest
    public void parametrized() {
        Assert.assertEquals(res, mfc.sum(x, y));
    }
}
