package com.epam.jwd_final.web.service.impl;

import com.epam.jwd_final.web.dao.BetDao;
import com.epam.jwd_final.web.dao.UserDao;
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
        return new Bet(userId, multiplierId, betMoney);
    }

    @Override
    public boolean saveBet(Bet bet) {
        return betDao.save(bet);
    }

    @Override
    public void deleteBet(int id) {
        betDao.delete(id);
    }

    public BigDecimal calculateExpectedWin(String name, int multiplierId) {
        return betDao.calculateExpectedWin(name, multiplierId);
    }

    public int findMultiplierIdById(int id) {
        return betDao.findOneById(id)
                .orElseThrow(IllegalArgumentException::new)
                .getMultiplierId();
    }

    public boolean isUserWinner(String userName, int matchId) {
        return betDao.isUserWinner(userName, matchId);
    }

    @Override
    public BigDecimal findBetMoneyById(int id) {
        return betDao.findOneById(id)
                .orElseThrow(IllegalArgumentException::new)
                .getBetMoney();
    }

    @Override
    public BigDecimal findBetMoneyByUserIdAndMultiplierId(int userId, int multiplierId) {
        return betDao.findOneByUserIdByMultiplierId(userId, multiplierId)
                .orElseThrow(IllegalArgumentException::new)
                .getBetMoney();
    }

    private BetDto convertToDto(Bet bet) {
        return new BetDto(bet.getId(), bet.getBetMoney());
    }
}
