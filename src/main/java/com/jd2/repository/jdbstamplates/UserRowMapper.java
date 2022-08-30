package com.jd2.repository.jdbstamplates;

import com.jd2.domain.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.jd2.repository.user.UserTableColumns.BIRTH_DATE;
import static com.jd2.repository.user.UserTableColumns.CHANGED;
import static com.jd2.repository.user.UserTableColumns.CREATED;
import static com.jd2.repository.user.UserTableColumns.ID;
import static com.jd2.repository.user.UserTableColumns.IS_DELETED;
import static com.jd2.repository.user.UserTableColumns.NAME;
import static com.jd2.repository.user.UserTableColumns.SURNAME;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong(ID))
                .userName(rs.getString(NAME))
                .surname(rs.getString(SURNAME))
                .birth(rs.getTimestamp(BIRTH_DATE))
                .isDeleted(rs.getBoolean(IS_DELETED))
                .creationDate(rs.getTimestamp(CREATED))
                .modificationDate(rs.getTimestamp(CHANGED))
                .build();
    }
}
