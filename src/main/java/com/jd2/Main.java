package com.jd2;

import com.jd2.domain.User;
import com.jd2.repository.UserRepository;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserRepository userRepository = new UserRepository();

        List<User> all = userRepository.findAll();

        all.forEach(System.out::println);
    }
}
