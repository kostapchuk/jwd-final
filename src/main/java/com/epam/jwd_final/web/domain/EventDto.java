package com.epam.jwd_final.web.domain;

import java.math.BigDecimal;
import java.util.Map;

public class EventDto {

    private final MatchDto matchDto;
    private final Map<Result, BigDecimal> coefficients;

    public EventDto(MatchDto matchDto, Map<Result, BigDecimal> coefficients) {
        this.matchDto = matchDto;
        this.coefficients = coefficients;
    }

    public MatchDto getMatchDto() {
        return matchDto;
    }

    public BigDecimal getFirstTeamCoefficient() {
        return coefficients.get(Result.FIRST_TEAM);
    }

    public BigDecimal getSecondTeamCoefficient() {
        return coefficients.get(Result.SECOND_TEAM);
    }

    public BigDecimal getDrawCoefficient() {
        return coefficients.get(Result.DRAW);
    }
}
