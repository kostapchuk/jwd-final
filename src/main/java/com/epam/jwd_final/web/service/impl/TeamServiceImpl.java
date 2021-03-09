package com.epam.jwd_final.web.service.impl;

import com.epam.jwd_final.web.dao.TeamDao;
import com.epam.jwd_final.web.dao.impl.TeamDaoImpl;
import com.epam.jwd_final.web.domain.Team;
import com.epam.jwd_final.web.exception.DaoException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.TeamService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public enum TeamServiceImpl implements TeamService {

    INSTANCE;

    private final TeamDao teamDao;

    TeamServiceImpl() {
        this.teamDao = new TeamDaoImpl();
    }

    @Override
    public List<String> findAll() throws ServiceException {
        try {
            return teamDao.findAll()
                    .stream()
                    .map(Team::getName)
                    .collect(Collectors.toList());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public int findIdByName(String name) throws ServiceException {
        try {
            return teamDao.findOneByName(name)
                    .orElseThrow(ServiceException::new)
                    .getId();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public String findNameById(int id) throws ServiceException {
        try {
            return teamDao.findOneById(id)
                    .orElseThrow(ServiceException::new)
                    .getName();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }
}
