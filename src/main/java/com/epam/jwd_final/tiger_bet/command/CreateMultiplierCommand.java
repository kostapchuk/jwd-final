package com.epam.jwd_final.tiger_bet.command;

import com.epam.jwd_final.tiger_bet.domain.Result;

import java.math.BigDecimal;

public enum CreateMultiplierCommand implements Command {

    INSTANCE;

    private static final String FIRST_TEAM_COEFFICIENT_PARAMETER = "firstTeamCoefficient";
    private static final String SECOND_TEAM_COEFFICIENT_PARAMETER = "secondTeamCoefficient";
    private static final String DRAW_COEFFICIENT_PARAMETER = "drawCoefficient";

    @Override
    public ResponseContext execute(RequestContext req) {
        final BigDecimal firstTeamCoefficient =
                new BigDecimal(String.valueOf(req.getAttribute(FIRST_TEAM_COEFFICIENT_PARAMETER)));
        final BigDecimal secondTeamCoefficient =
                new BigDecimal(String.valueOf(req.getAttribute(SECOND_TEAM_COEFFICIENT_PARAMETER)));
        final BigDecimal drawCoefficient =
                new BigDecimal(String.valueOf(req.getAttribute(DRAW_COEFFICIENT_PARAMETER)));
//        matchService.findMatchByStartAndFirstTeamAndSecondTeam();// TODO: move to createMultiplier
//        multiplierService.createMultiplier(Result.FIRST_TEAM, firstTeamCoefficient);
//        multiplierService.createMultiplier(Result.SECOND_TEAM, secondTeamCoefficient);
//        multiplierService.createMultiplier(Result.DRAW, drawCoefficient);
        return null;
    }
}
