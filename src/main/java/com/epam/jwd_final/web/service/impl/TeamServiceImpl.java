package com.epam.jwd_final.web.service.impl;

import com.epam.jwd_final.web.dao.impl.TeamDao;
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
        this.teamDao = new TeamDao();
    }

    @Override
    public List<String> findAll() throws ServiceException {
        try {
            return teamDao.findAll().orElse(Collections.emptyList())
                    .stream()
                    .map(Team::getName)
                    .collect(Collectors.toList());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }
}
