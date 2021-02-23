package com.epam.jwd_final.web.exception;

public class ServiceException extends Exception {

    public ServiceException() {
        super();
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
