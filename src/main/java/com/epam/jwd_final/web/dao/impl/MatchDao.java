package com.epam.jwd_final.web.dao.impl;

import com.epam.jwd_final.web.dao.AbstractDao;
import com.epam.jwd_final.web.domain.Match;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.domain.Status;
import com.epam.jwd_final.web.mapper.ModelMapper;
import com.epam.jwd_final.web.mapper.impl.MatchModelMapper;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MatchDao extends AbstractDao<Match> {

    private static final String FIND_ALL_MATCHES_BY_STATUS_SQL =
            "select id, sport_type_id, start, first_team_id, second_team_id, status_id, result_type_id from `match` where status_id = ?";

    private static final String SAVE_MATCH_SQL =
            "insert into `match`(sport_type_id, start, first_team_id, second_team_id) values(?, ?, ?, ?)";

    private static final String FIND_BY_START_BY_FIRST_TEAM_ID_BY_SECOND_TEAM_ID_SQL =
            "select id, sport_type_id, start, first_team_id, second_team_id, status_id, result_type_id from `match` where start = ? and first_team_id = ? and second_team_id = ?";

    private static final String UPDATE_RESULT_TYPE_SQL = "update `match` set result_type_id = ? where id = ?";

    private static final String UPDATE_STATUS_TYPE_SQL = "update `match` set status_id = ? where id = ?";

    private static final String FIND_MATCH_BY_ID_SQL = "select id, sport_type_id, start, first_team_id, second_team_id, status_id, result_type_id from `match` where id = ?";

    private final TeamDao teamDao = new TeamDao();

    public Optional<List<Match>> findAllByStatusId(int statusId) {
        return querySelectAll(
                FIND_ALL_MATCHES_BY_STATUS_SQL,
                Collections.singletonList(statusId)
        );
    }

    public void save(Match match) {
        queryUpdate(
                SAVE_MATCH_SQL,
                Arrays.asList(
                        match.getSportType().getId(),
                        java.sql.Timestamp.valueOf(match.getStart()),
                        teamDao.findIdByName(match.getFirstTeam()).orElseThrow(IllegalArgumentException::new),
                        teamDao.findIdByName(match.getSecondTeam()).orElseThrow(IllegalArgumentException::new)
                )
        );
    }

    public Optional<Match> findOneById(int id) {
        return querySelectOne(
                FIND_MATCH_BY_ID_SQL,
                Collections.singletonList(id)
        );
    }

    public Optional<Match> findOneByStartAndFirstTeamAndSecondTeam(Timestamp start, int firstTeamId, int secondTeamId) {
        return querySelectOne(
                FIND_BY_START_BY_FIRST_TEAM_ID_BY_SECOND_TEAM_ID_SQL,
                Arrays.asList(start, firstTeamId, secondTeamId)
        );
    }

    public boolean updateResult(int matchId, Result result) {
        return queryUpdate(
                UPDATE_RESULT_TYPE_SQL,
                Arrays.asList(result.getId(), matchId)
        );
    }

    public boolean updateStatus(int matchId, Status status) {
        return queryUpdate(
                UPDATE_STATUS_TYPE_SQL,
                Arrays.asList(status.getId(), matchId)
        );
    }

    @Override
    protected ModelMapper<Match> retrieveModelMapper() {
        return new MatchModelMapper();
    }
}
