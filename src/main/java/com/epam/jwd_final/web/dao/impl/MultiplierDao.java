package com.epam.jwd_final.web.dao.impl;

import com.epam.jwd_final.web.dao.AbstractDao;
import com.epam.jwd_final.web.domain.Multiplier;
import com.epam.jwd_final.web.exception.DaoException;
import com.epam.jwd_final.web.mapper.ModelMapper;
import com.epam.jwd_final.web.mapper.impl.MultiplierModelMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

public class MultiplierDao extends AbstractDao<Multiplier> {

    private static final String FIND_ONE_BY_ID_SQL =
            "select id, match_id, result_type_id, coefficient from multiplier where id = ?";

    private static final String FIND_ONE_BY_MATCH_ID_BY_RESULT_TYPE_ID_SQL =
            "select id, match_id, result_type_id, coefficient from `multiplier` where match_id = ? and result_type_id = ?";

    private static final String SAVE_SQL =
            "insert into `multiplier` (match_id, result_type_id, coefficient) values (?, ?, ?)";

    private static final String DELETE_BY_ID_SQL =
            "delete from `multiplier` where id = ?";


    public Optional<Multiplier> findOneById(int id) throws DaoException {
        return querySelectOne(
                FIND_ONE_BY_ID_SQL,
                Collections.singletonList(id)
        );
    }

    public Optional<Multiplier> findOneByMatchIdByResultId(int matchId, int resultId) throws DaoException {
        return querySelectOne(
                FIND_ONE_BY_MATCH_ID_BY_RESULT_TYPE_ID_SQL,
                Arrays.asList(matchId, resultId)
        );
    }

    public void save(Multiplier multiplier) throws DaoException {
        queryUpdate(
                SAVE_SQL,
                Arrays.asList(multiplier.getMatchId(), multiplier.getResult().getId(), multiplier.getCoefficient())
        );
    }

    public void deleteById(int id) throws DaoException {
        queryUpdate(
                DELETE_BY_ID_SQL,
                Collections.singletonList(id)
        );
    }

    @Override
    protected ModelMapper<Multiplier> retrieveModelMapper() {
        return new MultiplierModelMapper();
    }
}
