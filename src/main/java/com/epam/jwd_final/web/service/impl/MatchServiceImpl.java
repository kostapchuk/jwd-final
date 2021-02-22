package com.epam.jwd_final.web.service.impl;

import com.epam.jwd_final.web.dao.MatchDao;
import com.epam.jwd_final.web.dao.TeamDao;
import com.epam.jwd_final.web.domain.Match;
import com.epam.jwd_final.web.domain.MatchDto;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.domain.Sport;
import com.epam.jwd_final.web.domain.Status;
import com.epam.jwd_final.web.service.MatchService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public enum MatchServiceImpl implements MatchService {

    INSTANCE;

    private final MatchDao matchDao;
    private final TeamDao teamDao;

    MatchServiceImpl() {
        this.matchDao = new MatchDao();
        this.teamDao = new TeamDao();
    }

    @Override
    public Optional<List<MatchDto>> findAllUnfinishedMatches() {
        return matchDao.findAllByStatusId(Status.PLANNED.getId())
                .map(matches ->
                        matches.stream()
                                .map(this::convertToDto)
                                .collect(toList())
                );
    }

    @Override
    public Match createMatch(String sportType, String startTime, String firstTeam, String secondTeam) {
        return new Match(
                Sport.valueOf(sportType.toUpperCase()),
                LocalDateTime.parse(startTime),
                firstTeam,
                secondTeam
        );
    }

    @Override
    public int findMatchIdByStartAndFirstTeamAndSecondTeam(LocalDateTime start, String firstTeam, String secondTeam) {
        return matchDao.findOneByStartAndFirstTeamAndSecondTeam(
                Timestamp.valueOf(start),
                teamDao.findIdByName(firstTeam).orElseThrow(IllegalArgumentException::new), // TODO: redo exception
                teamDao.findIdByName(secondTeam).orElseThrow(IllegalArgumentException::new) // TODO: redo exception
        ).orElseThrow(IllegalArgumentException::new).getId(); // TODO: redo exception
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
    public Result findResultTypeById(int id) {
        return matchDao.findOneById(id).orElseThrow(IllegalArgumentException::new).getResultType();
    }

    @Override
    public void saveMatch(Match match) {
        matchDao.save(match);
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
