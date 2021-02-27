package com.epam.jwd_final.web.service;

import com.epam.jwd_final.web.domain.Match;
import com.epam.jwd_final.web.domain.MatchDto;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.ServiceException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MatchService {

    Optional<List<MatchDto>> findAllUnfinishedByDateBetween(LocalDate from, LocalDate to) throws ServiceException;

    Match createMatch(LocalDateTime start, String firstTeam, String secondTeam);

    void saveMatch(Match match) throws ServiceException;

    int findMatchIdByStartByFirstTeamBySecondTeam(LocalDateTime start, String firstTeam, String secondTeam) throws ServiceException;

    boolean updateResult(int matchId, Result result) throws ServiceException;

    Result findResultTypeById(int id) throws ServiceException;

    Match findById(int id) throws ServiceException;

    void deleteById(int id) throws ServiceException;

    void cancel(int id) throws ServiceException;
}