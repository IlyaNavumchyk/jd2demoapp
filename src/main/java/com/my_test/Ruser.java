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
public class Ruser {

    @Value("${PROPERTY}")
    private String massage;

    public Ruser() {
        System.out.println("\nIn ruzer constructor #start");
        say();
        System.out.println("In ruser constructor #stop");
    }

    public void say() {
        System.out.println(this + " say " + massage);
    }

    @PostConstruct
    public void initMethod() {
        System.out.println("\nIn ruser init method #start");
        say();
        System.out.println("In ruser init method #stop");
    }

    @PreDestroy
    public void destroyMethod() {
        System.out.println("\nIn ruser destroy method #start");
        say();
        System.out.println("In ruser destroy method #stop");
    }

    @Override
    public String toString() {
        return "ruser";
    }
}