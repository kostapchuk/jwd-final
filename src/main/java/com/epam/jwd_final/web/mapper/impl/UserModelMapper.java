package com.epam.jwd_final.tiger_bet.mapper.impl;

import com.epam.jwd_final.tiger_bet.domain.Role;
import com.epam.jwd_final.tiger_bet.domain.User;
import com.epam.jwd_final.tiger_bet.mapper.ModelMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModelMapper implements ModelMapper<User> {

    private static final String USER_ID_COLUMN = "id";
    private static final String USER_NAME_COLUMN = "name";
    private static final String USER_PASSWORD_COLUMN = "password";
    private static final String USER_BALANCE_COLUMN = "balance";
    private static final String USER_ROLE_COLUMN = "role";

    @Override
    public User mapToEntity(ResultSet rs) throws SQLException {
        final Integer id = rs.getInt(USER_ID_COLUMN);
        final String name = rs.getString(USER_NAME_COLUMN);
        final String password = rs.getString(USER_PASSWORD_COLUMN);
        final BigDecimal balance = rs.getBigDecimal(USER_BALANCE_COLUMN);
        final int roleId = rs.getInt(USER_ROLE_COLUMN);
        return new User(id, name, password, balance, Role.resolveRoleById(roleId));
    }
}
