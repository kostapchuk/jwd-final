package com.epam.jwd_final.tiger_bet.dao;

import com.epam.jwd_final.tiger_bet.connection.ConnectionPool;
import com.epam.jwd_final.tiger_bet.domain.Role;
import com.epam.jwd_final.tiger_bet.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private final ConnectionPool pool = ConnectionPool.getInstance();

    private static final String GET_USER_BY_ID_QUERY = "select * from user where id = ?";

    public User getUserByID(int id) throws SQLException {
        Connection con = pool.retrieveConnection();
        PreparedStatement prepStatement = con.prepareStatement(GET_USER_BY_ID_QUERY);
        pool.returnConnection(con);
        prepStatement.setInt(1, id);
        ResultSet resultSet = prepStatement.executeQuery();
        resultSet.next();
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getBigDecimal("balance"),
                Role.resolveRoleById(resultSet.getInt("role"))
        );

    }
}
