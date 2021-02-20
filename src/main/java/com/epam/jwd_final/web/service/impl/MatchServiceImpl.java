package com.epam.jwd_final.tiger_bet.service.impl;

import com.epam.jwd_final.tiger_bet.dao.MatchDao;
import com.epam.jwd_final.tiger_bet.dao.TeamDao;
import com.epam.jwd_final.tiger_bet.domain.Match;
import com.epam.jwd_final.tiger_bet.domain.MatchDto;
import com.epam.jwd_final.tiger_bet.domain.Result;
import com.epam.jwd_final.tiger_bet.domain.Status;
import com.epam.jwd_final.tiger_bet.service.MatchService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class MatchServiceImpl implements MatchService {

    private final MatchDao matchDao;
    private final TeamDao teamDao;

    public MatchServiceImpl(MatchDao matchDao, TeamDao teamDao) {
        this.matchDao = matchDao;
        this.teamDao = teamDao;
    }

    @Override
    public Optional<List<MatchDto>> findAllUnfinishedMatches() {
        return matchDao.findAllMatchesByStatus(Status.PLANNED)
                .map(matches ->
                        matches.stream()
                                .map(this::convertToDto)
                                .collect(toList())
                );
    }

    @Override
    public Match createMatch(String sportType, String startTime, String firstTeam, String secondTeam) {
        return matchDao.createMatch(sportType, startTime, firstTeam, secondTeam);
    }

    @Override
    public int findMatchIdByStartAndFirstTeamAndSecondTeam(LocalDateTime start, String firstTeam, String secondTeam) {
        return matchDao.findMatchIdByStartAndFirstTeamAndSecondTeam(
                start,
                teamDao.findIdByName(firstTeam).orElseThrow(IllegalArgumentException::new), // TODO: redo exception
                teamDao.findIdByName(secondTeam).orElseThrow(IllegalArgumentException::new)); // TODO: redo exception
    }

    @Override
    public boolean updateResult(int matchId, Result result) {
        return matchDao.updateResult(matchId, result);
    }

    @Override
    public boolean updateStatus(int matchId, Status status) {
        return matchDao.updateStatus(matchId, status);
    }

    @Override
    public boolean saveMatch(Match match) {
        return matchDao.saveMatch(match);
    }

    private MatchDto convertToDto(Match match) {
        return new MatchDto(
                match.getId(),
                match.getSportType(),
                match.getStart(),
                match.getFirstTeam(),
                match.getSecondTeam(),
                match.getStatus(),
                match.getResultType());
    }
}
