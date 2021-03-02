package com.epam.jwd_final.web.domain.dto;

import java.math.BigDecimal;

public class PreviousBetDto {

    private final BetDto bet;

    private final boolean win;
    private final BigDecimal placedMoney;

    public PreviousBetDto(BetDto bet, boolean win, BigDecimal placedMoney) {
        this.bet = bet;
        this.win = win;
        this.placedMoney = placedMoney;
    }

    public BetDto getBet() {
        return bet;
    }

    public boolean isWin() {
        return win;
    }

    public BigDecimal getPlacedMoney() {
        return placedMoney.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

}
