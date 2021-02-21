package com.epam.jwd_final.web.service.impl;

import com.epam.jwd_final.web.dao.BetDao;
import com.epam.jwd_final.web.domain.Bet;
import com.epam.jwd_final.web.domain.BetDto;
import com.epam.jwd_final.web.service.BetService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class BetServiceImpl implements BetService {

    private final BetDao betDao;

    public BetServiceImpl(BetDao betDao) {
        this.betDao = betDao;
    }

    public Optional<List<BetDto>> findAllBetsByUserName(String name) {
        return betDao.findAllBetsByUserName(name)
                .map(bets -> bets.stream()
                        .map(this::convertToDto)
                        .collect(toList())
                );
    }

    @Override
    public Bet createBet(int userId, int multiplierId, BigDecimal betMoney) {
        return betDao.createBet(userId, multiplierId, betMoney);
    }

    @Override
    public boolean saveBet(Bet bet) {
        return betDao.saveBet(bet);
    }

    @Override
    public void deleteBet(int id) {
        betDao.deleteBet(id);
    }

    public BigDecimal calculateExpectedWin(String name, int multiplierId) {
        return betDao.calculateExpectedWin(name, multiplierId);
    }

    public int findMultiplierIdById(int betId) {
        return betDao.findMultiplierIdById(betId);
    }

    public boolean isUserWinner(String userName, int matchId) {
        return betDao.isUserWinner(userName, matchId);
    }

    @Override
    public BigDecimal findBetMoneyById(int id) {
        return betDao.findBetMoneyById(id);
    }

    @Override
    public BigDecimal findBetMoneyByUserIdAndMultiplierId(int userId, int multiplierId) {
        return betDao.findBetMoneyByUserIdAndMultiplierId(userId, multiplierId);
    }

    private BetDto convertToDto(Bet bet) {
        return new BetDto(bet.getId(), bet.getBetMoney());
    }
}
