package com.epam.jwd_final.tiger_bet.service.impl;

import com.epam.jwd_final.tiger_bet.dao.GeneralDao;
import com.epam.jwd_final.tiger_bet.dao.UserDao;
import com.epam.jwd_final.tiger_bet.domain.User;
import com.epam.jwd_final.tiger_bet.service.GeneralService;

import java.util.List;
import java.util.Optional;

public class UserService implements GeneralService<User> {

    private final GeneralDao<User> userDao;

    public UserService(GeneralDao<User> userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<List<User>> findAll() {
        return Optional.empty();
    }
}
