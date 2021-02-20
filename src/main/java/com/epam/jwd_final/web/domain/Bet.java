package com.epam.jwd_final.web.domain;

import java.math.BigDecimal;

public class Bet extends AbstractEntity {

    private static final long serialVersionUID = -5658905568435861545L;

    private final int userId;
    private final int multiplierId;
    private final BigDecimal betMoney;

    public Bet(Integer id, int userId, int multiplierId, BigDecimal betMoney) {
        super(id);
        this.userId = userId;
        this.multiplierId = multiplierId;
        this.betMoney = betMoney;
    }

    public Bet(int userId, int multiplierId, BigDecimal betMoney) {
        this(null, userId, multiplierId, betMoney);
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

}
