package com.epam.jwd_final.tiger_bet.service.impl;

import com.epam.jwd_final.tiger_bet.domain.Match;
import com.epam.jwd_final.tiger_bet.factory.impl.MatchFactory;
import com.epam.jwd_final.tiger_bet.service.EntityService;

public enum MatchService implements EntityService<Match> {

    INSTANCE;

    @Override
    public Match createEntity(Object... params) {
        return MatchFactory.INSTANCE.create(params);
    }
}
