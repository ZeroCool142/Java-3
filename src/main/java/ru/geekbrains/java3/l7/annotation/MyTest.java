package ru.geekbrains.java3.l7.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MyTest {
    int priority() default 0;

    Class<? extends Throwable> exception() default MyTest.None.class;

    public static class None extends Throwable {

    }
}
