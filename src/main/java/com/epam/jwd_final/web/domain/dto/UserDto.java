package com.epam.jwd_final.web.domain.dto;

import java.math.BigDecimal;

public class UserDto {

    private final Integer id;
    private final String name;
    private final String role;
    private final BigDecimal balance;

    public UserDto(Integer id, String name, String role, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
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
