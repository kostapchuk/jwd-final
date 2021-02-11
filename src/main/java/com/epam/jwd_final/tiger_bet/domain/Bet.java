package com.epam.jwd_final.tiger_bet.domain;

import java.math.BigDecimal;

public class Bet extends AbstractEntity {

    private static final long serialVersionUID = -5658905568435861545L;

    private final User user;
    private final Multiplier multiplier;
    private final BigDecimal betMoney;
    private final Result userResultType;

    public Bet(Integer id, User user, Multiplier multiplier, BigDecimal betMoney, Result userResultType) {
        super(id);
        this.user = user;
        this.multiplier = multiplier;
        this.betMoney = betMoney;
        this.userResultType = userResultType;
    }

    public User getUser() {
        return user;
    }

    public Multiplier getMultiplier() {
        return multiplier;
    }

    public BigDecimal getBetMoney() {
        return betMoney;
    }

    public Result getUserResultType() {
        return userResultType;
    }
}
