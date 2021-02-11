package com.epam.jwd_final.tiger_bet.dao;

import com.epam.jwd_final.tiger_bet.domain.Bet;
import com.epam.jwd_final.tiger_bet.domain.Result;
import com.epam.jwd_final.tiger_bet.mapper.Mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BetDAO extends AbstractDAO<Bet> {

    private static final String RETRIEVE_ALL_BETS_BY_USER_ID_QUERY = "select * from bet where id = ?";
    private static final String RETRIEVE_BET_MONEY_BY_USER_ID_MULTIPLIER_ID_QUERY =
            "select * from bet where user_id = ? and multiplier_id = ?";
    private static final String RETRIEVE_USER_RESULT_BY_USER_ID_MULTIPLIER_ID_QUERY =
            "select * from bet where user_id = ? and multiplier_id = ?";

    public List<Bet> retrieveAllBetsByUserId(int id) {
        return query(RETRIEVE_ALL_BETS_BY_USER_ID_QUERY, Collections.singletonList(id));
    }

    public BigDecimal retrieveBetMoneyByUserIdAndMultiplierId(int userId, int multiplierId) {
        List<Object> params = new ArrayList<>(2);
        params.add(userId);
        params.add(multiplierId);
        Optional<Bet> bet = queryForSingleResult(RETRIEVE_BET_MONEY_BY_USER_ID_MULTIPLIER_ID_QUERY, params);
        if (bet.isPresent()) {
            return bet.get().getBetMoney();
        }
        return new BigDecimal("0.0"); // TODO what to do here
    }

    public Result retrieveUserResultByUserIdAndMultiplierId(int userId, int multiplierId) {
        List<Object> params = new ArrayList<>(2);
        params.add(userId);
        params.add(multiplierId);
        Optional<Bet> bet = queryForSingleResult(RETRIEVE_BET_MONEY_BY_USER_ID_MULTIPLIER_ID_QUERY, params);
        if (bet.isPresent()) {
            return bet.get().getUserResultType();
        }
        return Result.NO_RESULT; // TODO what to do here
    }

    @Override
    protected Mapper<Bet> retrieveMapper() {
        return null;
    }
}
