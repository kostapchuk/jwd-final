package com.epam.jwd_final.web.mapper.impl;

import com.epam.jwd_final.web.dao.impl.TeamDao;
import com.epam.jwd_final.web.domain.Match;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.DaoException;
import com.epam.jwd_final.web.exception.ModelMapperException;
import com.epam.jwd_final.web.mapper.ModelMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class MatchModelMapper implements ModelMapper<Match> {

    public static final String FIRST_TEAM_ID_COLUMN = "first_team_id";
    public static final String SECOND_TEAM_ID_COLUMN = "second_team_id";
    public static final String START_COLUMN = "start";
    public static final String RESULT_TYPE_ID_COLUMN = "result_type_id";
    private static final String ID_COLUMN = "id";

    private final TeamDao teamDao = new TeamDao();

    @Override
    public Match mapToEntity(ResultSet rs) throws SQLException, ModelMapperException {
        try {
            final int id = rs.getInt(ID_COLUMN);
            final LocalDateTime startTime = rs.getTimestamp(START_COLUMN).toLocalDateTime();
            final int firstTeamId = rs.getInt(FIRST_TEAM_ID_COLUMN);
            final int secondTeamId = rs.getInt(SECOND_TEAM_ID_COLUMN);
            final int resultTypeId = rs.getInt(RESULT_TYPE_ID_COLUMN);
            return new Match(
                    id,
                    startTime,
                    teamDao.findTeamById(firstTeamId).orElseThrow(ModelMapperException::new),
                    teamDao.findTeamById(secondTeamId).orElseThrow(ModelMapperException::new),
                    Result.resolveResultById(resultTypeId)
            );
        } catch (DaoException e) {
            throw new ModelMapperException(e.getMessage(), e.getCause());
        }
    }
}
