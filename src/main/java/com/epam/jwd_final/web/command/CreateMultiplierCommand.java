package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.command.page.ShowMainPage;
import com.epam.jwd_final.web.dao.impl.MultiplierDao;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;
import com.epam.jwd_final.web.service.impl.MultiplierServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public enum CreateMultiplierCommand implements Command {

    INSTANCE;

    private static final String FIRST_TEAM_PARAMETER = "firstTeam";
    private static final String SECOND_TEAM_PARAMETER = "secondTeam";
    private static final String START_TIME_PARAMETER = "startTime";

    private static final String FIRST_TEAM_COEFFICIENT_PARAMETER = "firstTeamCoefficient";
    private static final String SECOND_TEAM_COEFFICIENT_PARAMETER = "secondTeamCoefficient";
    private static final String DRAW_COEFFICIENT_PARAMETER = "drawCoefficient";

    private final MultiplierServiceImpl multiplierService;
    private final MatchService matchService;

    CreateMultiplierCommand() {
        this.multiplierService = MultiplierServiceImpl.INSTANCE;
        this.matchService = MatchServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final BigDecimal firstTeamCoefficient =
                    new BigDecimal(String.valueOf(req.getParameter(FIRST_TEAM_COEFFICIENT_PARAMETER)));
            final BigDecimal secondTeamCoefficient =
                    new BigDecimal(String.valueOf(req.getParameter(SECOND_TEAM_COEFFICIENT_PARAMETER)));
            final BigDecimal drawCoefficient =
                    new BigDecimal(String.valueOf(req.getParameter(DRAW_COEFFICIENT_PARAMETER)));
            final String start = String.valueOf(req.getParameter(START_TIME_PARAMETER));
            final String firstTeam = String.valueOf(req.getParameter(FIRST_TEAM_PARAMETER));
            final String secondTeam = String.valueOf(req.getParameter(SECOND_TEAM_PARAMETER));

            final int matchId = matchService
                    .findMatchIdByStartAndFirstTeamAndSecondTeam(LocalDateTime.parse(start), firstTeam, secondTeam);

            multiplierService.saveMultiplier(
                    multiplierService.createMultiplier(matchId, Result.FIRST_TEAM, firstTeamCoefficient));

            multiplierService.saveMultiplier(
                    multiplierService.createMultiplier(matchId, Result.SECOND_TEAM, secondTeamCoefficient));

            multiplierService.saveMultiplier(
                    multiplierService.createMultiplier(matchId, Result.DRAW, drawCoefficient));

            return ShowMainPage.INSTANCE.execute(req); // TODO: write successfully create
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
