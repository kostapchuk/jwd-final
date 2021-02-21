package com.epam.jwd_final.web.domain;

import java.math.BigDecimal;

public class UserDto {

    private final String name;
    private final String role;
    private final BigDecimal balance;

    public UserDto(String name, String role, BigDecimal balance) {
        this.name = name;
        this.role = role;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
