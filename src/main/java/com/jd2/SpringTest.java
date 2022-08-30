package com.jd2;

import com.jd2.domain.User;
import com.jd2.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class SpringTest {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext
                = new AnnotationConfigApplicationContext("com.jd2");

        UserService bean = applicationContext.getBean(UserService.class);

        List<User> all = bean.findAll();

        for (User user : all) {
            System.out.println(user);
        }
    }
}
