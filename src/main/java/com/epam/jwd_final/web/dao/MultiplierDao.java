package com.epam.jwd_final.web.dao;

import com.epam.jwd_final.web.domain.Multiplier;
import com.epam.jwd_final.web.exception.DaoException;

import java.util.Optional;

public interface MultiplierDao {

    Optional<Multiplier> findOneById(int id) throws DaoException;

    Optional<Multiplier> findOneByMatchIdByResultId(int matchId, int resultId) throws DaoException;

    void save(Multiplier multiplier) throws DaoException;

    void deleteById(int id) throws DaoException;
}
