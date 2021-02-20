package com.epam.jwd_final.web.domain;

public class UserDto {

    private final String name;
    private final String role;

    public UserDto(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
