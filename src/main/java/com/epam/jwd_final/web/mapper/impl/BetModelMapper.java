package com.epam.jwd_final.tiger_bet.mapper.impl;

import com.epam.jwd_final.tiger_bet.domain.Bet;
import com.epam.jwd_final.tiger_bet.mapper.ModelMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BetModelMapper implements ModelMapper<Bet> {

    @Override
    public Bet mapToEntity(ResultSet rs) throws SQLException {
        final int id = rs.getInt("id");
        final int userId = rs.getInt("user_id");
        final int multiplierId = rs.getInt("multiplier_id");
        final BigDecimal betMoney = rs.getBigDecimal("bet_money");
        return new Bet(id, userId, multiplierId, betMoney);
    }
}
