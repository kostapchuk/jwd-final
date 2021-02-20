package com.epam.jwd_final.web.mapper.impl;

import com.epam.jwd_final.web.domain.Multiplier;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.mapper.ModelMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MultiplierModelMapper implements ModelMapper<Multiplier> {

    private static final String ID_COLUMN = "id";

    @Override
    public Multiplier mapToEntity(ResultSet rs) throws SQLException {
        final int id = rs.getInt(ID_COLUMN);
        final int matchId = rs.getInt("match_id");
        final int resultTypeId = rs.getInt("result_type_id");
        final BigDecimal coefficient = rs.getBigDecimal("coefficient");
        return new Multiplier(id, matchId, Result.resolveResultById(resultTypeId), coefficient);
    }
}
