package com.epam.jwd_final.tiger_bet.factory.impl;

import com.epam.jwd_final.tiger_bet.domain.Match;
import com.epam.jwd_final.tiger_bet.domain.Sport;
import com.epam.jwd_final.tiger_bet.factory.EntityFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public enum MatchFactory implements EntityFactory<Match> {

    INSTANCE;

    @Override
    public Match create(Object ... params) {
        return new Match(
                Integer.parseInt(String.valueOf(params[0])),
                Sport.FOOTBALL.resolveSportById(Integer.parseInt(String.valueOf(params[1]))),
                LocalDateTime.parse((String) params[2], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                (String) params[3],
                (String) params[4]
        );
    }
}
