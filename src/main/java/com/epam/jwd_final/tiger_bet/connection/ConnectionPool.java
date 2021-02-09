package com.epam.jwd_final.tiger_bet.connection;


import com.epam.jwd_final.tiger_bet.context.ApplicationContext;
import com.epam.jwd_final.tiger_bet.properties.ConnectionPoolProperties;

import java.sql.Connection;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class ConnectionPool {

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
        INSTANCE_LOCK.lock();
        if (!initialized.get()) {
            try {
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
        CONNECTIONS_LOCK.lock();
        try {
            expandable.set(ConnectionPoolManager.INSTANCE.isExpandable());
            if (expandable.get()) {
                availableConnections.addAll(ConnectionPoolManager.INSTANCE.expandPool());
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
            // TODO throw new ConnectionPoolException(e);
            throw new IllegalArgumentException();
        } finally {
            CONNECTIONS_LOCK.unlock();
        }
    }

    public void returnConnection(Connection connection) {
        CONNECTIONS_LOCK.lock();
        try {
            while (counter == 0) {
                NOT_EMPTY.await();
            }
            if (connection == null || !unavailableConnections.contains((ProxyConnection) connection)) {
                // TODO throw new ConnectionPoolException(e); // log its better
                throw new IllegalArgumentException();
            }
            boolean result = availableConnections.add((ProxyConnection) connection) &&
                    unavailableConnections.remove(connection);
            if (result) {
                NOT_FULL.signal();
            }
        } catch (InterruptedException e) {
            // TODO throw new ConnectionPoolException(e);
            e.printStackTrace();
        } finally {
            CONNECTIONS_LOCK.unlock();
        }
    }

    private void init() {
        availableConnections.addAll(ConnectionPoolManager.INSTANCE.createConnections(INITIAL_POOL_SIZE));
        initialized.set(true);
        ConnectionPoolManager.INSTANCE.createListener();
    }

    public void destroy() {
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

