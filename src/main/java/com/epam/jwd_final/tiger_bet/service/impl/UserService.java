package com.epam.jwd_final.tiger_bet.service.impl;

import com.epam.jwd_final.tiger_bet.domain.User;

public enum UserService implements EntityService<User> {

    INSTANCE;

    @Override
    public User createEntity(Object ... params) {
        return UserFactory.INSTANCE.create(params);
    }
}
