package com.epam.jwd_final.tiger_bet.service;

import com.epam.jwd_final.tiger_bet.domain.MatchDto;

import java.util.List;
import java.util.Optional;

public interface MatchService {

    Optional<List<MatchDto>> findAllUnfinishedMatches();

    void createMatch(String sportType, String startTime, String firstTeam, String secondTeam);
}
