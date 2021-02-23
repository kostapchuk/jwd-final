package com.epam.jwd_final.web.service.impl;

import com.epam.jwd_final.web.dao.impl.MatchDao;
import com.epam.jwd_final.web.dao.impl.TeamDao;
import com.epam.jwd_final.web.domain.Match;
import com.epam.jwd_final.web.domain.MatchDto;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.DaoException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.MatchService;

import java.sql.Timestamp;
import java.time.LocalDate;
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

    public Optional<List<MatchDto>> findAllByStartOfDateByResult(LocalDate date, Result result) throws ServiceException {
        try {
            return matchDao.findAllByStartOfDateByResultId(date, result.getId())
                    .map(matches ->
                            matches.stream()
                                    .map(this::convertToDto)
                                    .collect(toList())
                    );
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Match createMatch(String startTime, String firstTeam, String secondTeam) {
        return new Match(
                LocalDateTime.parse(startTime),
                firstTeam,
                secondTeam
        );
    }

    @Override
    public int findMatchIdByStartAndFirstTeamAndSecondTeam(LocalDateTime start, String firstTeam, String secondTeam) throws ServiceException {
        try {
            return matchDao.findOneByStartByFirstTeamIdBySecondTeamId(
                    Timestamp.valueOf(start),
                    teamDao.findIdByName(firstTeam).orElseThrow(IllegalArgumentException::new), // TODO: redo exception
                    teamDao.findIdByName(secondTeam).orElseThrow(IllegalArgumentException::new) // TODO: redo exception
            ).orElseThrow(ServiceException::new).getId();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean updateResult(int matchId, Result result) throws ServiceException {
        try {
            return matchDao.updateResultId(matchId, result.getId());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Result findResultTypeById(int id) throws ServiceException {
        try {
            return matchDao.findOneById(id).orElseThrow(ServiceException::new).getResultType();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void saveMatch(Match match) throws ServiceException {
        try {
            matchDao.save(match);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    private MatchDto convertToDto(Match match) {
        return new MatchDto(
                match.getId(),
                match.getStart(),
                match.getFirstTeam(),
                match.getSecondTeam(),
                match.getResultType());
    }
}
