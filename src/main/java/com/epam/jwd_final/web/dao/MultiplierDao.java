package com.epam.jwd_final.web.dao;

import com.epam.jwd_final.web.domain.Multiplier;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.mapper.ModelMapper;
import com.epam.jwd_final.web.mapper.impl.MultiplierModelMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MultiplierDao extends AbstractDao<Multiplier> {

    private static final String SAVE_MULTIPLIER_SQL =
            "insert into `multiplier` (match_id, result_type_id, coefficient) values (?, ?, ?)";
    private static final String FIND_ID_SQL = "select id, match_id, result_type_id, coefficient from `multiplier` where match_id = ? and result_type_id = ?";

    private static final String FIND_MULTIPLIER_BY_ID_SQL = "select id, match_id, result_type_id, coefficient from multiplier where id = ?";

    public Multiplier createMultiplier(int matchId, Result result, BigDecimal coefficient) {
        return new Multiplier(matchId, result, coefficient);
    }

    public boolean saveMultiplier(Multiplier multiplier) {
        List<Object> params = new ArrayList<>();
        params.add(multiplier.getMatchId());
        params.add(multiplier.getResult().getId());
        params.add(multiplier.getCoefficient());
        return queryUpdate(SAVE_MULTIPLIER_SQL, params);
    }

    public int findIdByMatchIdAndResult(int matchId, int resultId) {
        List<Object> params = new ArrayList<>();
        params.add(matchId);
        params.add(resultId);
        final Optional<Multiplier> multiplier = querySelectForSingleResult(FIND_ID_SQL, params);
        return multiplier.orElseThrow(IllegalArgumentException::new).getId();
    }

    public BigDecimal findCoefficientById(int id) {
        return querySelectForSingleResult(FIND_MULTIPLIER_BY_ID_SQL,
                Collections.singletonList(id)).orElseThrow(IllegalArgumentException::new).getCoefficient();
    }

    public Result findResultTypeById(int id) {
        return querySelectForSingleResult(FIND_MULTIPLIER_BY_ID_SQL, Collections.singletonList(id)).orElseThrow(IllegalArgumentException::new).getResult();
    }

    @Override
    protected ModelMapper<Multiplier> retrieveModelMapper() {
        return new MultiplierModelMapper();
    }
}
