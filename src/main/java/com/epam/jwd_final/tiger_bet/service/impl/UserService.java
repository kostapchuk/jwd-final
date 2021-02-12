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

    public User createUser(String name, String email, String password, BigDecimal balance, Role role) {
        return new User(name, email, password, balance, role);
    }

    public boolean saveUser(User user) {
        return USER_DAO.saveUser(user);
    }

    public boolean logIn(String name, String password) {
        final Optional<User> optionalUser = userDAO.retrieveUserByEmail(email);
        boolean passwordEquality = false;
        if (optionalUser.isPresent()) {
            passwordEquality = DigestUtils.md5Hex(password).equals(optionalUser.get().getPassword());
        }
        return userDAO.retrieveAllEmails().contains(email) && passwordEquality;
    }
}
