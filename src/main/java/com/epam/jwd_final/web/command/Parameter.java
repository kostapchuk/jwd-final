package com.epam.jwd_final.web.command;

public enum Parameter {
    NAME("userName"),
    BETS("bets"),
    PLACED_BETS("placedBets"),
    BALANCE("userBalance");

    private String parameter;

    Parameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
