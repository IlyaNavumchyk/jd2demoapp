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
public class User2 {

    @Value("${PROPERTY}")
    private String massage;

    public User2() {
        System.out.println("\nIn user2 constructor #start");
        say();
        System.out.println("In user2 constructor #stop");
    }

    public void say() {
        System.out.println(this + " say " + massage);
    }

    @PostConstruct
    public void initMethod() {
        System.out.println("\nIn user2 init method #start");
        say();
        System.out.println("In user2 init method #stop");
    }

    @PreDestroy
    public void destroyMethod() {
        System.out.println("\nIn user2 destroy method #start");
        say();
        System.out.println("In user2 destroy method #stop");
    }

    @Override
    public String toString() {
        return "user2";
    }
}
