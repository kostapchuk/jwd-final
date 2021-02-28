package com.epam.jwd_final.web.dao.impl;


import com.epam.jwd_final.web.connection.ConnectionPool;
import com.epam.jwd_final.web.domain.Team;
import com.epam.jwd_final.web.exception.DaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TeamDaoImplTest {

    @Mock
    private ConnectionPool connectionPool;

    @Mock
    private Connection connection;

    @Mock
    private TeamDaoImpl teamDao;

    @BeforeEach
    void setUp() {
        when(connectionPool.retrieveConnection()).thenReturn(connection);
    }


    @Test
    void FindOneById_ShouldPass() throws DaoException {
        final Team arsenal = new Team(1, "Arsenal");
        final Team chelsea = new Team(2, "Chelsea");
        final Team realMadrid = new Team(3, "Real Madrid");

        final Integer actualArsenalId = teamDao.findOneById(arsenal.getId()).orElseThrow(DaoException::new).getId();
        final Integer actualChelseaId = teamDao.findOneById(chelsea.getId()).orElseThrow(DaoException::new).getId();
        final Integer actualRealMadridId = teamDao.findOneById(realMadrid.getId()).orElseThrow(DaoException::new).getId();

        assertEquals(1, actualArsenalId);
        assertEquals(2, actualChelseaId);
        assertEquals(3, actualRealMadridId);
    }

    @Test
    void findOneByName() {
    }

    @Test
    void findAll() {
    }

}