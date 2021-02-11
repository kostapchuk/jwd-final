package com.epam.jwd_final.tiger_bet.dao;

import com.epam.jwd_final.tiger_bet.domain.Bet;
import com.epam.jwd_final.tiger_bet.domain.Result;
import com.epam.jwd_final.tiger_bet.mapper.Mapper;

import java.math.BigDecimal;
import java.util.List;

public class BetDAO extends AbstractDAO<Bet> {

    public List<Bet> retrieveAllBetsByUserId(int id) {
        return null;
    }

    public BigDecimal retrieveBetMoneyByUserIdAndMultiplierId(int id) {
        return null;
    }

    public Result retrieveUserResultByUserIdAndMultiplierId(int id, int multiplier) {
        return null;
    }

    @Override
    protected Mapper<Bet> retrieveMapper() {
        return null;
    }
}
