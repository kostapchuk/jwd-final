package com.epam.jwd_final.tiger_bet.service.impl;

import com.epam.jwd_final.tiger_bet.domain.User;
import com.epam.jwd_final.tiger_bet.factory.impl.UserFactory;
import com.epam.jwd_final.tiger_bet.service.EntityService;

public enum UserService implements EntityService<User> {

    INSTANCE;

    @Override
    public User createEntity(Object ... params) {
        return UserFactory.INSTANCE.create(params);
    }
}
