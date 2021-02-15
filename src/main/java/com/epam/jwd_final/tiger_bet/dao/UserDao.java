package com.epam.jwd_final.tiger_bet.dao;

import com.epam.jwd_final.tiger_bet.domain.User;
import com.epam.jwd_final.tiger_bet.mapper.ModelMapper;
import com.epam.jwd_final.tiger_bet.mapper.impl.UserModelMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

public class UserDao extends AbstractDao<User> {

    private static final Logger LOGGER = LogManager.getLogger(UserDao.class);

    private static final String FIND_BY_NAME_SQL =
            "select id, name, password, balance, role from user where name = ?";

    private static final String SAVE_USER_SQL =
            "insert into user (name, password) values (?, ?)";

    public Optional<User> findByName(String name) {
        return querySelectForSingleResult(FIND_BY_NAME_SQL, Collections.singletonList(name));
    }

    public boolean save(User user) {
        return queryUpdate(SAVE_USER_SQL,
                new ArrayList<>(Arrays.asList(user.getName(), user.getPassword())));
    }

    @Override
    protected ModelMapper<User> retrieveModelMapper() {
        return new UserModelMapper();
    }
}
