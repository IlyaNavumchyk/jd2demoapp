package com.jd2.repository.jdbstamplates;

import com.jd2.domain.User;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.jd2.repository.user.UserTableColumns.BIRTH_DATE;
import static com.jd2.repository.user.UserTableColumns.MODIFICATION_DATE;
import static com.jd2.repository.user.UserTableColumns.CREATION_DATE;
import static com.jd2.repository.user.UserTableColumns.ID;
import static com.jd2.repository.user.UserTableColumns.IS_DELETED;
import static com.jd2.repository.user.UserTableColumns.NAME;
import static com.jd2.repository.user.UserTableColumns.SURNAME;
import static com.jd2.repository.user.UserTableColumns.USER_LOGIN;
import static com.jd2.repository.user.UserTableColumns.USER_PASSWORD;

@Component
public class UserRowMapper implements RowMapper<User> {

    private static final Logger LOG = Logger.getLogger(UserRowMapper.class);

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        LOG.info("User row mapping start");

        User user = User.builder()
                .id(rs.getLong(ID))
                .userName(rs.getString(NAME))
                .surname(rs.getString(SURNAME))
                .birth(rs.getTimestamp(BIRTH_DATE))
                .isDeleted(rs.getBoolean(IS_DELETED))
                .creationDate(rs.getTimestamp(CREATION_DATE))
                .modificationDate(rs.getTimestamp(MODIFICATION_DATE))
                .userLogin(rs.getString(USER_LOGIN))
                .userPassword(rs.getString(USER_PASSWORD))
                .build();

        LOG.info("User row mapping end");

        return user;
    }
}
