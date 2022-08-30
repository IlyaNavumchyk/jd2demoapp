package com.my_test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.my_test");

        //Можно посмотреть все Beans

        /*Iterator<String> beanNamesIterator = applicationContext.getBeanFactory().getBeanNamesIterator();

        while (beanNamesIterator.hasNext()) {
            System.out.println(beanNamesIterator.next());
        }*/

        applicationContext.close();
    }
}
