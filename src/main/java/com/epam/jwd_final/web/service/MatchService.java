package com.epam.jwd_final.web.service;

import com.epam.jwd_final.web.domain.Match;
import com.epam.jwd_final.web.domain.MatchDto;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.domain.Status;
import com.epam.jwd_final.web.exception.DaoException;
import com.epam.jwd_final.web.exception.ServiceException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MatchService {

    Optional<List<MatchDto>> findAllByStatus(Status status) throws ServiceException;

    Match createMatch(String sportType, String startTime, String firstTeam, String secondTeam);

    void saveMatch(Match match) throws ServiceException;

    int findMatchIdByStartAndFirstTeamAndSecondTeam(LocalDateTime start, String firstTeam, String secondTeam) throws ServiceException;

    boolean updateResult(int matchId, Result result) throws ServiceException;

    boolean updateStatus(int matchId, Status status) throws ServiceException;

    Result findResultTypeById(int id) throws ServiceException;
}