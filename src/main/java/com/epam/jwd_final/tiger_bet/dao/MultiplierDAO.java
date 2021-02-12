package com.epam.jwd_final.tiger_bet.dao;

import com.epam.jwd_final.tiger_bet.domain.Multiplier;
import com.epam.jwd_final.tiger_bet.mapper.Mapper;

import java.sql.ResultSet;

public class MultiplierDAO extends AbstractDAO<Multiplier> {

    public Integer retrieveCoefficientByMatchIdAndResultTypeId(int matchId, int resultTypeId) {
        return null;
    }

    @Override
    protected Mapper<ResultSet, Multiplier> retrieveMapper() {
        return null;
    }
}
