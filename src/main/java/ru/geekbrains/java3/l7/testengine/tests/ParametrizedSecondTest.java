package ru.geekbrains.java3.l7.testengine.tests;

import org.junit.Assert;
import ru.geekbrains.java3.l7.MyFancyClass;
import ru.geekbrains.java3.l7.annotation.AfterSuite;
import ru.geekbrains.java3.l7.annotation.BeforeSuite;
import ru.geekbrains.java3.l7.annotation.MyParams;
import ru.geekbrains.java3.l7.annotation.MyTest;

import java.util.Arrays;

public class ParametrizedSecondTest {
    @MyParams
    public static Object[][] data() {
        return new Object[][]{
                {new Integer[]{0, 0, 0, 0}, true},
                {new Integer[]{0, 0, 0, 1}, false},
                {new Integer[]{1, 1, 1, 1}, false},
        };
    }

    MyFancyClass mfc;


    int[] arr;
    boolean res;

    public ParametrizedSecondTest(Integer[] arr, Boolean res) {
        this.arr = Arrays.stream(arr).mapToInt(i -> i).toArray();
        this.res = res;
    }

    @BeforeSuite
    public void before() {
        System.out.println("@Before");
        mfc = new MyFancyClass();
    }

    @AfterSuite
    public void after() {
        System.out.println("@After");
    }

    @MyTest
    public void parametrized() {
        if (res) {
            Assert.assertTrue(mfc.arrIsZero(arr));
        } else {
            try {
                mfc.arrIsZero(arr);
                Assert.fail();
            } catch (Exception e) {

            }
        }
    }
}
