package com.epam.jwd_final.web.dao;

import com.epam.jwd_final.web.domain.Match;
import com.epam.jwd_final.web.exception.DaoException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MatchDao {

    Optional<Match> findOneById(int id) throws DaoException;

    Optional<Match> findOneByStartByFirstTeamIdBySecondTeamId(Timestamp start, int firstTeamId, int secondTeamId) throws DaoException;

    List<Match> findAllUnfinishedByDateBetween(LocalDateTime from, LocalDateTime to) throws DaoException;

    void save(LocalDateTime start, int firstTeamId, int secondTeamId) throws DaoException;

    void updateResultId(int matchId, int resultId) throws DaoException;

    void deleteById(int id) throws DaoException;
}
