package com.epam.jwd_final.web.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BetDto {

    private static final String PATTERN = "dd-MM-yyyy HH:mm";
    
    private final int id;
    private final LocalDateTime start;
    private final BigDecimal placedCoefficient;
    private final String placedTeam;
    private final BigDecimal expectedWin;
    private final String opponents;


    public BetDto(int id, LocalDateTime start, String placedTeam, BigDecimal placedCoefficient, BigDecimal expectedWin, String opponents) {
        this.id = id;
        this.start = start;
        this.placedTeam = placedTeam;
        this.placedCoefficient = placedCoefficient;
        this.expectedWin = expectedWin;
        this.opponents = opponents;
    }

    public int getId() {
        return id;
    }

    public String getStart() {
        return start.format(DateTimeFormatter.ofPattern(PATTERN));
    }

    public String getPlacedTeam() {
        return placedTeam;
    }

    public BigDecimal getPlacedCoefficient() {
        return placedCoefficient;
    }

    public BigDecimal getExpectedWin() {
        return expectedWin.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public String getOpponents() {
        return opponents;
    }
}