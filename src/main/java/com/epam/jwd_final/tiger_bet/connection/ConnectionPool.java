package com.epam.jwd_final.tiger_bet.connection;


import com.epam.jwd_final.tiger_bet.context.ApplicationContext;
import com.epam.jwd_final.tiger_bet.exception.ConnectionPoolException;
import com.epam.jwd_final.tiger_bet.properties.ConnectionPoolProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private static final ConnectionPoolProperties connectionPoolProperties =
            ApplicationContext.getConnectionPoolProperties();

    static final int INITIAL_POOL_SIZE = connectionPoolProperties.getInitialConnections();
    static final int MAX_POOL_SIZE = connectionPoolProperties.getMaxConnections();
    static final int EXTRA_CONNECTIONS_AMOUNT = connectionPoolProperties.getExtraConnections();
    static final double LOAD_FACTOR = connectionPoolProperties.getLoadFactor();
    static final double SHRINK_FACTOR = connectionPoolProperties.getShrinkFactor();
    static final int TIME_OUT = connectionPoolProperties.getConnectionTimeOut();

    private static final Lock INSTANCE_LOCK = new ReentrantLock();
    private static final Lock CONNECTIONS_LOCK = new ReentrantLock();
    private static final Condition NOT_FULL = CONNECTIONS_LOCK.newCondition();
    private static final Condition NOT_EMPTY = CONNECTIONS_LOCK.newCondition();

    private static final AtomicBoolean initialized = new AtomicBoolean(false);
    private static final AtomicBoolean expandable = new AtomicBoolean(false);
    private static final AtomicBoolean shrinkable = new AtomicBoolean(false);
    private static int counter;

    private final Deque<ProxyConnection> availableConnections;
    private final Deque<ProxyConnection> unavailableConnections;

    private ConnectionPool() {
        availableConnections = new ArrayDeque<>();
        unavailableConnections = new ArrayDeque<>();
        counter = 0;
    }

    private static class ConnectionPoolHolder {
        private static final ConnectionPool instance = new ConnectionPool();
    }

    public static ConnectionPool getInstance() {
        if (!initialized.get()) {
            try {
                INSTANCE_LOCK.lock();
                if (!initialized.get()) {
                    ConnectionPoolHolder.instance.init();
                }
            } finally {
                INSTANCE_LOCK.unlock();
            }
        }
        return ConnectionPoolHolder.instance;
    }

    public Connection retrieveConnection() {
        try {
            CONNECTIONS_LOCK.lock();
            expandable.set(ConnectionPoolManager.isExpandable());
            if (expandable.get()) {
                availableConnections.addAll(ConnectionPoolManager.expandPool());
            }
            while (counter == getAllConnectionsAmount()) {
                NOT_FULL.await();
            }
            ++counter;

            ProxyConnection connection = availableConnections.pollFirst();
            unavailableConnections.add(connection);
            NOT_EMPTY.signal();
            return connection;
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(e.getMessage(), e);
        } finally {
            CONNECTIONS_LOCK.unlock();
        }
    }

    public void returnConnection(Connection connection) {
        try {
            CONNECTIONS_LOCK.lock();
            while (counter == 0) {
                NOT_EMPTY.await();
            }
            if (connection == null || !unavailableConnections.contains((ProxyConnection) connection)) {
                LOGGER.error("Cannot return connection. Connection is null or not a ProxyConnection");
                return;
            }
            boolean result = availableConnections.add((ProxyConnection) connection) &&
                    unavailableConnections.remove(connection);
            --counter;
            if (result) {
                NOT_FULL.signal();
            }
        } catch (InterruptedException e) {
            LOGGER.error("Cannot return connection. Thread was interrupted");
        } finally {
            CONNECTIONS_LOCK.unlock();
        }
    }

    private void init() {
        LOGGER.info("Initializing connection pool...");
        availableConnections.addAll(ConnectionPoolManager.createConnections(INITIAL_POOL_SIZE));
        initialized.set(true);
        ConnectionPoolManager.createListener();
    }

    public void destroy() {
        LOGGER.info("Destroying connection pool...");
        availableConnections.forEach(ProxyConnection::closeConnection);
        unavailableConnections.forEach(ProxyConnection::closeConnection);
    }

    Deque<ProxyConnection> getAvailableConnections() {
        return availableConnections;
    }

    AtomicBoolean getIsShrinkable() {
        return shrinkable;
    }

    int getAllConnectionsAmount() {
        return availableConnections.size() + unavailableConnections.size();
    }

    int getUnavailableConnectionsAmount() {
        return unavailableConnections.size();
    }

    int getAvailableConnectionsAmount() {
        return availableConnections.size();
    }
}

