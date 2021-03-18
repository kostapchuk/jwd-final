package com.epam.jwd_final.web.service;

import com.epam.jwd_final.web.domain.Bet;
import com.epam.jwd_final.web.domain.dto.BetDto;
import com.epam.jwd_final.web.domain.dto.PreviousBetDto;
import com.epam.jwd_final.web.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

/**
 * BetService.
 *
 * @author Ostapchuk Kirill
 */
public interface BetService {

    /**
     * Finds all active bets for the user.
     *
     * @param id user id
     * @return List of BetDto
     */
    List<BetDto> findAllActiveByUserId(int id) throws ServiceException;

    /**
     * Finds all previous bets for the user.
     *
     * @param id user id
     * @return List of PreviousBetDto
     */
    List<PreviousBetDto> findAllPreviousByUserId(int id) throws ServiceException;

    /**
     * Creates a bet.
     *
     * @param userId user id
     * @param multiplierId multiplier id
     * @param betMoney user's bet money
     * @return Bet
     */
    Bet createBet(int userId, int multiplierId, BigDecimal betMoney);

    /**
     * Saves the bet.
     *
     * @param bet bet
     */
    void save(Bet bet) throws ServiceException;

    /**
     * Deletes bet from database.
     *
     * @param id bet id
     */
    void deleteById(int id) throws ServiceException;

    /**
     * Finds user's bet money.
     *
     * @param id bet id
     */
    BigDecimal findBetMoneyById(int id) throws ServiceException;

    /**
     * Deletes all the bet from database by multiplier id.
     *
     * @param multiplierId multiplier id
     */
    void deleteAllByMultiplierId(int multiplierId) throws ServiceException;

    /**
     * Finds all the users' ids by multiplier id.
     *
     * @param multiplierId multiplier id
     */
    List<Integer> findAllUserIdByMultiplierId(int multiplierId) throws ServiceException;

    /**
     * Finds user's bet money by user id and multiplier id.
     *
     * @param userId user it
     * @param multiplierId multiplier it
     */
    BigDecimal findBetMoneyByUserIdByMultiplierId(int userId, int multiplierId) throws ServiceException;

    /**
     * Places bet.
     *
     * @param userId user id
     * @param multiplierId multiplier id
     * @param betMoney bet money
     */
    void placeBet(int userId, int multiplierId, BigDecimal betMoney) throws ServiceException;
}
