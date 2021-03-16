package com.epam.jwd_final.web.service.impl;

import com.epam.jwd_final.web.dao.BetDao;
import com.epam.jwd_final.web.dao.impl.BetDaoImpl;
import com.epam.jwd_final.web.domain.Bet;
import com.epam.jwd_final.web.domain.Match;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.domain.dto.BetDto;
import com.epam.jwd_final.web.domain.dto.PreviousBetDto;
import com.epam.jwd_final.web.exception.DaoException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.BetService;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.MultiplierService;
import com.epam.jwd_final.web.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum BetServiceImpl implements BetService {

    INSTANCE;

    private static final String DRAW_RESULT = "Draw";
    private static final String EMPTY_RESULT = "";

    private final BetDao betDao;
    private final UserService userService;
    private final MultiplierService multiplierService;
    private final MatchService matchService;

    BetServiceImpl() {
        this.userService = UserServiceImpl.INSTANCE;
        this.multiplierService = MultiplierServiceImpl.INSTANCE;
        this.matchService = MatchServiceImpl.INSTANCE;
        this.betDao = new BetDaoImpl();
    }

    @Override
    public List<BetDto> findAllActiveByUserId(int userId) throws ServiceException {
        try {
            List<BetDto> activeBetDtos = new ArrayList<>();
            final List<Integer> betIds = betDao.findAllByUserId(userId).stream().map(Bet::getId).collect(Collectors.toList());
            for (int betId : betIds) {
                if (createBetDto(userId, betId).isPresent()) {
                    activeBetDtos.add(createBetDto(userId, betId).get());
                }
            }
            if (activeBetDtos.isEmpty()) {
                return Collections.emptyList();
            }
            return activeBetDtos;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<PreviousBetDto> findAllPreviousByUserId(int userId) throws ServiceException {
        try {
            List<PreviousBetDto> previousBetDtos = new ArrayList<>();
            List<Integer> betIds = betDao.findAllByUserId(userId).stream().map(Bet::getId).collect(Collectors.toList());
            for (int betId : betIds) {
                if (createPreviousBetDto(userId, betId).isPresent()) {
                    previousBetDtos.add(createPreviousBetDto(userId, betId).get());
                }
            }
            if (previousBetDtos.isEmpty()) {
                return Collections.emptyList();
            }
            return previousBetDtos;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
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
    public void deleteAllByMultiplierId(int multiplierId) throws ServiceException {
        try {
            betDao.deleteAllByMultiplierId(multiplierId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Integer> findAllUserIdByMultiplierId(int multiplierId) throws ServiceException {
        try {
            return betDao.findAllByMultiplierId(multiplierId)
                    .stream()
                    .map(Bet::getUserId)
                    .collect(Collectors.toList());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public BigDecimal findBetMoneyByUserIdByMultiplierId(int userId, int multiplierId) throws ServiceException {
        try {
            return betDao.findOneByUserIdByMultiplierId(userId, multiplierId)
                    .orElseThrow(ServiceException::new)
                    .getBetMoney();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void placeBet(int userId, int multiplierId, BigDecimal betMoney) throws ServiceException {
        try {
            if (!betDao.findOneByUserIdByMultiplierId(userId, multiplierId).isPresent()) {
                final BigDecimal currentBalance = userService.findBalanceById(userId);
                final BigDecimal finalBalance = currentBalance.subtract(betMoney);

                if (finalBalance.compareTo(BigDecimal.ZERO) >= 0) {
                    save(createBet(userId, multiplierId, betMoney));
                    userService.decreaseBalance(userId, betMoney);
                }
            }
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

    private BetDto convertToBetDto(int betId, LocalDateTime start, String placedTeam,
                                   BigDecimal placedCoefficient, BigDecimal expectedWin, String opponents) {
        return new BetDto(betId, start, placedTeam, placedCoefficient, expectedWin, opponents);
    }

    private PreviousBetDto convertToPreviousBetDto(BetDto bet, boolean win, BigDecimal placedMoney) {
        return new PreviousBetDto(bet, win, placedMoney);
    }

    private Optional<BetDto> createBetDto(int userId, int betId) throws ServiceException, DaoException {
        final int multiplierId = betDao.findOneById(betId).orElseThrow(ServiceException::new).getMultiplierId();
        final int matchId = multiplierService.findMatchIdByMultiplierId(multiplierId);
        final Match match = matchService.findById(matchId);
        if (match.getResultType() == null) {
            final BigDecimal placedCoefficient = multiplierService.findCoefficientById(multiplierId);
            final Result placedResult = multiplierService.findResultById(multiplierId).orElseThrow(ServiceException::new);
            String placedResultStr = parseResult(placedResult, match);
            final String opponents = match.getFirstTeam() + " - " + match.getSecondTeam();
            return Optional.of(convertToBetDto(betId, match.getStart(),
                    placedResultStr, placedCoefficient, userService.calculateToReturn(userId, multiplierId),
                    opponents));
        }
        return Optional.empty();
    }


    private Optional<PreviousBetDto> createPreviousBetDto(int userId, int betId) throws ServiceException, DaoException {
        final int multiplierId = betDao.findOneById(betId).orElseThrow(ServiceException::new).getMultiplierId();
        final int matchId = multiplierService.findMatchIdByMultiplierId(multiplierId);
        final Match match = matchService.findById(matchId);
        if (match.getResultType() != null) {
            final BigDecimal placedCoefficient = multiplierService.findCoefficientById(multiplierId);
            final Result placedResult = multiplierService.findResultById(multiplierId).orElseThrow(ServiceException::new);
            String placedResultStr = parseResult(placedResult, match);
            final boolean win = placedResult.equals(match.getResultType());
            final String opponents = match.getFirstTeam() + " - " + match.getSecondTeam();
            final BetDto betDto = convertToBetDto(betId, match.getStart(),
                    placedResultStr, placedCoefficient, userService.calculateToReturn(userId, multiplierId),
                    opponents);
            final BigDecimal betMoney = findBetMoneyById(betId);
            return Optional.of(convertToPreviousBetDto(betDto, win, betMoney));
        }
        return Optional.empty();
    }

    private String parseResult(Result placedResult, Match match) {
        String result;
        if (Result.FIRST_TEAM.equals(placedResult)) {
            result = match.getFirstTeam();
        } else if (Result.SECOND_TEAM.equals(placedResult)) {
            result = match.getSecondTeam();
        } else if (Result.DRAW.equals(placedResult)) {
            result = DRAW_RESULT;
        } else {
            result = EMPTY_RESULT;
        }
        return result;
    }

}
