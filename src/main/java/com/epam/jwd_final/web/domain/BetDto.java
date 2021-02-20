package com.epam.jwd_final.tiger_bet.domain;

import java.math.BigDecimal;

public class BetDto {

    private final int id;
    private final BigDecimal betMoney;

    public BetDto(int id, BigDecimal betMoney) {
        this.id = id;
        this.betMoney = betMoney;
    }

    public String getBetMoney() {
        return betMoney.toString();
    }

    public int getId() {
        return id;
    }
}