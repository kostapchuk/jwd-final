package com.epam.jwd_final.web.domain;

import java.math.BigDecimal;

public class MultiplierDto {

    private int matchId;
    private BigDecimal firstTeamCoefficient;
    private BigDecimal secondTeamCoefficient;
    private BigDecimal drawCoefficient;

    public MultiplierDto(int matchId, BigDecimal firstTeamCoefficient, BigDecimal secondTeamCoefficient, BigDecimal drawCoefficient) {
        this.matchId = matchId;
        this.firstTeamCoefficient = firstTeamCoefficient;
        this.secondTeamCoefficient = secondTeamCoefficient;
        this.drawCoefficient = drawCoefficient;
    }

    public int getMatchId() {
        return matchId;
    }

    public String getFirstTeamCoefficient() {
        return firstTeamCoefficient.toPlainString();
    }

    public String getSecondTeamCoefficient() {
        return secondTeamCoefficient.toPlainString();
    }

    public String getDrawCoefficient() {
        return drawCoefficient.toPlainString();
    }
}
