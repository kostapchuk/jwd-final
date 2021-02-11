package com.epam.jwd_final.tiger_bet.mapper.impl;

import com.epam.jwd_final.tiger_bet.domain.Match;
import com.epam.jwd_final.tiger_bet.domain.Sport;
import com.epam.jwd_final.tiger_bet.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchMapper implements Mapper<Match> {

    @Override
    public Match mapFrom(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        Sport sport = Sport.FOOTBALL.resolveSportById(rs.getInt("sport_type_id"));
    }
}
