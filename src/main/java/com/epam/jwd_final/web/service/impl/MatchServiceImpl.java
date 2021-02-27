package com.epam.jwd_final.web.service.impl;

import com.epam.jwd_final.web.dao.impl.MatchDaoImpl;
import com.epam.jwd_final.web.dao.MatchDao;
import com.epam.jwd_final.web.dao.impl.TeamDao;
import com.epam.jwd_final.web.domain.Match;
import com.epam.jwd_final.web.domain.MatchDto;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.DaoException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.BetService;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.MultiplierService;
import com.epam.jwd_final.web.service.UserService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public enum MatchServiceImpl implements MatchService {

    INSTANCE;

    private final MatchDao matchDao;
    private final TeamDao teamDao;

    MatchServiceImpl() {
        this.matchDao = new MatchDaoImpl();
        this.teamDao = new TeamDao();
    }

    @Override
    public Optional<List<MatchDto>> findAllUnfinishedByDateBetween(LocalDate from, LocalDate to) throws ServiceException {
        try {
            return matchDao.findAllUnfinishedByDateBetween(from, to)
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
    public Match createMatch(LocalDateTime start, String firstTeam, String secondTeam) {
        return new Match(start, firstTeam, secondTeam);
    }

    @Override
    public int findMatchIdByStartByFirstTeamBySecondTeam(LocalDateTime start, String firstTeam, String secondTeam) throws ServiceException {
        try {
            return matchDao.findOneByStartByFirstTeamIdBySecondTeamId(
                    Timestamp.valueOf(start),
                    teamDao.findOneByName(firstTeam).orElseThrow(IllegalArgumentException::new).getId(), // TODO: redo exception
                    teamDao.findOneByName(secondTeam).orElseThrow(IllegalArgumentException::new).getId() // TODO: redo exception
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
    public Match findById(int id) throws ServiceException {
        try {
            return matchDao.findOneById(id).orElseThrow(ServiceException::new);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        try {
            matchDao.deleteById(id);
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
                match.getSecondTeam()
        );
    }
}
