package com.epam.jwd_final.web.service.impl;

import com.epam.jwd_final.web.dao.impl.TeamDao;
import com.epam.jwd_final.web.exception.DaoException;
import com.epam.jwd_final.web.exception.ServiceException;

import java.util.Collections;
import java.util.List;

public enum TeamService {

    INSTANCE;

    private final TeamDao teamDao;

    TeamService() {
        this.teamDao = new TeamDao();
    }

    public List<String> findAll() throws ServiceException {
        try {
            return teamDao.findAll().orElse(Collections.emptyList());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

}
