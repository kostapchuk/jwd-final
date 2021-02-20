package com.epam.jwd_final.tiger_bet.service;

import com.epam.jwd_final.tiger_bet.domain.Bet;
import com.epam.jwd_final.tiger_bet.domain.BetDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BetService {

    Optional<List<BetDto>> findAllBetsByUserName(String name);

    Bet createBet(int userId, int multiplierId, BigDecimal betMoney);

    boolean saveBet(Bet bet);

    void deleteBet(int id);
}
