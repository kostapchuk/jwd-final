package com.epam.jwd_final.tiger_bet.factory;

import com.epam.jwd_final.tiger_bet.domain.Entity;

public interface EntityFactory<T extends Entity> {

    // TODO: or user String domain name to create entities

    T create(Object ... params);
}
