package com.jd2.repository.jdbstamplates;

import com.jd2.domain.User;
import com.jd2.exception.NoSuchEntityException;
import com.jd2.repository.user.UserRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.jd2.util.UUIDGenerator.generateUUID;

@Repository
@RequiredArgsConstructor
@Primary
public class JdbcTemplateUserRepository implements UserRepositoryInterface {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final UserRowMapper userRowMapper;

    @Override
    public User findById(Long id) {
        try {

            return jdbcTemplate.queryForObject("select * from carshop.users where id = " + id, userRowMapper);

        } catch (Exception e) {
            throw new NoSuchEntityException("Entity User with id " + id + " does not exist", 404, generateUUID());
        }
    }

    @Override
    public Optional<User> findOne(Long id) {
        return Optional.of(findById(id));
    }

    @Override
    public List<User> findAll() {
        return findAll(DEFAULT_FIND_ALL_LIMIT, DEFAULT_FIND_ALL_OFFSET);
    }

    @Override
    public List<User> findAll(int limit, int offset) {
        return jdbcTemplate.query("select * from carshop.users order by id limit " + limit + " offset " + offset, userRowMapper);
    }

    @Override
    public User create(User object) {
        final String insertQuery =
                "insert into carshop.users (user_name, surname, birth, is_deleted, creation_date, modification_date) " +
                        " values (:userName, :surname, :birth, :isDeleted, :creationDate, :modificationDate);";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userName", object.getUserName());
        mapSqlParameterSource.addValue("surname", object.getSurname());
        mapSqlParameterSource.addValue("birth", object.getBirth());
        mapSqlParameterSource.addValue("isDeleted", object.getIsDeleted());
        mapSqlParameterSource.addValue("creationDate", object.getCreationDate());
        mapSqlParameterSource.addValue("modificationDate", object.getModificationDate());

        namedParameterJdbcTemplate.update(insertQuery, mapSqlParameterSource);

        Long lastInsertId = namedParameterJdbcTemplate.query("SELECT currval('carshop.users_id_seq') as last_id",
                resultSet -> {
                    resultSet.next();
                    return resultSet.getLong("last_id");
                });

        return findById(lastInsertId);
    }

    @Override
    public User update(User object) {
        final String updateQuery =
                "update carshop.users set user_name = :userName, surname = :surname, birth = :birth, " +
                        "is_deleted = :isDeleted, creation_date = :creationDate, " +
                        "modification_date = :modificationDate where id = :id";

        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("userName", object.getUserName());
            mapSqlParameterSource.addValue("surname", object.getSurname());
            mapSqlParameterSource.addValue("birth", object.getBirth());
            mapSqlParameterSource.addValue("isDeleted", object.getIsDeleted());
            mapSqlParameterSource.addValue("creationDate", object.getCreationDate());
            mapSqlParameterSource.addValue("modificationDate", object.getModificationDate());
            mapSqlParameterSource.addValue("id", object.getId());

            namedParameterJdbcTemplate.update(updateQuery, mapSqlParameterSource);

            return findById(object.getId());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL issues");
        }
    }

    @Override
    public Long delete(Long id) {
        jdbcTemplate.update("delete from carshop.users where id = " + id);
        return id;
    }

    @Override
    public Map<String, Object> getUserStats(boolean isDeleted) {
        final String callFunction = "select carshop.get_users_stats(:flag)";

        try {

            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("flag", isDeleted);

            Integer count = namedParameterJdbcTemplate.query(callFunction, mapSqlParameterSource, resultSet -> {
                resultSet.next();
                return resultSet.getInt(1);
            });

            return Collections.singletonMap("avg", count);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public List<User> find_user_by_name_and_surname(String name, String surname) {
        final String callFunction = "select carshop.find_user_by_name_and_surname(:userName, :surname)";

        List<User> list = new ArrayList<>();
        try {

            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("userName", name);
            mapSqlParameterSource.addValue("surname", surname);

            namedParameterJdbcTemplate.query(callFunction, mapSqlParameterSource, resultSet -> {
                do {
                    list.add(findById(resultSet.getLong(1)));
                } while (resultSet.next());
            });

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
}
