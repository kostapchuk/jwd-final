package com.epam.jwd_final.tiger_bet.service;

import com.epam.jwd_final.tiger_bet.domain.Match;
import com.epam.jwd_final.tiger_bet.domain.MatchDto;
import com.epam.jwd_final.tiger_bet.domain.Result;
import com.epam.jwd_final.tiger_bet.domain.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MatchService {

    Optional<List<MatchDto>> findAllUnfinishedMatches();

    Match createMatch(String sportType, String startTime, String firstTeam, String secondTeam);

    boolean saveMatch(Match match);

    int findMatchIdByStartAndFirstTeamAndSecondTeam(LocalDateTime start, String firstTeam, String secondTeam);

    boolean updateResult(int matchId, Result result);

    boolean updateStatus(int matchId, Status status);
}