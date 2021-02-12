package com.epam.jwd_final.tiger_bet.service.impl;

import com.epam.jwd_final.tiger_bet.dao.UserDAO;
import com.epam.jwd_final.tiger_bet.domain.Role;
import com.epam.jwd_final.tiger_bet.domain.User;
import com.epam.jwd_final.tiger_bet.factory.impl.UserFactory;
import com.epam.jwd_final.tiger_bet.service.EntityService;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigDecimal;

public enum UserService {

    INSTANCE;

    //    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal("0.0");
//    private static final Role DEFAULT_ROLE = Role.CLIENT;
    private static final UserDAO USER_DAO = new UserDAO();

    private final UserDAO userDAO = new UserDAO();

    public User createUser(String name, String email, String password, BigDecimal balance, Role role) {
        return new User(name, email, password, balance, role);
    }

    public boolean saveUser(User user) {
        return USER_DAO.saveUser(user);
    }

    public boolean signUp(String name, String email, String password) {
        final String md5Password = DigestUtils.md5Hex(password);
        if (!isExist(name, email)) {
            return saveUser(createUser(name, email, md5Password, null, null));
        }
        return false;
    }

    private boolean isExist(String name, String email) {
        return userDAO.retrieveAllNames().contains(name) && userDAO.retrieveAllEmails().contains(email);
    }
}
