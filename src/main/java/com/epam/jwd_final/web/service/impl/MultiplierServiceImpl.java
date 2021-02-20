package com.epam.jwd_final.tiger_bet.service.impl;

import com.epam.jwd_final.tiger_bet.dao.MultiplierDao;
import com.epam.jwd_final.tiger_bet.domain.Multiplier;
import com.epam.jwd_final.tiger_bet.domain.Result;

import java.math.BigDecimal;

public class MultiplierServiceImpl {

    private final MultiplierDao multiplierDao;

    public MultiplierServiceImpl(MultiplierDao multiplierDao) {
        this.multiplierDao = multiplierDao;
    }

    public Multiplier createMultiplier(int matchId, Result result, BigDecimal coefficient) {
        return multiplierDao.createMultiplier(matchId, result, coefficient);
    }

    public boolean saveMultiplier(Multiplier multiplier) {
        return multiplierDao.saveMultiplier(multiplier);
    }

    public int findIdByMatchIdAndResult(int matchId, Result result) {
        return multiplierDao.findIdByMatchIdAndResult(matchId, result.getId());
    }
}
