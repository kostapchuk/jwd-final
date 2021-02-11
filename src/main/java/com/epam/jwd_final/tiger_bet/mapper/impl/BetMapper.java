package com.epam.jwd_final.tiger_bet.mapper.impl;

import com.epam.jwd_final.tiger_bet.domain.Bet;
import com.epam.jwd_final.tiger_bet.domain.Result;
import com.epam.jwd_final.tiger_bet.mapper.Mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BetMapper implements Mapper<Bet> {

    @Override
    public Bet mapFrom(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int userId = rs.getInt("user_id");
        int multiplierId = rs.getInt("multiplier_id");
        BigDecimal betMoney = rs.getBigDecimal("bet_money");
        Result userResult = Result.resolveResultById(rs.getInt("user_result_type_id"));
        return new Bet(id, userId, multiplierId, betMoney, userResult);
    }
}
