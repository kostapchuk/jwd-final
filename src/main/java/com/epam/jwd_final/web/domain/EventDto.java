package com.epam.jwd_final.web.domain;

import java.math.BigDecimal;

public class EventDto {

    private final MatchDto matchDto;
    private final BigDecimal firstTeamCoefficient;
    private final BigDecimal secondTeamCoefficient;
    private final BigDecimal drawCoefficient;


    public EventDto(MatchDto matchDto, BigDecimal firstTeamCoefficient, BigDecimal secondTeamCoefficient, BigDecimal drawCoefficient) {
        this.matchDto = matchDto;
        this.firstTeamCoefficient = firstTeamCoefficient;
        this.secondTeamCoefficient = secondTeamCoefficient;
        this.drawCoefficient = drawCoefficient;
    }

    public MatchDto getMatchDto() {
        return matchDto;
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
