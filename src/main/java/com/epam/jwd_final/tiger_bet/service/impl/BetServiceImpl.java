package com.epam.jwd_final.tiger_bet.service.impl;

import com.epam.jwd_final.tiger_bet.dao.BetDao;
import com.epam.jwd_final.tiger_bet.domain.Bet;
import com.epam.jwd_final.tiger_bet.domain.BetDto;
import com.epam.jwd_final.tiger_bet.service.BetService;

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

    private BetDto convertToDto(Bet bet) {
        return new BetDto(bet.getId(), bet.getBetMoney());
    }
}
