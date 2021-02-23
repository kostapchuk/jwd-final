package com.epam.jwd_final.web.dao;

import com.epam.jwd_final.web.domain.Bet;
import com.epam.jwd_final.web.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface BetDao {

    Optional<Bet> findOneById(int id) throws DaoException;

    Optional<Bet> findOneByUserIdByMultiplierId(int userId, int multiplierId) throws DaoException;

    Optional<List<Bet>> findAllByUserId(int userId) throws DaoException;

    void save(Bet bet) throws DaoException;

    void deleteById(int id) throws DaoException;
}
