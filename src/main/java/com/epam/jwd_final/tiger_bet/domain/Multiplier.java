package com.epam.jwd_final.tiger_bet.domain;

import java.math.BigDecimal;

public class Multiplier extends AbstractEntity {

    private static final long serialVersionUID = -441013376426801014L;

    private final Match match;
    private final Result result;
    private final BigDecimal coefficient;

    public Multiplier(Integer id, Match match, Result result, BigDecimal coefficient) {
        super(id);
        this.match = match;
        this.result = result;
        this.coefficient = coefficient;
    }

    public Match getMatch() {
        return match;
    }

    public Result getResult() {
        return result;
    }

    public BigDecimal getCoefficient() {
        return coefficient;
    }
}
