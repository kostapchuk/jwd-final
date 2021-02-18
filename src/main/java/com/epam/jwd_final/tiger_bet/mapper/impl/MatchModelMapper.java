package com.epam.jwd_final.tiger_bet.mapper.impl;

import com.epam.jwd_final.tiger_bet.dao.TeamDao;
import com.epam.jwd_final.tiger_bet.domain.Match;
import com.epam.jwd_final.tiger_bet.domain.Result;
import com.epam.jwd_final.tiger_bet.domain.Sport;
import com.epam.jwd_final.tiger_bet.domain.Status;
import com.epam.jwd_final.tiger_bet.mapper.ModelMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class MatchModelMapper implements ModelMapper<Match> {

    public static final String SPORT_TYPE_ID_COLUMN = "sport_type_id";
    public static final String FIRST_TEAM_ID_COLUMN = "first_team_id";
    public static final String SECOND_TEAM_ID_COLUMN = "second_team_id";
    public static final String STATUS_ID_COLUMN = "status_id";
    public static final String START_COLUMN = "start";
    public static final String RESULT_TYPE_ID_COLUMN = "result_type_id";
    private static final String ID_COLUMN = "id";

    private final TeamDao teamDao = new TeamDao();

    @Override
    public Match mapToEntity(ResultSet rs) throws SQLException {
        final int id = rs.getInt(ID_COLUMN);
        final int sportTypeId = rs.getInt(SPORT_TYPE_ID_COLUMN);
        final LocalDateTime startTime = rs.getTimestamp(START_COLUMN).toLocalDateTime();
        final int firstTeamId = rs.getInt(FIRST_TEAM_ID_COLUMN);
        final int secondTeamId = rs.getInt(SECOND_TEAM_ID_COLUMN);
        final int statusId = rs.getInt(STATUS_ID_COLUMN);
        final int resultTypeId = rs.getInt(RESULT_TYPE_ID_COLUMN);
        return new Match(
                id,
                Sport.resolveSportById(sportTypeId),
                startTime,
                teamDao.findTeamById(firstTeamId).orElse("No team"), // TODO: throw exception
                teamDao.findTeamById(secondTeamId).orElse("No team"), // TODO: throw exception
                Status.resolveStatusById(statusId),
                Result.resolveResultById(resultTypeId)
        );
    }
}
