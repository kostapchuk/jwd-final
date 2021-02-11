package com.epam.jwd_final.tiger_bet.domain;

import java.math.BigDecimal;

public class Bet extends AbstractEntity {

    private static final long serialVersionUID = -5658905568435861545L;

    private final int userId;
    private final int multiplierId;
    private final BigDecimal betMoney;
    private final Result userResultType;

    public Bet(Integer id, int userId, int multiplierId, BigDecimal betMoney, Result userResultType) {
        super(id);
        this.userId = userId;
        this.multiplierId = multiplierId;
        this.betMoney = betMoney;
        this.userResultType = userResultType;
    }

    public int getUserId() {
        return userId;
    }

    public int getMultiplierId() {
        return multiplierId;
    }

    public BigDecimal getBetMoney() {
        return betMoney;
    }

    public Result getUserResultType() {
        return userResultType;
    }
}
