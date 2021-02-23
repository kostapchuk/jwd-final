package com.epam.jwd_final.web.dao.impl;

import com.epam.jwd_final.web.dao.AbstractDao;
import com.epam.jwd_final.web.domain.Match;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.DaoException;
import com.epam.jwd_final.web.mapper.ModelMapper;
import com.epam.jwd_final.web.mapper.impl.MatchModelMapper;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MatchDao extends AbstractDao<Match> {

    private static final String FIND_ONE_BY_ID_SQL =
            "select id, start, first_team_id, second_team_id, result_type_id from `match` where id = ?";

    private static final String FIND_ONE_BY_START_BY_FIRST_TEAM_ID_BY_SECOND_TEAM_ID_SQL =
            "select id, start, first_team_id, second_team_id, result_type_id from `match` where start = ? and first_team_id = ? and second_team_id = ?";

    private static final String FIND_ALL_BY_DATE_BY_RESULT_SQL =
            "select id, start, first_team_id, second_team_id, result_type_id from `match` where date(start) = ? and result_type_id = ?";

    private static final String SAVE_SQL =
            "insert into `match`(start, first_team_id, second_team_id) values(?, ?, ?)";

    private static final String UPDATE_RESULT_TYPE_ID_SQL =
            "update `match` set result_type_id = ? where id = ?";

    private final TeamDao teamDao = new TeamDao();

    public Optional<Match> findOneById(int id) throws DaoException {
        return querySelectOne(
                FIND_ONE_BY_ID_SQL,
                Collections.singletonList(id)
        );
    }

    public Optional<Match> findOneByStartByFirstTeamIdBySecondTeamId(Timestamp start, int firstTeamId, int secondTeamId) throws DaoException {
        return querySelectOne(
                FIND_ONE_BY_START_BY_FIRST_TEAM_ID_BY_SECOND_TEAM_ID_SQL,
                Arrays.asList(start, firstTeamId, secondTeamId)
        );
    }

    public Optional<List<Match>> findAllByStartOfDateByResultId(LocalDate date, int resultId) throws DaoException {
        return querySelectAll(
                FIND_ALL_BY_DATE_BY_RESULT_SQL,
                Arrays.asList(Timestamp.valueOf(date.atStartOfDay()), resultId)
        );
    }

    public void save(Match match) throws DaoException {
        queryUpdate(
                SAVE_SQL,
                Arrays.asList(
                        java.sql.Timestamp.valueOf(match.getStart()),
                        teamDao.findIdByName(match.getFirstTeam()).orElseThrow(DaoException::new),
                        teamDao.findIdByName(match.getSecondTeam()).orElseThrow(DaoException::new)
                )
        );
    }

    public boolean updateResultId(int matchId, int resultId) throws DaoException {
        return queryUpdate(
                UPDATE_RESULT_TYPE_ID_SQL,
                Arrays.asList(resultId, matchId)
        );
    }

    @Override
    protected ModelMapper<Match> retrieveModelMapper() {
        return new MatchModelMapper();
    }
}
