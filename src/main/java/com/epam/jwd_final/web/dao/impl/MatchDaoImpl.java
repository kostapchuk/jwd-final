package com.epam.jwd_final.web.dao.impl;

import com.epam.jwd_final.web.dao.AbstractDao;
import com.epam.jwd_final.web.dao.MatchDao;
import com.epam.jwd_final.web.domain.Match;
import com.epam.jwd_final.web.exception.DaoException;
import com.epam.jwd_final.web.mapper.ModelMapper;
import com.epam.jwd_final.web.mapper.impl.MatchModelMapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MatchDaoImpl extends AbstractDao<Match> implements MatchDao {

    private static final String FIND_ONE_BY_ID_SQL =
            "select id, start, first_team_id, second_team_id, result_type_id from `match` where id = ?";

    private static final String FIND_ONE_BY_START_BY_FIRST_TEAM_ID_BY_SECOND_TEAM_ID_SQL =
            "select id, start, first_team_id, second_team_id, result_type_id from `match` where start = ? and first_team_id = ? and second_team_id = ?";

    private static final String FIND_ALL_UNFINISHED_BY_DATE_BETWEEN_SQL =
            "select id, start, first_team_id, second_team_id, result_type_id from `match` where date(start) between ? and ? and result_type_id IS NULL";

    private static final String SAVE_SQL =
            "insert into `match`(start, first_team_id, second_team_id) values(?, ?, ?)";

    private static final String UPDATE_RESULT_TYPE_ID_SQL =
            "update `match` set result_type_id = ? where id = ?";

    private static final String DELETE_BY_ID_SQL =
            "delete from `match` where id = ?";

    @Override
    public Optional<Match> findOneById(int id) throws DaoException {
        return querySelectOne(
                FIND_ONE_BY_ID_SQL,
                Collections.singletonList(id)
        );
    }

    @Override
    public Optional<Match> findOneByStartByFirstTeamIdBySecondTeamId(Timestamp start, int firstTeamId, int secondTeamId)
            throws DaoException {
        return querySelectOne(
                FIND_ONE_BY_START_BY_FIRST_TEAM_ID_BY_SECOND_TEAM_ID_SQL,
                Arrays.asList(start, firstTeamId, secondTeamId)
        );
    }

    @Override
    public List<Match> findAllUnfinishedByDateBetween(LocalDateTime from, LocalDateTime to) throws DaoException { // TODO: redo to Timestamp
        return querySelectAll(
                FIND_ALL_UNFINISHED_BY_DATE_BETWEEN_SQL,
                Arrays.asList(Timestamp.valueOf(from), Timestamp.valueOf(to))
        );
    }

    @Override
    public void save(LocalDateTime start, int firstTeamId, int secondTeamId) throws DaoException {
        queryUpdate(
                SAVE_SQL,
                Arrays.asList(Timestamp.valueOf(start), firstTeamId, secondTeamId)
        );
    }

    @Override
    public void updateResultId(int matchId, int resultId) throws DaoException {
        queryUpdate(
                UPDATE_RESULT_TYPE_ID_SQL,
                Arrays.asList(resultId, matchId)
        );
    }

    @Override
    public void deleteById(int id) throws DaoException {
        queryUpdate(
                DELETE_BY_ID_SQL,
                Collections.singletonList(id)
        );
    }

    @Override
    protected ModelMapper<Match> retrieveModelMapper() {
        return new MatchModelMapper();
    }
}
