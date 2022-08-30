package com.my_test;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Getter
@Setter
@AllArgsConstructor
@PropertySource("classpath:myProperty.properties")
public class User {

    @PropertyAnnotation(property = "LOL")
    @Value("${PROPERTY}")
    private String massage;

    public User() {
        System.out.println("\nIn user constructor #start");
        say();
        System.out.println("In user constructor #stop");
    }

    public void say() {
        System.out.println(this + " say " + massage);
    }

    @PostConstruct
    public void initMethod() {
        System.out.println("\nIn user init method #start");
        say();
        System.out.println("In user init method #stop");
    }

    @PreDestroy
    public void destroyMethod() {
        System.out.println("\nIn user destroy method #start");
        say();
        System.out.println("In user destroy method #stop");
    }

    @Override
    public String toString() {
        return "user";
    }
}
