package com.epam.jwd_final.web.mapper.impl;

import com.epam.jwd_final.web.domain.Role;
import com.epam.jwd_final.web.domain.User;
import com.epam.jwd_final.web.exception.ModelMapperException;
import com.epam.jwd_final.web.mapper.ModelMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModelMapper implements ModelMapper<User> {

    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String PASSWORD_COLUMN = "password";
    private static final String BALANCE_COLUMN = "balance";
    private static final String ROLE_COLUMN = "role";

    @Override
    public User mapToEntity(ResultSet rs) throws ModelMapperException {
        try {
            final Integer id = rs.getInt(ID_COLUMN);
            final String name = rs.getString(NAME_COLUMN);
            final String password = rs.getString(PASSWORD_COLUMN);
            final BigDecimal balance = rs.getBigDecimal(BALANCE_COLUMN);
            final int roleId = rs.getInt(ROLE_COLUMN);
            return new User(id, name, password, balance, Role.resolveRoleById(roleId));
        } catch (SQLException e) {
            throw new ModelMapperException(e.getMessage(), e.getCause());
        }
    }
}
