package com.epam.jwd_final.web.dao;

import com.epam.jwd_final.web.domain.Match;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.domain.Sport;
import com.epam.jwd_final.web.domain.Status;
import com.epam.jwd_final.web.mapper.ModelMapper;
import com.epam.jwd_final.web.mapper.impl.MatchModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MatchDao extends AbstractDao<Match> {

    private static final String FIND_ALL_MATCHES_BY_STATUS_SQL =
            "select id, sport_type_id, start, first_team_id, second_team_id, status_id, result_type_id from `match` where status_id = ?";

    private static final String SAVE_MATCH_SQL =
            "insert into `match`(sport_type_id, start, first_team_id, second_team_id) values(?, ?, ?, ?)";

    private static final String FIND_MATCH_ID_BY_START_FIRST_TEAM_SECOND_TEAM_SQL =
            "select id, sport_type_id, start, first_team_id, second_team_id, status_id, result_type_id from `match` where start = ? and first_team_id = ? and second_team_id = ?";

    private static final String UPDATE_RESULT_TYPE_SQL = "update `match` set result_type_id = ? where id = ?";

    private static final String UPDATE_STATUS_TYPE_SQL = "update `match` set status_id = ? where id = ?";
    private static final String FIND_MATCH_BY_ID_SQL = "select id, sport_type_id, start, first_team_id, second_team_id, status_id, result_type_id from `match` where id = ?";

    private final TeamDao teamDao = new TeamDao();

    public Optional<List<Match>> findAllMatchesByStatus(Status status) {
        return querySelect(FIND_ALL_MATCHES_BY_STATUS_SQL, Collections.singletonList(status.getId()));
    }

    public boolean saveMatch(Match match) {
        List<Object> params = new ArrayList<>();
        params.add(match.getSportType().getId());
        params.add(java.sql.Timestamp.valueOf(match.getStart()));
        params.add(teamDao.findIdByName(match.getFirstTeam()).orElseThrow(IllegalArgumentException::new)); // TODO: redo
        params.add(teamDao.findIdByName(match.getSecondTeam()).orElseThrow(IllegalArgumentException::new)); // TODO: redo
        return queryUpdate(SAVE_MATCH_SQL, params);
    }

    public Match createMatch(String sportType, String startTime, String firstTeam, String secondTeam) {
        return new Match(
                Sport.valueOf(sportType.toUpperCase()),
                LocalDateTime.parse(startTime),
                firstTeam,
                secondTeam
        );
    }

    public boolean updateResult(int matchId, Result result) {
        List<Object> params = new ArrayList<>();
        params.add(result.getId());
        params.add(matchId);
        return queryUpdate(UPDATE_RESULT_TYPE_SQL, params);
    }

    public boolean updateStatus(int matchId, Status status) {
        List<Object> params = new ArrayList<>();
        params.add(status.getId());
        params.add(matchId);
        return queryUpdate(UPDATE_STATUS_TYPE_SQL, params);
    }

    public int findMatchIdByStartAndFirstTeamAndSecondTeam(LocalDateTime start, int firstTeamId, int secondTeamId) {
        List<Object> params = new ArrayList<>();
        params.add(java.sql.Timestamp.valueOf(start));
        params.add(firstTeamId);
        params.add(secondTeamId);
        return querySelectForSingleResult(FIND_MATCH_ID_BY_START_FIRST_TEAM_SECOND_TEAM_SQL, params)
                .map(Match::getId).orElseThrow(IllegalStateException::new); // TODO: throw custom or another thing
    }

    public Result findResultTypeById(int id) {
        return querySelectForSingleResult(FIND_MATCH_BY_ID_SQL, Collections.singletonList(id)).orElseThrow(IllegalArgumentException::new).getResultType();
    }

    public Status findMatchStatusById(int id) {
        return querySelectForSingleResult(FIND_MATCH_BY_ID_SQL, Collections.singletonList(id)).orElseThrow(IllegalArgumentException::new).getStatus();
    }

    @Override
    protected ModelMapper<Match> retrieveModelMapper() {
        return new MatchModelMapper();
    }
}
