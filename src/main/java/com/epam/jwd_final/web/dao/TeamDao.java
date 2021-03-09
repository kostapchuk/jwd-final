package com.epam.jwd_final.web.dao;

import com.epam.jwd_final.web.domain.Team;
import com.epam.jwd_final.web.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface TeamDao {

    Optional<Team> findOneById(int id) throws DaoException;

    Optional<Team> findOneByName(String name) throws DaoException;

    List<Team> findAll() throws DaoException;
}
