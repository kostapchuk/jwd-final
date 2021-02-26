package com.epam.jwd_final.web.mapper.impl;

import com.epam.jwd_final.web.domain.Multiplier;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.ModelMapperException;
import com.epam.jwd_final.web.mapper.ModelMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MultiplierModelMapper implements ModelMapper<Multiplier> {

    private static final String ID_COLUMN = "id";
    private static final String MATCH_ID_COLUMN = "match_id";
    private static final String RESULT_TYPE_ID_COLUMN = "result_type_id";
    private static final String COEFFICIENT_COLUMN = "coefficient";

    @Override
    public Multiplier mapToEntity(ResultSet rs) throws ModelMapperException {
        try {
            final int id = rs.getInt(ID_COLUMN);
            final int matchId = rs.getInt(MATCH_ID_COLUMN);
            final int resultTypeId = rs.getInt(RESULT_TYPE_ID_COLUMN);
            final BigDecimal coefficient = rs.getBigDecimal(COEFFICIENT_COLUMN);
            return new Multiplier(id, matchId, Result.resolveResultById(resultTypeId), coefficient);
        } catch (SQLException e) {
            throw new ModelMapperException(e.getMessage(), e.getCause());
        }
    }
}
