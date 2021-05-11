package com.epam.jwd_final.web.util;

import com.epam.jwd_final.web.exception.EmailException;

public interface EmailUtils {

    void sendEmailTo(String to) throws EmailException;
}
