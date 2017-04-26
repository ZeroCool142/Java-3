package ru.geekbrains.java3.l7.testengine;

import ru.geekbrains.java3.l7.annotation.AfterSuite;
import ru.geekbrains.java3.l7.annotation.BeforeSuite;
import ru.geekbrains.java3.l7.annotation.MyParams;
import ru.geekbrains.java3.l7.annotation.MyTest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TestEngine {

    private static Class cls;
    private static Method before;
    private static Method after;
    private static List<Method> tests;
    private static Object[][] parameters;

    public static void start(Class c) {
        startTest(c);
    }


    public static void start(String className) {
        try {
            startTest(Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void startTest(Class c) {
        cls = c;
        try {
            init();
            if (parameters != null) parametrizedTest();
            else simpleTest(cls.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            parameters = null;
            after = null;
            before = null;
        }
    }

    private static void parametrizedTest() throws Exception {
        Constructor c;
        for (int i = 0; i < parameters.length; i++) {
            Class[] types = Arrays.stream(parameters[i]).map(Object::getClass).toArray(size -> new Class[size]);
            c = cls.getConstructor(types);
            simpleTest(c.newInstance(parameters[i]));
        }
    }

    private static void simpleTest(Object obj) throws IllegalAccessException, InvocationTargetException {
        if (before != null) before.invoke(obj);
        for (Method m : tests) {
            try {
                m.invoke(obj);
                if (m.getAnnotation(MyTest.class).exception() == MyTest.None.class)
                    System.out.println("Test " + m.getName() + " pass.");
                else System.out.println("Test " + m.getName() + " failed.");
            } catch (Exception e) {
                if (m.getAnnotation(MyTest.class).exception() == e.getCause().getClass()) {
                    System.out.println("Test " + m.getName() + " pass.");
                } else {
                    System.out.println("Test " + m.getName() + " failed. (" + e.getCause() + ")");
                }
            }
        }
        if (after != null) after.invoke(obj);
    }

    private static void init() {
        int bs = 0, as = 0;
        tests = new LinkedList<>();
        for (Method m : cls.getDeclaredMethods()) {
            for (Annotation a : m.getDeclaredAnnotations()) {
                if (a.annotationType() == BeforeSuite.class) {
                    bs++;
                    before = m;
                } else if (a.annotationType() == AfterSuite.class) {
                    as++;
                    after = m;
                } else if (a.annotationType() == MyTest.class) {
                    tests.add(m);
                } else if (a.annotationType() == MyParams.class) {
                    try {
                        parameters = (Object[][]) m.invoke(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (bs > 1 || as > 1) throw new RuntimeException("Multiple define in " + cls.getName());
        }
        tests.sort((o1, o2) -> {
            int prio1 = o1.getDeclaredAnnotation(MyTest.class).priority();
            int prio2 = o2.getDeclaredAnnotation(MyTest.class).priority();
            return (prio1 > prio2) ? -1 : (prio1 == prio2) ? 0 : 1;
        });

    }
}
