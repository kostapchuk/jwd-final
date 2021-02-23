package com.epam.jwd_final.web.domain;

import java.math.BigDecimal;

public class EventDto {

    private final Integer matchId;
    private final String start;
    private final String firstTeam;
    private final String secondTeam;
    private final BigDecimal firstTeamCoefficient;
    private final BigDecimal secondTeamCoefficient;
    private final BigDecimal drawCoefficient;


    public EventDto(Integer matchId, String start, String firstTeam, String secondTeam,
                    BigDecimal firstTeamCoefficient, BigDecimal secondTeamCoefficient, BigDecimal drawCoefficient) {
        this.matchId = matchId;
        this.start = start;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.firstTeamCoefficient = firstTeamCoefficient;
        this.secondTeamCoefficient = secondTeamCoefficient;
        this.drawCoefficient = drawCoefficient;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public String getStart() {
        return start;
    }

    public String getFirstTeam() {
        return firstTeam;
    }

    public String getSecondTeam() {
        return secondTeam;
    }

    public BigDecimal getFirstTeamCoefficient() {
        return firstTeamCoefficient;
    }

    public BigDecimal getSecondTeamCoefficient() {
        return secondTeamCoefficient;
    }

    public BigDecimal getDrawCoefficient() {
        return drawCoefficient;
    }
}
