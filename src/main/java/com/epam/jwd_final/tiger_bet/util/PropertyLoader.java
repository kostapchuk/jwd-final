package com.epam.jwd_final.tiger_bet.util;

import com.epam.jwd_final.tiger_bet.properties.ConnectionPoolProperties;
import com.epam.jwd_final.tiger_bet.properties.DatabaseProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    private static final Properties properties = new Properties();

    private PropertyLoader() {
    }

    public DatabaseProperties loadDatabaseProperties() {
        final String propertiesFileName = "src/main/resources/database.properties"; // TODO

        try (InputStream inputStream = new FileInputStream(propertiesFileName)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new DatabaseProperties(
                properties.getProperty("url"),
                properties.getProperty("user"),
                properties.getProperty("password")
        );
    }

    public ConnectionPoolProperties loadConnectionPoolProperties() {
        final String propertiesFileName = "src/main/resources/connection_pool.properties"; // TODO

        try (InputStream inputStream = new FileInputStream(propertiesFileName)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ConnectionPoolProperties(
                Integer.parseInt(properties.getProperty("maxPoolSize")),
                Integer.parseInt(properties.getProperty("initialPoolSize")),
                Integer.parseInt(properties.getProperty("extraConnectionAmount")),
                Double.parseDouble(properties.getProperty("loadFactor")),
                Double.parseDouble(properties.getProperty("shrinkFactor")),
                Integer.parseInt(properties.getProperty("connectionTimeOut"))
        );
    }

    private static class PropertyLoaderSingletonHolder {
        private final static PropertyLoader instance = new PropertyLoader();
    }

    public static PropertyLoader getInstance() {
        return PropertyLoaderSingletonHolder.instance;
    }
}
