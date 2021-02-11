package com.epam.jwd_final.tiger_bet.factory.impl;

import com.epam.jwd_final.tiger_bet.domain.Role;
import com.epam.jwd_final.tiger_bet.domain.User;
import com.epam.jwd_final.tiger_bet.factory.EntityFactory;

import java.math.BigDecimal;

public enum UserFactory implements EntityFactory<User> {

    INSTANCE;

    // TODO: wrap with try and return optional if exception????
    @Override
    public User create(Object ... params) {
        return new User(
                Integer.parseInt(String.valueOf(params[1])),
                (String) params[1],
                (String) params[1],
                (String) params[1],
                new BigDecimal(String.valueOf(params[1])),
                Role.resolveRoleById(Integer.parseInt(String.valueOf(params[1]))));
    }
}
