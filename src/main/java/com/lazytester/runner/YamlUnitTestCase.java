package com.lazytester.runner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface YamlUnitTestCase {

    String[] features() default {};

    String[] tags() default {};   //TODO implement

    String[] plugin() default {}; //TODO implement
}
