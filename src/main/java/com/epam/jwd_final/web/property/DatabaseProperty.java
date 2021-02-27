package com.epam.jwd_final.web.property;

public class DatabaseProperty {

    static final String DB_URL_PROPERTY = "url";
    static final String DB_USER_PROPERTY = "user";
    static final String DB_PASSWORD_PROPERTY = "password";
    static final String DB_CLASSNAME_PROPERTY = "classname";

    private final String url;
    private final String user;
    private final String password;
    private final String className;

    public DatabaseProperty(String url, String user, String password, String className) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.className = className;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getClassname() {
        return className;
    }
}
