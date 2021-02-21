package com.epam.jwd_final.web.domain;

import java.math.BigDecimal;

public class BetDto {

    private final int id;
    private final BigDecimal betMoney;
    private BigDecimal expectedWin;

    public BetDto(int id, BigDecimal betMoney) {
        this.id = id;
        this.betMoney = betMoney;
    }

    public BigDecimal getExpectedWin() {
        return expectedWin;
    }

    public void setExpectedWin(BigDecimal expectedWin) {
        this.expectedWin = expectedWin;
    }

    public String getBetMoney() {
        return betMoney.toString();
    }

    public int getId() {
        return id;
    }
}