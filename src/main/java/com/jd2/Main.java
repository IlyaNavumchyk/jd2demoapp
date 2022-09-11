package com.jd2;

import com.jd2.domain.User;
import com.jd2.repository.user.UserRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        UserRepository userRepository = new UserRepository();

        List<User> all = userRepository.findAll();

        all.forEach(System.out::println);

        /*System.out.println(userRepository.findById(11L));
        System.out.println(userRepository.findOne(10L));

        Timestamp timestamp = new Timestamp(new Date().getTime());
        User user = new User("NAUM", "EBASH", timestamp, false, timestamp, timestamp);
        System.out.println(user);
        User user1 = userRepository.create(user);
        System.out.println(user1);

        user.setId(user1.getId());
        user.setUserName("Update Prepared");
        user.setModificationDate(new Timestamp(new Date().getTime()));
        User user2 = userRepository.update(user);
        System.out.println(user2);

        System.out.println(userRepository.delete(20L));

        List<User> alll = userRepository.findAll();

        alll.forEach(System.out::println);

        Map<String, Object> userStats =
                userRepository.getUserStats(true);

        for (Map.Entry<String, Object> stringObjectEntry : userStats.entrySet()) {
            System.out.println(stringObjectEntry.getValue());
        }

        List<User> all = userRepository.find_user_by_name_and_surname("na", "eb");
        all.forEach(System.out::println);

        User user = userRepository.findById(150L);*/
    }
}
