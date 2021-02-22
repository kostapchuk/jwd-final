package com.epam.jwd_final.web.dao.impl;

import com.epam.jwd_final.web.dao.AbstractDao;
import com.epam.jwd_final.web.domain.Multiplier;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.mapper.ModelMapper;
import com.epam.jwd_final.web.mapper.impl.MultiplierModelMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MultiplierDao extends AbstractDao<Multiplier> {

    private static final String FIND_BY_ID_SQL =
            "select id, match_id, result_type_id, coefficient from multiplier where id = ?";

    private static final String FIND_BY_MATCH_ID_BY_RESULT_TYPE_ID_SQL =
            "select id, match_id, result_type_id, coefficient from `multiplier` where match_id = ? and result_type_id = ?";

    private static final String SAVE_SQL =
            "insert into `multiplier` (match_id, result_type_id, coefficient) values (?, ?, ?)";


    public Optional<Multiplier> findOneById(int id) {
        return querySelectOne(
                FIND_BY_ID_SQL,
                Collections.singletonList(id)
        );
    }

    public Optional<Multiplier> findOneByMatchIdByResultId(int matchId, int resultId) {
        return querySelectOne(
                FIND_BY_MATCH_ID_BY_RESULT_TYPE_ID_SQL,
                Arrays.asList(matchId, resultId)
        );
    }

    public void save(Multiplier multiplier) {
        queryUpdate(
                SAVE_SQL,
                Arrays.asList(multiplier.getMatchId(), multiplier.getResult().getId(), multiplier.getCoefficient())
        );
    }

    @Override
    protected ModelMapper<Multiplier> retrieveModelMapper() {
        return new MultiplierModelMapper();
    }
}
