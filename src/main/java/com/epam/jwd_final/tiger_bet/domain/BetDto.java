package com.epam.jwd_final.tiger_bet.domain;

import java.math.BigDecimal;

public class BetDto {

    private final BigDecimal betMoney;

    public BetDto(BigDecimal betMoney) {
        this.betMoney = betMoney;
    }

    public String getBetMoney() {
        return betMoney.toString();
    }

}