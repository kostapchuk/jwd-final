package com.epam.jwd_final.tiger_bet.dao;

import com.epam.jwd_final.tiger_bet.domain.Match;
import com.epam.jwd_final.tiger_bet.mapper.Mapper;
import com.epam.jwd_final.tiger_bet.mapper.impl.MatchMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MatchDAO extends AbstractDAO<Match> {

    private static final String RETRIEVE_MATCH_BY_ID_QUERY = "select * from match where id = ?";
    private static final String RETRIEVE_MATCHES_BY_SPORT_TYPE_ID_QUERY = "select * from match where sport_type_id = ?";

    public Optional<Match> retrieveMatchById(int id) {
        return queryForSingleResult(RETRIEVE_MATCH_BY_ID_QUERY, Collections.singletonList(id));
    }

    public List<Match> retrieveAllMatchesBySportTypeId(int id){
        query(RETRIEVE_MATCHES_BY_SPORT_TYPE_ID_QUERY, Collections.singletonList(id));
        return Collections.emptyList();
    }

//    public List<Match> retrieveAllMatchesByFirstTeamId(int id){
//
//    }
//
//    public List<Match> retrieveAllMatchesBySecondTeamId(int id){
//
//    }

    // TODO add get all matches by date/sport type


    @Override
    protected Mapper<Match> retrieveMapper() {
        return MatchMapper.INSTANCE;
    }
}
