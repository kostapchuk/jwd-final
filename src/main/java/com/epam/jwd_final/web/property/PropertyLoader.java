package com.epam.jwd_final.web.property;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.epam.jwd_final.web.property.ConnectionPoolProperty.POOL_CONNECTION_TIME_OUT_PROPERTY;
import static com.epam.jwd_final.web.property.ConnectionPoolProperty.POOL_EXTRA_CONNECTIONS_AMOUNT_PROPERTY;
import static com.epam.jwd_final.web.property.ConnectionPoolProperty.POOL_INITIAL_SIZE_PROPERTY;
import static com.epam.jwd_final.web.property.ConnectionPoolProperty.POOL_LOAD_FACTOR_PROPERTY;
import static com.epam.jwd_final.web.property.ConnectionPoolProperty.POOL_MAX_SIZE_PROPERTY;
import static com.epam.jwd_final.web.property.ConnectionPoolProperty.POOL_SHRINK_FACTOR_PROPERTY;

import static com.epam.jwd_final.web.property.DatabaseProperty.DB_CLASSNAME_PROPERTY;
import static com.epam.jwd_final.web.property.DatabaseProperty.DB_PASSWORD_PROPERTY;
import static com.epam.jwd_final.web.property.DatabaseProperty.DB_URL_PROPERTY;
import static com.epam.jwd_final.web.property.DatabaseProperty.DB_USER_PROPERTY;

import static com.epam.jwd_final.web.property.SmtpProperty.SMTP_AUTH_PROPERTY;
import static com.epam.jwd_final.web.property.SmtpProperty.SMTP_FROM_PROPERTY;
import static com.epam.jwd_final.web.property.SmtpProperty.SMTP_HOST_PROPERTY;
import static com.epam.jwd_final.web.property.SmtpProperty.SMTP_PASSWORD_PROPERTY;
import static com.epam.jwd_final.web.property.SmtpProperty.SMTP_PORT_PROPERTY;
import static com.epam.jwd_final.web.property.SmtpProperty.SMTP_STARTTLS_PROPERTY;
import static com.epam.jwd_final.web.property.SmtpProperty.SMTP_USERNAME_PROPERTY;

public final class PropertyLoader {

    private static final Properties properties = new Properties();

    private static final String DATABASE_PROPERTY_FILE_NAME = "database.properties";
    private static final String CONNECTION_POOL_PROPERTY_FILE_NAME = "pool.properties";
    private static final String SMTP_PROPERTY_FILE_NAME = "smtp.properties";

    private static final String DB_ERROR_MSG = "Cannot load database property file";
    private static final String POOL_ERROR_MSG = "Cannot load connection pool property file";
    private static final String SMTP_ERROR_MSG = "Cannot load smtp property file";

    private PropertyLoader() {
    }

    public DatabaseProperty loadDatabaseProperties() {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(DATABASE_PROPERTY_FILE_NAME)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(DB_ERROR_MSG); //TODO
        }
        return new DatabaseProperty(
                properties.getProperty(DB_URL_PROPERTY),
                properties.getProperty(DB_USER_PROPERTY),
                properties.getProperty(DB_PASSWORD_PROPERTY),
                properties.getProperty(DB_CLASSNAME_PROPERTY)
        );
    }

    public ConnectionPoolProperty loadConnectionPoolProperties() {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(CONNECTION_POOL_PROPERTY_FILE_NAME)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(POOL_ERROR_MSG); //TODO
        }
        return new ConnectionPoolProperty(
                Integer.parseInt(properties.getProperty(POOL_MAX_SIZE_PROPERTY)),
                Integer.parseInt(properties.getProperty(POOL_INITIAL_SIZE_PROPERTY)),
                Integer.parseInt(properties.getProperty(POOL_EXTRA_CONNECTIONS_AMOUNT_PROPERTY)),
                Double.parseDouble(properties.getProperty(POOL_LOAD_FACTOR_PROPERTY)),
                Double.parseDouble(properties.getProperty(POOL_SHRINK_FACTOR_PROPERTY)),
                Integer.parseInt(properties.getProperty(POOL_CONNECTION_TIME_OUT_PROPERTY))
        );
    }

    public SmtpProperty loadSmtpProperties() {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(SMTP_PROPERTY_FILE_NAME)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(SMTP_ERROR_MSG); //TODO
        }
        return new SmtpProperty(
                properties.getProperty(SMTP_AUTH_PROPERTY),
                properties.getProperty(SMTP_STARTTLS_PROPERTY),
                properties.getProperty(SMTP_HOST_PROPERTY),
                properties.getProperty(SMTP_PORT_PROPERTY),
                properties.getProperty(SMTP_FROM_PROPERTY),
                properties.getProperty(SMTP_USERNAME_PROPERTY),
                properties.getProperty(SMTP_PASSWORD_PROPERTY)
        );
    }

    private static class PropertyLoaderSingletonHolder {
        private final static PropertyLoader instance = new PropertyLoader();
    }

    public static PropertyLoader getInstance() {
        return PropertyLoaderSingletonHolder.instance;
    }
}
