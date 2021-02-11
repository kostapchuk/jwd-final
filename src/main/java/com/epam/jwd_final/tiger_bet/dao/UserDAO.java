package com.epam.jwd_final.tiger_bet.dao;

import com.epam.jwd_final.tiger_bet.domain.User;
import com.epam.jwd_final.tiger_bet.mapper.Mapper;
import com.epam.jwd_final.tiger_bet.mapper.impl.UserMapper;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;

public class UserDAO extends AbstractDAO<User> {

    private static final String GET_USER_BY_ID_QUERY = "select * from user where id = ?";

    public Optional<User> getUserByID(int id) throws SQLException {
        return queryForSingleResult(GET_USER_BY_ID_QUERY, Collections.singletonList(id));
    }

    // TODO getAllAdmins() and etc.

    @Override
    protected Mapper<User> retrieveMapper() {
        return UserMapper.INSTANCE;
    }
}
