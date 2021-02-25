package com.epam.jwd_final.web.service.impl;

import com.epam.jwd_final.web.dao.impl.BetDaoImpl;
import com.epam.jwd_final.web.dao.UserDao;
import com.epam.jwd_final.web.dao.BetDao;
import com.epam.jwd_final.web.dao.impl.UserDaoImpl;
import com.epam.jwd_final.web.domain.Bet;
import com.epam.jwd_final.web.domain.BetDto;
import com.epam.jwd_final.web.exception.DaoException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.BetService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public enum BetServiceImpl implements BetService {

    INSTANCE;

    private final BetDao betDao;
    private final UserDao userDao;

    BetServiceImpl() {
        this.betDao = new BetDaoImpl();
        this.userDao = new UserDaoImpl();
    }

    @Override
    public Optional<List<BetDto>> findAllByUserName(String name) throws ServiceException {
        try {
            final Integer userId = userDao.findOneByName(name)
                    .orElseThrow(ServiceException::new)
                    .getId();
            return betDao.findAllByUserId(userId)
                    .map(bets -> bets.stream()
                            .map(this::convertToDto)
                            .collect(toList())
                    );
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

    private BetDto convertToDto(Bet bet) {
        return new BetDto(bet.getId(), bet.getBetMoney());
    }
}
