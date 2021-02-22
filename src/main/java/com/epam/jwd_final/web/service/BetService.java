package com.epam.jwd_final.web.service;

import com.epam.jwd_final.web.domain.Bet;
import com.epam.jwd_final.web.domain.BetDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BetService {

    Optional<List<BetDto>> findAllByUserName(String name);

    Bet createBet(int userId, int multiplierId, BigDecimal betMoney);

    void save(Bet bet);

    void deleteById(int id);

    BigDecimal findBetMoneyById(int id);

    int findMultiplierIdById(int id);
}
