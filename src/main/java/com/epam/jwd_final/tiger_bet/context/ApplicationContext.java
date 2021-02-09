package com.epam.jwd_final.tiger_bet.context;

import com.epam.jwd_final.tiger_bet.properties.ConnectionPoolProperties;
import com.epam.jwd_final.tiger_bet.properties.DatabaseProperties;
import com.epam.jwd_final.tiger_bet.util.PropertyLoader;

public final class ApplicationContext {

    private static final ConnectionPoolProperties connectionPoolProperties =
            PropertyLoader.getInstance().loadConnectionPoolProperties();

    private static final DatabaseProperties databaseProperties =
            PropertyLoader.getInstance().loadDatabaseProperties();

    private ApplicationContext(){
    }

    public static ConnectionPoolProperties getConnectionPoolProperties() {
        return connectionPoolProperties;
    }

    public static DatabaseProperties getDatabaseProperties() {
        return databaseProperties;
    }
}