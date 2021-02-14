package com.epam.jwd_final.tiger_bet.property;

public class ConnectionPoolProperty {

    static final String POOL_MAX_SIZE_PROPERTY = "maxPoolSize";
    static final String POOL_INITIAL_SIZE_PROPERTY = "initialPoolSize";
    static final String POOL_EXTRA_CONNECTIONS_AMOUNT_PROPERTY = "extraConnectionAmount";
    static final String POOL_LOAD_FACTOR_PROPERTY = "loadFactor";
    static final String POOL_SHRINK_FACTOR_PROPERTY = "shrinkFactor";
    static final String POOL_CONNECTION_TIME_OUT_PROPERTY = "connectionTimeOut";

    private final int maxConnections;
    private final int initialConnections;
    private final int extraConnections;
    private final double loadFactor;
    private final double shrinkFactor;
    private final int connectionTimeOut;

    public ConnectionPoolProperty(int maxConnections,
                                  int initialConnections,
                                  int extraConnections,
                                  double loadFactor,
                                  double shrinkFactor,
                                  int connectionTimeOut) {
        this.maxConnections = maxConnections;
        this.initialConnections = initialConnections;
        this.extraConnections = extraConnections;
        this.loadFactor = loadFactor;
        this.shrinkFactor = shrinkFactor;
        this.connectionTimeOut = connectionTimeOut;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public int getInitialConnections() {
        return initialConnections;
    }

    public int getExtraConnections() {
        return extraConnections;
    }

    public double getLoadFactor() {
        return loadFactor;
    }

    public double getShrinkFactor() {
        return shrinkFactor;
    }

    public int getConnectionTimeOut() {
        return connectionTimeOut;
    }
}
