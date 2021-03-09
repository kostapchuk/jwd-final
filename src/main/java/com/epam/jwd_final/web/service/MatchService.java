package com.epam.jwd_final.web.service;

import com.epam.jwd_final.web.domain.Match;
import com.epam.jwd_final.web.domain.dto.MatchDto;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.ServiceException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MatchService {

    List<MatchDto> findAllUnfinishedByDateBetween(LocalDate from, LocalDate to) throws ServiceException;

    Match createMatch(LocalDateTime start, String firstTeam, String secondTeam);

    void save(Match match) throws ServiceException;

    int findIdByStartByFirstTeamBySecondTeam(LocalDateTime start, String firstTeam, String secondTeam) throws ServiceException;

    void updateResult(int matchId, Result result) throws ServiceException;

    Result findResultById(int id) throws ServiceException;

    Match findById(int id) throws ServiceException;

    void deleteById(int id) throws ServiceException;
}