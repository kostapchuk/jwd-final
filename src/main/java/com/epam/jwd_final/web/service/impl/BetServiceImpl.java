package com.epam.jwd_final.web.service.impl;

import com.epam.jwd_final.web.dao.impl.BetDao;
import com.epam.jwd_final.web.dao.UserDao;
import com.epam.jwd_final.web.dao.impl.UserDaoImpl;
import com.epam.jwd_final.web.domain.Bet;
import com.epam.jwd_final.web.domain.BetDto;
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
        this.betDao = new BetDao();
        this.userDao = new UserDaoImpl();
    }

    @Override
    public Optional<List<BetDto>> findAllByUserName(String name) {
        final Integer userId = userDao.findOneByName(name)
                .orElseThrow(IllegalArgumentException::new)
                .getId();
        return betDao.findAllByUserId(userId)
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
    public void save(Bet bet) {
        betDao.save(bet);
    }

    @Override
    public void deleteById(int id) {
        betDao.deleteById(id);
    }

    @Override
    public int findMultiplierIdById(int id) {
        return betDao.findOneById(id)
                .orElseThrow(IllegalArgumentException::new)
                .getMultiplierId();
    }

    @Override
    public BigDecimal findBetMoneyById(int id) {
        return betDao.findOneById(id)
                .orElseThrow(IllegalArgumentException::new)
                .getBetMoney();
    }

    private BetDto convertToDto(Bet bet) {
        return new BetDto(bet.getId(), bet.getBetMoney());
    }
}
