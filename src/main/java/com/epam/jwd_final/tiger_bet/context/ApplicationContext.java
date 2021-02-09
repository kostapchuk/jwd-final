package com.epam.jwd_final.tiger_bet.context;

import com.epam.jwd_final.tiger_bet.properties.ConnectionPoolProperties;
import com.epam.jwd_final.tiger_bet.util.PropertyLoader;

public final class ApplicationContext {

    private ApplicationContext(){
    }

    private static final ConnectionPoolProperties connectionPoolProperties =
            PropertyLoader.getInstance().loadConnectionPoolProperties();

    public static ConnectionPoolProperties getConnectionPoolProperties() {
        return connectionPoolProperties;
    }
}