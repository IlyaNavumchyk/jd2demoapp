package com.jd2.repository.user;

import com.jd2.domain.User;
import com.jd2.exception.NoSuchEntityException;
import com.jd2.util.DatabasePropertiesReader;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.jd2.repository.user.UserTableColumns.BIRTH_DATE;
import static com.jd2.repository.user.UserTableColumns.CHANGED;
import static com.jd2.repository.user.UserTableColumns.CREATED;
import static com.jd2.repository.user.UserTableColumns.ID;
import static com.jd2.repository.user.UserTableColumns.IS_DELETED;
import static com.jd2.repository.user.UserTableColumns.NAME;
import static com.jd2.repository.user.UserTableColumns.SURNAME;

@Component
@RequiredArgsConstructor
public class UserRepository implements UserRepositoryInterface {

    private final DatabasePropertiesReader databasePropertiesReader;

    @Override
    public User findByID(Long id) {
        final String findByIdQuery = "select * from carshop.users where id = " + id + " and is_deleted = false";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(findByIdQuery)) {

            boolean hasRow = resultSet.next();
            if (hasRow) {
                return userRowMapping(resultSet);
            } else {
                throw new NoSuchEntityException("Entity User with id " + id + " does not exist", 404);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL issues");
        }
    }

    @Override
    public Optional<User> findOne(Long id) {
        return Optional.of(findByID(id));
    }

    public List<User> findAll() {
        return findAll(DEFAULT_FIND_ALL_LIMIT, DEFAULT_FIND_ALL_OFFSET);
    }

    @Override
    public List<User> findAll(int limit, int offset) {
        final String findAllQuery = "select * from carshop.users " +
                "order by id limit " + limit + " offset " + offset;

        List<User> result = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(findAllQuery)) {

            while (resultSet.next()) {
                result.add(userRowMapping(resultSet));
            }
            return result;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL issues");
        }
    }

    @Override
    public User create(User object) {

        final String insertQuery =
                "INSERT INTO carshop.users (user_name, surname, birth, is_deleted, creation_date, modification_date) " +
                        "values (?,?,?,?,?,?);";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, object.getUserName());
            preparedStatement.setString(2, object.getSurname());
            preparedStatement.setTimestamp(3, object.getBirth());
            preparedStatement.setBoolean(4, object.getIsDeleted());
            preparedStatement.setTimestamp(5, object.getCreationDate());
            preparedStatement.setTimestamp(6, object.getModificationDate());

            preparedStatement.executeUpdate();

            ResultSet resultSet = connection.prepareStatement("SELECT currval('carshop.users_id_seq') as last_id").executeQuery();
            resultSet.next();
            long userLastInsertId = resultSet.getLong("last_id");
            resultSet.close();
            return findByID(userLastInsertId);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL issues");
        }
    }

    @Override
    public User update(User object) {

        final String updateQuery =
                "update carshop.users set user_name = ?, surname = ?, birth = ?, is_deleted = ?, creation_date = ?," +
                        " modification_date = ? where id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, object.getUserName());
            preparedStatement.setString(2, object.getSurname());
            preparedStatement.setTimestamp(3, object.getBirth());
            preparedStatement.setBoolean(4, object.getIsDeleted());
            preparedStatement.setTimestamp(5, object.getCreationDate());
            preparedStatement.setTimestamp(6, object.getModificationDate());
            preparedStatement.setLong(7, object.getId());

            preparedStatement.executeUpdate();

            return findByID(object.getId());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL issues");
        }
    }

    @Override
    public User delete(Long id) {

        final String deleteQuery = "update carshop.users set is_deleted = ? where id = " + id;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setBoolean(1, true);

            preparedStatement.executeUpdate();

            final String findUser = "select * from carshop.users where id = " + id;
            ResultSet resultSet = connection.prepareStatement(findUser).executeQuery();
            resultSet.next();
            return userRowMapping(resultSet);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL issues");
        }
    }

    @Override
    public Map<String, Object> getUserStats(boolean isDeleted) {
        final String callFunction =
                "select carshop.get_users_stats(?)";

        Connection connection;
        PreparedStatement statement;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(callFunction);
            statement.setBoolean(1, isDeleted);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            int functionCall = resultSet.getInt(1);

            return Collections.singletonMap("avg", functionCall);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public List<User> find_user_by_name_and_surname(String name, String surname) {
        final String callFunction =
                "select carshop.find_user_by_name_and_surname(?,?)";

        List<User> result = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(callFunction)) {

            statement.setString(1, name);
            statement.setString(2, name);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result.add(findByID(resultSet.getLong(1)));
            }
            resultSet.close();

            if (!result.isEmpty()) {
                return result;
            } else {
                throw new NoSuchEntityException("Entity User with name " + name + " and surname " + surname + " does not exist", 404);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    private Connection getConnection() throws SQLException {
        try {
            Class.forName(databasePropertiesReader.getDriverName());
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }

        String url = databasePropertiesReader.getUrl();
        String port = databasePropertiesReader.getPort();
        String dbName = databasePropertiesReader.getName();
        String login = databasePropertiesReader.getLogin();
        String password = databasePropertiesReader.getPassword();

        String jdbcURL = StringUtils.join(url, port, dbName);

        return DriverManager.getConnection(jdbcURL, login, password);
    }

    private User userRowMapping(ResultSet rs) throws SQLException {
        /*User user = new User();

        user.setId(rs.getLong(ID));
        user.setUserName(rs.getString(NAME));
        user.setSurname(rs.getString(SURNAME));
        user.setBirth(rs.getTimestamp(BIRTH_DATE));
        user.setDeleted(rs.getBoolean(IS_DELETED));
        user.setCreationDate(rs.getTimestamp(CREATED));
        user.setModificationDate(rs.getTimestamp(CHANGED));

        return user;*/

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
