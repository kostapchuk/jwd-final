package com.epam.jwd_final.web.connection;

import com.epam.jwd_final.web.context.ApplicationContext;
import com.epam.jwd_final.web.exception.ConnectionException;
import com.epam.jwd_final.web.property.DatabaseProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Timer;
import java.util.TimerTask;

import static com.epam.jwd_final.web.connection.ConnectionPool.EXTRA_CONNECTIONS_AMOUNT;
import static com.epam.jwd_final.web.connection.ConnectionPool.INITIAL_POOL_SIZE;
import static com.epam.jwd_final.web.connection.ConnectionPool.LOAD_FACTOR;
import static com.epam.jwd_final.web.connection.ConnectionPool.MAX_POOL_SIZE;
import static com.epam.jwd_final.web.connection.ConnectionPool.SHRINK_FACTOR;
import static com.epam.jwd_final.web.connection.ConnectionPool.TIME_OUT;

public final class ConnectionPoolManager {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPoolManager.class);

    private static final DatabaseProperty databaseProperties =
            ApplicationContext.getDatabaseProperties();

    private ConnectionPoolManager() {
    }

    static void createListener() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ConnectionPool.getInstance().getIsShrinkable().set(ConnectionPoolManager.isShrinkable());
                if (ConnectionPool.getInstance().getIsShrinkable().get()) {
                    ConnectionPoolManager.shrinkPool();
                }
            }
        }, TIME_OUT, TIME_OUT);
    }

    static Deque<ProxyConnection> createConnections(int extraConnectionsAmount) {
        Deque<ProxyConnection> newConnections = new ArrayDeque<>(extraConnectionsAmount);
        for (int i = 0; i < extraConnectionsAmount; i++) {
            try {
                Connection connection = DriverManager.getConnection(
                        databaseProperties.getUrl(),
                        databaseProperties.getUser(),
                        databaseProperties.getPassword());
                newConnections.add(new ProxyConnection(connection));
            } catch (SQLException e) {
                if (extraConnectionsAmount == INITIAL_POOL_SIZE) {
                    throw new ConnectionException(e.getMessage(), e);
                } else {
                    LOGGER.error("Cannot create a connection...");
                }
            }
        }
        return newConnections;
    }

    static boolean isExpandable() {
        return ConnectionPool.getInstance().getAllConnectionsAmount() * LOAD_FACTOR ==
                ConnectionPool.getInstance().getUnavailableConnectionsAmount()
                && ConnectionPool.getInstance().getAllConnectionsAmount() < MAX_POOL_SIZE;
    }

    static Deque<ProxyConnection> expandPool() {
        LOGGER.debug("Expanding pool...");
        return createConnections(EXTRA_CONNECTIONS_AMOUNT);
    }

    static boolean isShrinkable() {
        return ConnectionPool.getInstance().getAllConnectionsAmount() * SHRINK_FACTOR <=
                ConnectionPool.getInstance().getAvailableConnectionsAmount()
                && INITIAL_POOL_SIZE < ConnectionPool.getInstance().getAllConnectionsAmount();
    }

    static void shrinkPool() {
        LOGGER.debug("Shrinking pool...");
        int shrinkSize = (int) Math.min(EXTRA_CONNECTIONS_AMOUNT, ConnectionPool.getInstance().getAllConnectionsAmount() * SHRINK_FACTOR);
        for (int i = 0; i < shrinkSize && ConnectionPool.getInstance().getAllConnectionsAmount() > INITIAL_POOL_SIZE; i++) {
            ProxyConnection proxyConnection = ConnectionPool.getInstance().getAvailableConnections().pollFirst();
            if (proxyConnection != null) {
                proxyConnection.closeConnection();
            }
        }
    }
}