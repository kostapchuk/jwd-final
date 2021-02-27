package com.epam.jwd_final.web.dao;

import com.epam.jwd_final.web.domain.User;
import com.epam.jwd_final.web.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<List<User>> findAll() throws DaoException;

    Optional<User> findOneByName(String name) throws DaoException;

    Optional<User> findOneById(int id) throws DaoException;

    boolean save(User user) throws DaoException;

    void updateRoleById(int id, int newRoleId) throws DaoException;

    void rollbackRoleById(int id, int newRoleId) throws DaoException;

    void updateBalance(int id, BigDecimal balance) throws DaoException;
}
