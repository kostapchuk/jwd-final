package com.epam.jwd_final.tiger_bet.property;

public class DatabaseProperty {

    static final String DB_URL_PROPERTY = "url";
    static final String DB_USER_PROPERTY = "user";
    static final String DB_PASSWORD_PROPERTY = "password";

    private final String url;
    private final String user;
    private final String password;

    public DatabaseProperty(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
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
}
