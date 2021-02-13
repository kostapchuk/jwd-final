package com.epam.jwd_final.tiger_bet.dao;

import com.epam.jwd_final.tiger_bet.connection.ConnectionPool;
import com.epam.jwd_final.tiger_bet.domain.Role;
import com.epam.jwd_final.tiger_bet.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao extends AbstractDao<User> {

    private static final Logger LOGGER = LogManager.getLogger(UserDao.class);

    private static final String FIND_BY_NAME_SQL =
            "select name, password, balance, role from user where name = ?";

    private static final String SAVE_USER_SQL =
            "insert into user (name, password) values (?, ?)";

    public Optional<User> findByName(String name) {
        try {
            final PreparedStatement preparedStatement =
                    ConnectionPool.getInstance().retrieveConnection().prepareStatement(FIND_BY_NAME_SQL);
            preparedStatement.setString(1, name);
            final ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            if (rs.isFirst()) {
                return Optional.of(convertToUser(rs));
            }
        } catch (SQLException e) {
            LOGGER.info("Cannot find user with the name: " + name);
        }
        return Optional.empty();
        // TODO: return connection
    }

    public boolean save(User user) {
        try {
            if (!findByName(user.getName()).isPresent()) {
                final PreparedStatement preparedStatement =
                        ConnectionPool.getInstance().retrieveConnection().prepareStatement(SAVE_USER_SQL);
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            LOGGER.info("User with such name exists: " + user.getName());
        }
        return false;
    }

    private User convertToUser(ResultSet rs) throws SQLException {
        final String name = rs.getString("name");
        final String password = rs.getString("password");
        final BigDecimal balance = rs.getBigDecimal("balance");
        final int roleId = rs.getInt("role");
        return new User(name, password, balance, Role.resolveRoleById(roleId));
    }
}
