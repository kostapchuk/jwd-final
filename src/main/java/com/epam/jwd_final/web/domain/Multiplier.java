package com.epam.jwd_final.web.domain;

import java.math.BigDecimal;

public class Multiplier extends AbstractEntity {

    private static final long serialVersionUID = -441013376426801014L;

    private final Result result;
    private final Integer matchId;
    private final BigDecimal coefficient;

    public Multiplier(Integer id, Integer matchId, Result result, BigDecimal coefficient) {
        super(id);
        this.matchId = matchId;
        this.result = result;
        this.coefficient = coefficient;
    }

    public Multiplier(Integer matchId, Result result, BigDecimal coefficient) {
        this(null, matchId, result, coefficient);
    }

    public Integer getMatchId() {
        return matchId;
    }

    public Result getResult() {
        return result;
    }

    public BigDecimal getCoefficient() {
        return coefficient;
    }
}
