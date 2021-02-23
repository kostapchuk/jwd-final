package com.epam.jwd_final.web.service;

import com.epam.jwd_final.web.domain.Bet;
import com.epam.jwd_final.web.domain.BetDto;
import com.epam.jwd_final.web.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BetService {

    Optional<List<BetDto>> findAllByUserName(String name) throws ServiceException;

    Bet createBet(int userId, int multiplierId, BigDecimal betMoney);

    void save(Bet bet) throws ServiceException;

    void deleteById(int id) throws ServiceException;

    BigDecimal findBetMoneyById(int id) throws ServiceException;

    int findMultiplierIdById(int id) throws ServiceException;
}
