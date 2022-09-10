package com.jd2;

import com.jd2.aop.CustomAspect;
import com.jd2.domain.User;
import com.jd2.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

public class SpringTest {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext
                = new AnnotationConfigApplicationContext("com.jd2");

        UserService bean = applicationContext.getBean(UserService.class);

        List<User> all = bean.findAll();

        for (User user : all) {
            System.out.println(user);
        }

        System.out.println();

        List<User> list= bean.find_user_by_name_and_surname("au", "eb");

        for (User user : list) {
            System.out.println(user);
        }

        /*Map<String, Object> userStats = bean.getUserStats(true);

        userStats.forEach((s, o) -> System.out.println(s + " " + o));

        User user = bean.findById(16L);
        System.out.println(user);

        Timestamp timestamp = new Timestamp(new Date().getTime());
        User user1 = new User(16L,"WAR", "CRAFT", timestamp, false, timestamp, timestamp);
        System.out.println(user1);

        User user2 = bean.update(user1);
        System.out.println(user2);*/

        System.out.println("\nMethod usage statistics for class JdbcTemplateUserRepository:");
        for(Map.Entry<String, Integer> entry : CustomAspect.methodUsageStatistics.entrySet()) {
            System.out.println(entry);
        }
    }
}
