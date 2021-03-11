package com.epam.jwd_final.web.i18n;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LocaleLanguageTag extends TagSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocaleLanguageTag.class);

    private String key;

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();
        String language = "en";

        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie temp : cookies) {
                if (temp.getName().equals("language")) {
                    language = temp.getValue();
                }
            }

            Properties properties = new Properties();

            try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("page_" + language + ".properties")) {
                properties.load(inputStream);
            } catch (IOException e) {
                throw new IllegalStateException("Cannot load database property file");
            }
            String message = properties.getProperty(key);

            try {
                out.print(message);
            } catch (IOException e) {
                LOGGER.error("Could not print message! locale {} message {}", language, message, e);
            }
        }

        return EVAL_PAGE;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
