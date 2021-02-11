package com.epam.jwd_final.tiger_bet.dao;

import com.epam.jwd_final.tiger_bet.domain.Match;
import com.epam.jwd_final.tiger_bet.domain.Result;
import com.epam.jwd_final.tiger_bet.mapper.Mapper;
import com.epam.jwd_final.tiger_bet.mapper.impl.MatchMapper;

import java.time.LocalDateTime;

public class MatchDAO extends AbstractDAO<Match> {

    public Result retrieveSportTypeById(int id) {
        return null;
    }

    public String retrieveFirstTeamById(int id) {
        return null;
    }

    public String retrieveSecondTeamById(int id) {
        return null;
    }

    public LocalDateTime retrieveStartById(int id) {
        return null;
    }

    // TODO add get all matches by date/sport type


    @Override
    protected Mapper<Match> retrieveMapper() {
        return MatchMapper.INSTANCE;
    }
}
