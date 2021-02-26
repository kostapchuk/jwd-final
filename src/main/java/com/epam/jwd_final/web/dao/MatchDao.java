package com.epam.jwd_final.web.dao;

import com.epam.jwd_final.web.domain.Match;
import com.epam.jwd_final.web.exception.DaoException;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MatchDao {
    Optional<Match> findOneById(int id) throws DaoException;

    Optional<Match> findOneByStartByFirstTeamIdBySecondTeamId(Timestamp start, int firstTeamId, int secondTeamId) throws DaoException;

    Optional<List<Match>> findAllByStartOfDateByResultId(LocalDate date, int resultId) throws DaoException;

    void save(Match match) throws DaoException;

    boolean updateResultId(int matchId, int resultId) throws DaoException;

    void deleteById(int id) throws DaoException;
}
