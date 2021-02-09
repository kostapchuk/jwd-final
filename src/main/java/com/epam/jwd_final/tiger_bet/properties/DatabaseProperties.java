package com.epam.jwd_final.tiger_bet.properties;

public class DatabaseProperties {

    private final String url;
    private final String user;
    private final String password;

    public DatabaseProperties(String url, String user, String password) {
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
