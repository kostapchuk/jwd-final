package com.epam.jwd_final.web.dao.impl;

import com.epam.jwd_final.web.dao.AbstractDao;
import com.epam.jwd_final.web.dao.MatchDao;
import com.epam.jwd_final.web.domain.Match;
import com.epam.jwd_final.web.exception.DaoException;
import com.epam.jwd_final.web.mapper.ModelMapper;
import com.epam.jwd_final.web.mapper.impl.MatchModelMapper;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MatchDaoImpl extends AbstractDao<Match> implements MatchDao {

    private static final String FIND_ONE_BY_ID_SQL =
            "select id, start, first_team_id, second_team_id, result_type_id from `match` where id = ?";

    private static final String FIND_ONE_BY_START_BY_FIRST_TEAM_ID_BY_SECOND_TEAM_ID_SQL =
            "select id, start, first_team_id, second_team_id, result_type_id from `match` where start = ? and first_team_id = ? and second_team_id = ?";

    private static final String FIND_ALL_UNFINISHED_BY_DATE_SQL =
            "select id, start, first_team_id, second_team_id, result_type_id from `match` where date(start) = ? and result_type_id IS NULL";

    private static final String SAVE_SQL =
            "insert into `match`(start, first_team_id, second_team_id) values(?, ?, ?)";

    private static final String UPDATE_RESULT_TYPE_ID_SQL =
            "update `match` set result_type_id = ? where id = ?";

    private static final String DELETE_BY_ID_SQL =
            "delete from `match` where id = ?";

    private final TeamDao teamDao = new TeamDao();

    @Override
    public Optional<Match> findOneById(int id) throws DaoException {
        return querySelectOne(
                FIND_ONE_BY_ID_SQL,
                Collections.singletonList(id)
        );
    }

    @Override
    public Optional<Match> findOneByStartByFirstTeamIdBySecondTeamId(Timestamp start, int firstTeamId, int secondTeamId) throws DaoException {
        return querySelectOne(
                FIND_ONE_BY_START_BY_FIRST_TEAM_ID_BY_SECOND_TEAM_ID_SQL,
                Arrays.asList(start, firstTeamId, secondTeamId)
        );
    }

    @Override
    public Optional<List<Match>> findAllUnfinishedByDate(LocalDate date) throws DaoException {
        return querySelectAll(
                FIND_ALL_UNFINISHED_BY_DATE_SQL,
                Collections.singletonList(Timestamp.valueOf(date.atStartOfDay()))
        );
    }

    @Override
    public void save(Match match) throws DaoException {
        queryUpdate(
                SAVE_SQL,
                Arrays.asList(
                        java.sql.Timestamp.valueOf(match.getStart()),
                        teamDao.findOneByName(match.getFirstTeam()).orElseThrow(DaoException::new).getId(),
                        teamDao.findOneByName(match.getSecondTeam()).orElseThrow(DaoException::new).getId()
                )
        );
    }

    @Override
    public boolean updateResultId(int matchId, int resultId) throws DaoException {
        return queryUpdate(
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
