package com.epam.jwd_final.web.mapper.impl;

import com.epam.jwd_final.web.domain.Bet;
import com.epam.jwd_final.web.exception.ModelMapperException;
import com.epam.jwd_final.web.mapper.ModelMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BetModelMapper implements ModelMapper<Bet> {

    private static final String ID_COLUMN = "id";
    private static final String USER_ID_COLUMN = "user_id";
    private static final String MULTIPLIER_ID_COLUMN = "multiplier_id";
    private static final String BET_MONEY_COLUMN = "bet_money";

    @Override
    public Bet mapToEntity(ResultSet rs) throws ModelMapperException {
        try {
            final int id = rs.getInt(ID_COLUMN);
            final int userId = rs.getInt(USER_ID_COLUMN);
            final int multiplierId = rs.getInt(MULTIPLIER_ID_COLUMN);
            final BigDecimal betMoney = rs.getBigDecimal(BET_MONEY_COLUMN);
            return new Bet(id, userId, multiplierId, betMoney);
        } catch (SQLException e) {
            throw new ModelMapperException(e.getMessage(), e.getCause());
        }
    }
}
