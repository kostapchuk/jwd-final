package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.command.page.ShowAllBetsPage;
import com.epam.jwd_final.web.command.page.ShowAllMatchesPage;
import com.epam.jwd_final.web.command.page.ShowBookmakerPage;
import com.epam.jwd_final.web.command.page.ShowMainPage;
import com.epam.jwd_final.web.domain.EventDto;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;
import com.epam.jwd_final.web.service.impl.MultiplierServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public enum CreateMatchCommand implements Command {

    INSTANCE;

    private static final String FIRST_TEAM_PARAMETER = "firstTeam";
    private static final String SECOND_TEAM_PARAMETER = "secondTeam";
    private static final String START_TIME_PARAMETER = "startTime";

    private static final String FIRST_TEAM_COEFFICIENT_PARAMETER = "firstTeamCoefficient";
    private static final String SECOND_TEAM_COEFFICIENT_PARAMETER = "secondTeamCoefficient";
    private static final String DRAW_COEFFICIENT_PARAMETER = "drawCoefficient";

    private final MatchService matchService;
    private final MultiplierServiceImpl multiplierService;

    CreateMatchCommand() {
        this.multiplierService = MultiplierServiceImpl.INSTANCE;
        this.matchService = MatchServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final String start = String.valueOf(req.getParameter(START_TIME_PARAMETER));
            final String firstTeam = String.valueOf(req.getParameter(FIRST_TEAM_PARAMETER));
            final String secondTeam = String.valueOf(req.getParameter(SECOND_TEAM_PARAMETER));
            if (firstTeam.equals(secondTeam)) {
                return ShowBookmakerPage.INSTANCE.execute(req);
            }
            matchService.saveMatch(matchService.createMatch(start, firstTeam, secondTeam));

            final BigDecimal firstTeamCoefficient =
                    new BigDecimal(String.valueOf(req.getParameter(FIRST_TEAM_COEFFICIENT_PARAMETER)));
            final BigDecimal secondTeamCoefficient =
                    new BigDecimal(String.valueOf(req.getParameter(SECOND_TEAM_COEFFICIENT_PARAMETER)));
            final BigDecimal drawCoefficient =
                    new BigDecimal(String.valueOf(req.getParameter(DRAW_COEFFICIENT_PARAMETER)));

            final int matchId = matchService
                    .findMatchIdByStartAndFirstTeamAndSecondTeam(LocalDateTime.parse(start), firstTeam, secondTeam);

            multiplierService.saveMultiplier(
                    multiplierService.createMultiplier(matchId, Result.FIRST_TEAM, firstTeamCoefficient));

            multiplierService.saveMultiplier(
                    multiplierService.createMultiplier(matchId, Result.SECOND_TEAM, secondTeamCoefficient));

            multiplierService.saveMultiplier(
                    multiplierService.createMultiplier(matchId, Result.DRAW, drawCoefficient));
            return ShowAllMatchesPage.INSTANCE.execute(req);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
