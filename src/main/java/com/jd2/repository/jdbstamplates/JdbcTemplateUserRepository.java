package com.jd2.repository.jdbstamplates;

import com.jd2.domain.User;
import com.jd2.repository.user.UserRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Primary
public class JdbcTemplateUserRepository implements UserRepositoryInterface {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final UserRowMapper userRowMapper;

    @Override
    public User findByID(Long id) {
        return jdbcTemplate.queryForObject("select * from carshop.users where id = " + id, userRowMapper);
    }

    @Override
    public Optional<User> findOne(Long id) {
        return Optional.of(findByID(id));
    }

    @Override
    public List<User> findAll() {
        return findAll(DEFAULT_FIND_ALL_LIMIT, DEFAULT_FIND_ALL_OFFSET);
    }

    @Override
    public List<User> findAll(int limit, int offset) {
        return jdbcTemplate.query("select * from carshop.users limit " + limit + " offset " + offset, userRowMapper);
    }

    @Override
    public User create(User object) {
        return null;
    }

    @Override
    public User update(User object) {
        return null;
    }

    @Override
    public User delete(Long id) {
        return null;
    }

    @Override
    public Map<String, Object> getUserStats(boolean isDeleted) {
        return null;
    }

    @Override
    public List<User> find_user_by_name_and_surname(String name, String surname) {
        return null;
    }
}
