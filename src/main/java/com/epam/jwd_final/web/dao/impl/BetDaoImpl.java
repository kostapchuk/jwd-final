package com.epam.jwd_final.web.dao.impl;

import com.epam.jwd_final.web.dao.AbstractDao;
import com.epam.jwd_final.web.dao.BetDao;
import com.epam.jwd_final.web.domain.Bet;
import com.epam.jwd_final.web.exception.DaoException;
import com.epam.jwd_final.web.mapper.ModelMapper;
import com.epam.jwd_final.web.mapper.impl.BetModelMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BetDaoImpl extends AbstractDao<Bet> implements BetDao {

    private static final String FIND_ONE_BY_ID_SQL =
            "select id, user_id, multiplier_id, bet_money from bet where id = ?";

    private static final String FIND_ONE_BY_USER_ID_BY_MULTIPLIER_ID_SQL =
            "select id, user_id, multiplier_id, bet_money from bet where user_id = ? and multiplier_id = ?";

    private static final String FIND_ALL_BY_USER_ID_SQL =
            "select id, user_id, multiplier_id, bet_money from bet where user_id = ?";

    private static final String FIND_ALL_BY_MULTIPLIER_ID_SQL =
            "select id, user_id, multiplier_id, bet_money from bet where multiplier_id = ?";

    private static final String SAVE_SQL =
            "insert into bet (user_id, multiplier_id, bet_money) values (?, ?, ?)";

    private static final String DELETE_BY_ID_SQL =
            "delete from bet where id = ?";

    private static final String DELETE_ALL_BY_MULTIPLIER_ID_SQL =
            "delete from bet where multiplier_id = ?";

    @Override
    public Optional<Bet> findOneById(int id) throws DaoException {
        return querySelectOne(
                FIND_ONE_BY_ID_SQL,
                Collections.singletonList(id)
        );
    }

    @Override
    public Optional<Bet> findOneByUserIdByMultiplierId(int userId, int multiplierId) throws DaoException {
        return querySelectOne(
                FIND_ONE_BY_USER_ID_BY_MULTIPLIER_ID_SQL,
                Arrays.asList(userId, multiplierId)
        );
    }

    @Override
    public Optional<List<Bet>> findAllByUserId(int userId) throws DaoException {
        return querySelectAll(
                FIND_ALL_BY_USER_ID_SQL,
                Collections.singletonList(userId)
        );
    }

    @Override
    public void save(Bet bet) throws DaoException {
        queryUpdate(
                SAVE_SQL,
                Arrays.asList(bet.getUserId(), bet.getMultiplierId(), bet.getBetMoney())
        );
    }

    @Override
    public void deleteById(int id) throws DaoException { // TODO: replace id with Bet bet
        queryUpdate(
                DELETE_BY_ID_SQL,
                Collections.singletonList(id)
        );
    }

    @Override
    public void deleteAllByMultiplierId(int multiplierId) throws DaoException {
        queryUpdate(
                DELETE_ALL_BY_MULTIPLIER_ID_SQL,
                Collections.singletonList(multiplierId)
        );
    }

    @Override
    public Optional<List<Bet>> findAllByMultiplierId(int multiplierId) throws DaoException {
        return querySelectAll(
                FIND_ALL_BY_MULTIPLIER_ID_SQL,
                Collections.singletonList(multiplierId)
        );
    }

    @Override
    protected ModelMapper<Bet> retrieveModelMapper() {
        return new BetModelMapper();
    }
}
