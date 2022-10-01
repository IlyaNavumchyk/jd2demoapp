package com.jd2.repository.user;

import com.jd2.domain.User;
import com.jd2.repository.CRUDRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserRepositoryInterface extends CRUDRepository<Long, User> {

    Map<String, Object> getUserStats(boolean isDeleted);

    List<User> find_user_by_name_and_surname(String name , String surname);

    Optional<User> findByLogin(String login);
}
