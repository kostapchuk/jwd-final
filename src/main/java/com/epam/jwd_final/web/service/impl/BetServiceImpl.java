package com.epam.jwd_final.web.service.impl;

import com.epam.jwd_final.web.dao.BetDao;
import com.epam.jwd_final.web.dao.UserDao;
import com.epam.jwd_final.web.dao.impl.BetDaoImpl;
import com.epam.jwd_final.web.dao.impl.UserDaoImpl;
import com.epam.jwd_final.web.domain.Bet;
import com.epam.jwd_final.web.domain.BetDto;
import com.epam.jwd_final.web.domain.Match;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.DaoException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.BetService;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.MultiplierService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum BetServiceImpl implements BetService {

    INSTANCE;

    private final BetDao betDao;

    BetServiceImpl() {
        this.betDao = new BetDaoImpl();
    }

    @Override
    public Optional<List<BetDto>> findAllByUserId(int id) throws ServiceException {
        try {
            List<BetDto> betDtos = new ArrayList<>();
            final List<Bet> bets = betDao.findAllByUserId(id).orElse(Collections.emptyList());
            for (Bet bet : bets) {
                final int multiplierId = BetServiceImpl.INSTANCE.findMultiplierIdById(bet.getId());
                final int matchId = MultiplierServiceImpl.INSTANCE.findMatchIdByMultiplierId(multiplierId);
                final Match match = MatchServiceImpl.INSTANCE.findById(matchId);
                final BigDecimal placedCoefficient = MultiplierServiceImpl.INSTANCE.findCoefficientById(multiplierId);
                final Result placedResult = MultiplierServiceImpl.INSTANCE.findResultById(multiplierId);
                String placedResultStr = parseResult(placedResult, match);
                betDtos.add(convertToDto(bet, match.getStart(),
                        placedResultStr, placedCoefficient, UserServiceImpl.INSTANCE.calculateExpectedWin(id, multiplierId),
                        match.getFirstTeam() + " - " + match.getSecondTeam()));
            }
            if (betDtos.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(betDtos);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    String parseResult(Result placedResult, Match match) {
        String result;
        if (Result.FIRST_TEAM.equals(placedResult)) {
            result = match.getFirstTeam();
        } else if (Result.SECOND_TEAM.equals(placedResult)) {
            result = match.getSecondTeam();
        } else if (Result.DRAW.equals(placedResult)) {
            result = "Draw";
        } else {
            result = "";
        }
        return result;
    }

    @Override
    public Bet createBet(int userId, int multiplierId, BigDecimal betMoney) {
        return new Bet(userId, multiplierId, betMoney);
    }

    @Override
    public void save(Bet bet) throws ServiceException {
        try {
            betDao.save(bet);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        try {
            betDao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean isBetExist(int userId, int multiplierId) throws ServiceException {
        try {
            final Optional<Bet> optionalBet = betDao.findOneByUserIdByMultiplierId(userId, multiplierId);
            return optionalBet.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void deleteAllByMultiplierId(int multiplierId) throws ServiceException {
        try {
            betDao.deleteAllByMultiplierId(multiplierId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<List<Integer>> findAllUserIdByMultiplierId(int multiplierId) throws ServiceException {
        try {
            return betDao.findAllByMultiplierId(multiplierId)
                    .map(bets -> bets
                            .stream()
                            .map(Bet::getUserId)
                            .collect(Collectors.toList())
                    );
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public BigDecimal findBetMoneyByUserIdByMultiplierId(int userId, int multiplierId) throws ServiceException {
        try {
            return betDao
                    .findOneByUserIdByMultiplierId(userId, multiplierId)
                    .orElseThrow(ServiceException::new)
                    .getBetMoney();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public int findMultiplierIdById(int id) throws ServiceException {
        try {
            return betDao.findOneById(id)
                    .orElseThrow(ServiceException::new)
                    .getMultiplierId();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public BigDecimal findBetMoneyById(int id) throws ServiceException {
        try {
            return betDao.findOneById(id)
                    .orElseThrow(ServiceException::new)
                    .getBetMoney();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    private BetDto convertToDto(Bet bet, LocalDateTime start, String placedTeam, BigDecimal placedCoefficient, BigDecimal expectedWin, String opponents) {
        return new BetDto(bet.getId(), start, placedTeam, placedCoefficient, expectedWin, opponents);
    }
}
