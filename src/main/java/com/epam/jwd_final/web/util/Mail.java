package com.epam.jwd_final.web.util;

import com.epam.jwd_final.web.exception.EmailException;

public interface Mail {

    void send(String to) throws EmailException;
}
