package com.epam.jwd_final.web.service.impl;

import com.epam.jwd_final.web.dao.impl.MultiplierDao;
import com.epam.jwd_final.web.domain.Multiplier;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.DaoException;
import com.epam.jwd_final.web.exception.ServiceException;

import java.math.BigDecimal;

public enum MultiplierServiceImpl {

    INSTANCE;

    private final MultiplierDao multiplierDao;

    MultiplierServiceImpl() {
        this.multiplierDao = new MultiplierDao();
    }

    public Multiplier createMultiplier(int matchId, Result result, BigDecimal coefficient) {
        return new Multiplier(matchId, result, coefficient);
    }

    public void saveMultiplier(Multiplier multiplier) throws ServiceException {
        try {
            multiplierDao.save(multiplier);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public BigDecimal findCoefficientByMatchIdByResult(int matchId, Result result) throws ServiceException {
        try {
            return multiplierDao.findOneByMatchIdByResultId(matchId, result.getId()).orElseThrow(ServiceException::new).getCoefficient();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public int findIdByMatchIdAndResult(int matchId, Result result) throws ServiceException {
        try {
            return multiplierDao.findOneByMatchIdByResultId(matchId, result.getId())
                    .orElseThrow(ServiceException::new)
                    .getId();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public void deleteById(int id) throws ServiceException {
        try {
            multiplierDao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public int findMatchIdByMultiplierId(int multiplierId) throws ServiceException {
        try {
            return multiplierDao.findOneById(multiplierId).orElseThrow(ServiceException::new).getMatchId();
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public BigDecimal findCoefficientById(int id) throws ServiceException {
        try {
            return multiplierDao.findOneById(id).orElseThrow(ServiceException::new).getCoefficient();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public Result findResultById(int id) throws ServiceException {
        try {
            return multiplierDao.findOneById(id).orElseThrow(ServiceException::new).getResult();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

}
