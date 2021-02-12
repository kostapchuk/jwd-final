package com.epam.jwd_final.tiger_bet.service.impl;

import com.epam.jwd_final.tiger_bet.dao.UserDAO;
import com.epam.jwd_final.tiger_bet.domain.Role;
import com.epam.jwd_final.tiger_bet.domain.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigDecimal;
import java.util.Optional;

public enum UserService {

    INSTANCE;

    private static final UserDAO USER_DAO = new UserDAO();

    public boolean logIn(String name, String password) {
    }
}
