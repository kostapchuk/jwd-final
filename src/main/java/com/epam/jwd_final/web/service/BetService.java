package com.epam.jwd_final.web.service;

import com.epam.jwd_final.web.domain.Bet;
import com.epam.jwd_final.web.domain.BetDto;
import com.epam.jwd_final.web.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BetService {

    Optional<List<BetDto>> findAllUnfinishedByUserId(int id) throws ServiceException;

    Bet createBet(int userId, int multiplierId, BigDecimal betMoney);

    void save(Bet bet) throws ServiceException;

    void deleteById(int id) throws ServiceException;

    BigDecimal findBetMoneyById(int id) throws ServiceException;

    int findMultiplierIdById(int id) throws ServiceException;

    boolean isBetExist(int userId, int multiplierId) throws ServiceException;

    void deleteAllByMultiplierId(int multiplierId) throws ServiceException;

    Optional<List<Integer>> findAllUserIdByMultiplierId(int multiplierId) throws ServiceException;

    BigDecimal findBetMoneyByUserIdByMultiplierId(int userId, int multiplierId) throws ServiceException;

    void placeBet(int userId, int multiplierId, BigDecimal betMoney) throws ServiceException;
}
