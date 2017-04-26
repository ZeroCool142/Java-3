package ru.geekbrains.java3.l7.testengine.tests;


import org.junit.Assert;
import ru.geekbrains.java3.l7.MyFancyClass;
import ru.geekbrains.java3.l7.annotation.AfterSuite;
import ru.geekbrains.java3.l7.annotation.BeforeSuite;
import ru.geekbrains.java3.l7.annotation.MyTest;

public class SecondTest {

    MyFancyClass mfc;

    @BeforeSuite
    public void before() {
        System.out.println("@BeforeSuit");
        mfc = new MyFancyClass();
    }

    @MyTest(exception = RuntimeException.class,
            priority = 10)
    public void ckArr2() {
        Assert.assertTrue(mfc.arrIsZero(new int[]{0, 0, 0, 0, 0, 1}));
    }

    @MyTest
    public void divisionDoubleByZero() {
        Assert.assertEquals(Double.POSITIVE_INFINITY, mfc.div(5d, 0), 0.0001);
    }

    @MyTest(priority = 3)
    public void divTest() {
        Assert.assertEquals(10f, mfc.div(100f, 10f), 0.0001);
    }

    @MyTest(priority = 8)
    public void chkArrIsZero() {
        Assert.assertTrue(mfc.arrIsZero(new int[]{0, 0, 0, 0, 0, 0}));
    }

    @MyTest
    public void alwaysFail() {
        throw new AssertionError("It's ok!");
    }

    @AfterSuite
    public void after() {
        System.out.println("@AfterSuit");
    }
}
