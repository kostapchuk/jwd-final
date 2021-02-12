package com.epam.jwd_final.tiger_bet.dao;

import com.epam.jwd_final.tiger_bet.connection.ConnectionPool;
import com.epam.jwd_final.tiger_bet.domain.User;
import com.epam.jwd_final.tiger_bet.mapper.Mapper;
import com.epam.jwd_final.tiger_bet.mapper.impl.UserMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserDAO extends AbstractDAO<User> {

    private static final String RETRIEVE_USER_BY_ID_QUERY = "select * from user where id = ?";
    private static final String RETRIEVE_ALL_EMAILS = "select email from user";
    private static final String RETRIEVE_ALL_NAMES = "select name from user";
    private static final String INSERT_USER_QUERY = "insert into user (name, email, password) values (?, ?, ?)";

    public Optional<User> retrieveUserByID(int id) {
        return queryForSingleResult(RETRIEVE_USER_BY_ID_QUERY, Collections.singletonList(id));
    }

    public List<String> retrieveAllEmails() {
        return retrieveAll(RETRIEVE_ALL_EMAILS, "email");
    }

    public List<String> retrieveAllNames() {
        return retrieveAll(RETRIEVE_ALL_NAMES, "name");
    }

    public boolean saveUser(User user) {
        try {
            final PreparedStatement preparedStatement =
                    ConnectionPool.getInstance().retrieveConnection().prepareStatement(INSERT_USER_QUERY);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            return !preparedStatement.execute();
        } catch (SQLException e) {
            // TODO: log or throw custom exception
        }
        return false;
    }

    private List<String> retrieveAll(String querySQL, String columnName) {
        List<String> objects = new ArrayList<>();
        try {
            final PreparedStatement preparedStatement =
                    ConnectionPool.getInstance().retrieveConnection().prepareStatement(querySQL);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                objects.add(resultSet.getString(columnName));
            }
            return objects;
        } catch (SQLException e) {
            // TODO: log or throw custom exception
        }
        return Collections.emptyList();
    }
    // TODO getAllAdmins() and etc.

    @Override
    protected Mapper<ResultSet, User> retrieveMapper() {
        return UserMapper.INSTANCE;
    }
}
