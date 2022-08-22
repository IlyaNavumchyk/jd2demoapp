package com.jd2.repository;

import com.jd2.domain.User;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public static final String POSTRGES_DRIVER_NAME = "org.postgresql.Driver";
    public static final String DATABASE_URL = "jdbc:postgresql://localhost:";
    public static final int DATABASE_PORT = 5432;
    public static final String DATABASE_NAME = "/jd2";
    public static final String DATABASE_LOGIN = "postgres";
    public static final String DATABASE_PASSWORD = "root";

    private static final String ID = "id";
    private static final String NAME = "user_name";
    private static final String SURNAME = "surname";
    private static final String BIRTH_DATE = "birth";
    private static final String IS_DELETED = "is_deleted";
    private static final String CREATED = "creation_date";
    private static final String CHANGED = "modification_date";

    public List<User> findAll() {

        final String findAllQuery = "select * from carshop.users order by id";

        List<User> result = new ArrayList<>();

        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            Class.forName(POSTRGES_DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC driver can not be loaded!");
            throw new RuntimeException("JDBC driver can not be loaded!");
        }

        String jdbcUrl = StringUtils.join(DATABASE_URL, DATABASE_PORT, DATABASE_NAME);

        try {
            connection = DriverManager.getConnection(jdbcUrl, DATABASE_LOGIN, DATABASE_PASSWORD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(findAllQuery);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(ID));
                user.setUserName(resultSet.getString(NAME));
                user.setSurname(resultSet.getString(SURNAME));
                user.setBirth(resultSet.getTimestamp(BIRTH_DATE));
                user.setDeleted(resultSet.getBoolean(IS_DELETED));
                user.setCreationDate(resultSet.getTimestamp(CREATED));
                user.setModificationDate(resultSet.getTimestamp(CHANGED));

                result.add(user);
            }

            return result;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL issues");
        }
    }
}
