package com.epam.jwd_final.web.dao;

import com.epam.jwd_final.web.domain.AbstractEntity;
import com.epam.jwd_final.web.domain.Bet;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.domain.Status;
import com.epam.jwd_final.web.mapper.ModelMapper;
import com.epam.jwd_final.web.mapper.impl.BetModelMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BetDao extends AbstractDao<Bet> {

    private static final String FIND_BY_USER_ID_SQL =
            "select id, user_id, multiplier_id, bet_money from bet where user_id = ?";

    private static final String SAVE_BET_SQL =
            "insert into bet (user_id, multiplier_id, bet_money) values (?, ?, ?)";

    private static final String DELETE_BY_ID_SQL =
            "delete from bet where id = ?";

    private static final String FIND_BY_USER_ID_BY_MULTIPLIER_ID_SQL =
            "select id, user_id, multiplier_id, bet_money from bet where user_id = ? and multiplier_id = ?";

    private static final String FIND_BET_BY_USER_ID_AND_MULTIPLIER_SQL = "select id, user_id, multiplier_id, bet_money from bet where user_id = ? and multiplier_id = ?";

    private static final String FIND_ONE_BY_ID_SQL =
            "select id, user_id, multiplier_id, bet_money from bet where id = ?";

    private final UserDao userDao;
    private final MultiplierDao multiplierDao;
    private final MatchDao matchDao;

    public BetDao(UserDao userDao, MultiplierDao multiplierDao, MatchDao matchDao) {
        this.userDao = userDao;
        this.multiplierDao = multiplierDao;
        this.matchDao = matchDao;
    }

    public Optional<List<Bet>> findAllByUserId(int userId) {
        return querySelect(FIND_BY_USER_ID_SQL, Collections.singletonList(userId));
    }

    public boolean save(Bet bet) {
        List<Object> params = new ArrayList<>();
        params.add(bet.getUserId());
        params.add(bet.getMultiplierId());
        params.add(bet.getBetMoney());
        return queryUpdate(SAVE_BET_SQL, params);
    }

    public void delete(int id) { // TODO: replace id with Bet bet
        queryUpdate(DELETE_BY_ID_SQL, Collections.singletonList(id));
    }

    public Optional<Bet> findOneById(int id) {
        return querySelectForSingleResult(FIND_ONE_BY_ID_SQL, Collections.singletonList(id));
    }

    public Optional<Bet> findOneByUserIdByMultiplierId(int userId, int multiplierId) {
        return querySelectForSingleResult(
                FIND_BY_USER_ID_BY_MULTIPLIER_ID_SQL,
                Arrays.asList(userId, multiplierId));
    }

    public Optional<List<Bet>> findAllBetsByUserName(String name) {
        final Optional<Integer> userId = userDao.findOneByName(name).map(AbstractEntity::getId);
        if (!userId.isPresent()) {
            return Optional.empty();
        }
        return querySelect(FIND_BY_USER_ID_SQL, Collections.singletonList(userId.get()));
    }

    public BigDecimal calculateExpectedWin(String name, int multiplierId) {
        final int userId = userDao.findOneByName(name).orElseThrow(IllegalArgumentException::new).getId();
        List<Object> params = new ArrayList<>();
        params.add(userId);
        params.add(multiplierId);
        final Bet bet = querySelectForSingleResult(FIND_BET_BY_USER_ID_AND_MULTIPLIER_SQL, params).orElseThrow(IllegalArgumentException::new);
        final BigDecimal betMoney = bet.getBetMoney();
        return multiplierDao.findCoefficientById(bet.getMultiplierId()).multiply(betMoney);
    }

    public boolean isUserWinner(String userName, int matchId) {
        final int userId = userDao.findOneByName(userName).orElseThrow(IllegalArgumentException::new).getId();
        final Result actualResultType = matchDao.findResultTypeById(matchId);
        final int multiplierId = multiplierDao.findIdByMatchIdAndResult(matchId, actualResultType.getId());
        List<Object> params = new ArrayList<>();
        params.add(userId);
        params.add(multiplierId);
        final Optional<Bet> bet = querySelectForSingleResult(FIND_BET_BY_USER_ID_AND_MULTIPLIER_SQL, params);
        if (!bet.isPresent()) {
            return false;
        }
        final Status matchStatus = matchDao.findMatchStatusById(matchId);
        final Result userResultType = multiplierDao.findResultTypeById(bet.get().getMultiplierId());
        return userResultType.equals(actualResultType) && matchStatus.equals(Status.FINISHED);
    }

    @Override
    protected ModelMapper<Bet> retrieveModelMapper() {
        return new BetModelMapper();
    }
}
