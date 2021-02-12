package com.epam.jwd_final.tiger_bet.service.impl;

import com.epam.jwd_final.tiger_bet.dao.UserDao;

public enum UserService {

    INSTANCE;

    private static final UserDao USER_DAO = new UserDao();

}
