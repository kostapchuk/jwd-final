package com.epam.jwd_final.tiger_bet.service;

import com.epam.jwd_final.tiger_bet.domain.Entity;

public interface EntityService<T extends Entity> {

    T createEntity(Object ... params);
}
