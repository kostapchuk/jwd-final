package com.epam.jwd_final.web.dao.impl;


import com.epam.jwd_final.web.connection.ConnectionPool;
import com.epam.jwd_final.web.exception.DaoException;
import com.epam.jwd_final.web.property.DatabaseProperty;
import com.epam.jwd_final.web.property.PropertyLoader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class TeamDaoImplTest {

    @Mock
    private TeamDaoImpl teamDao;

    @Mock
    private ConnectionPool connectionPool;

    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        DatabaseProperty databaseProperty = PropertyLoader.getInstance().loadDatabaseProperties();
        this.connection = DriverManager.getConnection(
                databaseProperty.getUrl(),
                databaseProperty.getUser(),
                databaseProperty.getPassword()
        );

//        connection.createStatement().execute("insert  into team values (15, 'Oratek')");
//        connection.createStatement().execute("insert  into team values (16, 'Pipol')");
//        connection.createStatement().execute("insert  into team values (17, 'Qwasd')");
//
//        when(connectionPool.retrieveConnection()).thenReturn(connection);
    }

    @AfterEach
    public void cleanUp() throws SQLException {
        connection.createStatement().execute("delete from team where id = 15");
        connection.createStatement().execute("delete from team where id = 16");
        connection.createStatement().execute("delete from team where id = 17");
//        ConnectionPool.getInstance().returnConnection(connection);
    }


    @Test
    void FindOneById_ShouldPass() throws DaoException {

//        final String actualName = teamDao.findOneById(15).orElseThrow(DaoException::new).getName();
        final String expected = "Oratek";

//        assertEquals(expected, actualName);
    }

    @Test
    void findOneByName() {
    }

    @Test
    void findAll() {
    }
}