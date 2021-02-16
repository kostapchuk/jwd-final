package com.epam.jwd_final.tiger_bet.dao;

import com.epam.jwd_final.tiger_bet.domain.Match;
import com.epam.jwd_final.tiger_bet.domain.Status;
import com.epam.jwd_final.tiger_bet.mapper.ModelMapper;
import com.epam.jwd_final.tiger_bet.mapper.impl.MatchModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MatchDao extends AbstractDao<Match> {

    private static final String FIND_BY_STATUS_SQL =
            "select sport_type_id, start, first_team_id, second_team_id, status_id, result_type_id from `match` where status_id = ?";

    private static final String INSERT_MATCH_SQL = "SOME INSERT SQL";


    public Optional<List<Match>> findAllMatchesByStatus(Status status) {
        return querySelect(FIND_BY_STATUS_SQL, Collections.singletonList(status.getId()));
    }

    public void insertMatch(String sportType, String startTime, String firstTeam, String secondTeam) {
        List<Object> params = new ArrayList<>();
        params.add(sportType);
        params.add(startTime);
        params.add(firstTeam);
        params.add(secondTeam);
        queryUpdate(INSERT_MATCH_SQL, params);
    }

    @Override
    protected ModelMapper<Match> retrieveModelMapper() {
        return new MatchModelMapper();
    }
}
