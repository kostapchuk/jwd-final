package com.epam.jwd_final.web.i18n;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LocaleLanguageTag extends TagSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocaleLanguageTag.class);

    private static final String CANNOT_LOAD_DB_PROPERTIES_MSG = "Cannot load database property file";
    private static final String CANNOT_PRINT_FOR_THIS_LOCALE_MSG = "Could not print message! locale {} message {}";
    private static final String PAGE = "page_";
    private static final String PROPERTIES = ".properties";
    private static final String LANGUAGE = "language";
    private static final String EN = "en";

    private String key;

    @Override
    public int doStartTag() {

        JspWriter out = pageContext.getOut();
        String language = EN;

        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie temp : cookies) {
                if (temp.getName().equals(LANGUAGE)) {
                    language = temp.getValue();
                }
            }

            Properties properties = new Properties();

            try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(PAGE + language + PROPERTIES)) {
                properties.load(inputStream);
            } catch (IOException e) {
                throw new IllegalStateException(CANNOT_LOAD_DB_PROPERTIES_MSG);
            }
            String message = properties.getProperty(key);

            try {
                out.print(message);
            } catch (IOException e) {
                LOGGER.error(CANNOT_PRINT_FOR_THIS_LOCALE_MSG, language, message, e);
            }
        }

        return EVAL_PAGE;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
