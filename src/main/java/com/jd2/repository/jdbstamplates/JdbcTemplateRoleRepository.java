package com.jd2.repository.jdbstamplates;

import com.jd2.domain.Role;
import com.jd2.exception.NoSuchEntityException;
import com.jd2.repository.role.RoleRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.jd2.util.UUIDGenerator.generateUUID;

@Component
@RequiredArgsConstructor
public class JdbcTemplateRoleRepository implements RoleRepositoryInterface {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RoleRowMapper roleRowMapper;

    @Override
    public Role findById(Integer id) {
        try {
            return jdbcTemplate.queryForObject("select * from carshop.roles where id = " + id, roleRowMapper);

        } catch (Exception e) {
            throw new NoSuchEntityException("Entity Role with id " + id + " does not exist", 404, generateUUID());
        }
    }

    @Override
    public Optional<Role> findOne(Integer id) {
        return Optional.of(findById(id));
    }

    @Override
    public List<Role> findAll() {
        return findAll(DEFAULT_FIND_ALL_LIMIT, DEFAULT_FIND_ALL_OFFSET);
    }

    @Override
    public List<Role> findAll(int limit, int offset) {
        return jdbcTemplate.query("select * from carshop.roles limit " + limit + " offset " + offset, roleRowMapper);
    }

    @Override
    public Role create(Role object) {
        final String insertQuery =
                "insert into carshop.roles (role_name, creation_date, modification_date) " +
                        " values (:roleName, :creationDate, :modificationDate);";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("roleName", object.getRoleName());
        mapSqlParameterSource.addValue("creationDate", object.getCreationDate());
        mapSqlParameterSource.addValue("modificationDate", object.getModificationDate());

        namedParameterJdbcTemplate.update(insertQuery, mapSqlParameterSource);

        Integer lastInsertId = namedParameterJdbcTemplate.query("SELECT currval('carshop.roles_id_seq') as last_id",
                resultSet -> {
                    resultSet.next();
                    return resultSet.getInt("last_id");
                });

        return findById(lastInsertId);
    }

    @Override
    public Role update(Role object) {
        final String updateQuery =
                "update carshop.roles set role_name = :roleName, creation_date = :creationDate, " +
                        "modification_date = :modificationDate where id = :id";

        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("roleName", object.getRoleName());
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
    public Integer delete(Integer id) {
        jdbcTemplate.update("delete from carshop.users where id = " + id);
        return id;
    }

    @Override
    public List<Role> findRolesByUserId(Long userId) {
        return jdbcTemplate.query("select * from carshop.roles " +
                "inner join carshop.l_user_role lur on where roles.id = lru.role_id " +
                "where lru.user_id = " + userId, roleRowMapper);
    }
}
