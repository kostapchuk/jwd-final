package com.epam.jwd_final.web.dao.impl;

import com.epam.jwd_final.web.dao.AbstractDao;
import com.epam.jwd_final.web.domain.Match;
import com.epam.jwd_final.web.mapper.ModelMapper;
import com.epam.jwd_final.web.mapper.impl.MatchModelMapper;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MatchDao extends AbstractDao<Match> {

    private static final String FIND_ONE_BY_ID_SQL =
            "select id, sport_type_id, start, first_team_id, second_team_id, status_id, result_type_id from `match` where id = ?";

    private static final String FIND_ONE_BY_START_BY_FIRST_TEAM_ID_BY_SECOND_TEAM_ID_SQL =
            "select id, sport_type_id, start, first_team_id, second_team_id, status_id, result_type_id from `match` where start = ? and first_team_id = ? and second_team_id = ?";

    private static final String FIND_ALL_BY_STATUS_ID_SQL =
            "select id, sport_type_id, start, first_team_id, second_team_id, status_id, result_type_id from `match` where status_id = ?";

    private static final String SAVE_SQL =
            "insert into `match`(sport_type_id, start, first_team_id, second_team_id) values(?, ?, ?, ?)";

    private static final String UPDATE_RESULT_TYPE_ID_SQL =
            "update `match` set result_type_id = ? where id = ?";

    private static final String UPDATE_STATUS_TYPE_ID_SQL =
            "update `match` set status_id = ? where id = ?";

    private final TeamDao teamDao = new TeamDao();

    public Optional<Match> findOneById(int id) {
        return querySelectOne(
                FIND_ONE_BY_ID_SQL,
                Collections.singletonList(id)
        );
    }

    public Optional<Match> findOneByStartByFirstTeamIdBySecondTeamId(Timestamp start, int firstTeamId, int secondTeamId) {
        return querySelectOne(
                FIND_ONE_BY_START_BY_FIRST_TEAM_ID_BY_SECOND_TEAM_ID_SQL,
                Arrays.asList(start, firstTeamId, secondTeamId)
        );
    }

    public Optional<List<Match>> findAllByStatusId(int statusId) {
        return querySelectAll(
                FIND_ALL_BY_STATUS_ID_SQL,
                Collections.singletonList(statusId)
        );
    }

    public void save(Match match) {
        queryUpdate(
                SAVE_SQL,
                Arrays.asList(
                        match.getSportType().getId(),
                        java.sql.Timestamp.valueOf(match.getStart()),
                        teamDao.findIdByName(match.getFirstTeam()).orElseThrow(IllegalArgumentException::new),
                        teamDao.findIdByName(match.getSecondTeam()).orElseThrow(IllegalArgumentException::new)
                )
        );
    }

    public boolean updateResultId(int matchId, int resultId) {
        return queryUpdate(
                UPDATE_RESULT_TYPE_ID_SQL,
                Arrays.asList(resultId, matchId)
        );
    }

    public boolean updateStatusId(int matchId, int statusId) {
        return queryUpdate(
                UPDATE_STATUS_TYPE_ID_SQL,
                Arrays.asList(statusId, matchId)
        );
    }

    @Override
    protected ModelMapper<Match> retrieveModelMapper() {
        return new MatchModelMapper();
    }
}
