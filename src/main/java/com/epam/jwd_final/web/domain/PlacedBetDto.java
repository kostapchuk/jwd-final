package com.epam.jwd_final.web.domain;

import java.math.BigDecimal;

public class PlacedBetDto {

    private final int id;
    private final String start;
    private final String placedTeam;
    private final BigDecimal placedCoefficient;
    private final BigDecimal expectedWin;
    private final String opponents;


    public PlacedBetDto(int id, String start, String placedTeam, BigDecimal placedCoefficient, BigDecimal expectedWin, String opponents) {
        this.id = id;
        this.start = start;
        this.placedTeam = placedTeam;
        this.placedCoefficient = placedCoefficient;
        this.expectedWin = expectedWin;
        this.opponents = opponents;
    }

    public BigDecimal getExpectedWin() {
        return expectedWin.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public int getId() {
        return id;
    }

    public String getStart() {
        return start;
    }

    public String getPlacedTeam() {
        return placedTeam;
    }

    public BigDecimal getPlacedCoefficient() {
        return placedCoefficient;
    }

    public String getOpponents() {
        return opponents;
    }
}
