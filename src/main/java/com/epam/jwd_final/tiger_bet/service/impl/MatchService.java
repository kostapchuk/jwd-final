package com.epam.jwd_final.tiger_bet.service.impl;

import com.epam.jwd_final.tiger_bet.dao.MatchDao;
import com.epam.jwd_final.tiger_bet.domain.Match;
import com.epam.jwd_final.tiger_bet.domain.MatchDto;
import com.epam.jwd_final.tiger_bet.domain.Status;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class MatchService {

    private final MatchDao matchDao;

    public MatchService(MatchDao matchDao) {
        this.matchDao = matchDao;
    }

    public Optional<List<MatchDto>> findAllUnfinishedMatches() {
        return matchDao.findAllMatchesByStatus(Status.PLANNED)
                .map(matches ->
                        matches.stream()
                                .map(this::convertToDto)
                                .collect(toList())
                );
    }

    private MatchDto convertToDto(Match match) {
        return new MatchDto(
                match.getSportType(),
                match.getStart(),
                match.getFirstTeam(),
                match.getSecondTeam(),
                match.getStatus());
    }
}
