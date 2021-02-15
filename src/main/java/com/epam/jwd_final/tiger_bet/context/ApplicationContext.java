package com.epam.jwd_final.tiger_bet.context;

import com.epam.jwd_final.tiger_bet.property.ConnectionPoolProperty;
import com.epam.jwd_final.tiger_bet.property.DatabaseProperty;
import com.epam.jwd_final.tiger_bet.property.PropertyLoader;

public final class ApplicationContext {

    private static final ConnectionPoolProperty connectionPoolProperties =
            PropertyLoader.getInstance().loadConnectionPoolProperties();

    private static final DatabaseProperty databaseProperties =
            PropertyLoader.getInstance().loadDatabaseProperties();

    private ApplicationContext() {
    }

    public static ConnectionPoolProperty getConnectionPoolProperties() {
        return connectionPoolProperties;
    }

    public static DatabaseProperty getDatabaseProperties() {
        return databaseProperties;
    }
}