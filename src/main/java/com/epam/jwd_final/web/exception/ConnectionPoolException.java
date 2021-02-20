package com.epam.jwd_final.web.exception;

public class ConnectionPoolException extends RuntimeException {

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }
}
