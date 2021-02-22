package com.epam.jwd_final.web.dao;

import com.epam.jwd_final.web.domain.Bet;

import java.util.List;
import java.util.Optional;

public interface BetDao {

    Optional<Bet> findOneById(int id);

    Optional<Bet> findOneByUserIdByMultiplierId(int userId, int multiplierId);

    Optional<List<Bet>> findAllByUserId(int userId);

    void save(Bet bet);

    void deleteById(int id);
}
