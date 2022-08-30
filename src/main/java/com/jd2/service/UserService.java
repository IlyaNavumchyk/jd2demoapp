package com.jd2.service;

import com.jd2.domain.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    User findById(Long id);

    Optional<User> findOne(Long id);

    List<User> findAll();

    List<User> findAll(int limit, int offset);

    User create(User object);

    User update(User object);

    Long delete(Long id);

    Map<String, Object> getUserStats(boolean isDeleted);

    List<User> find_user_by_name_and_surname(String name, String surname);
}