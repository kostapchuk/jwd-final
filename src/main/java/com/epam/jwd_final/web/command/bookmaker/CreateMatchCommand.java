package com.epam.jwd_final.web.command.bookmaker;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.command.page.ShowErrorPage;
import com.epam.jwd_final.web.command.page.ShowMatchesPage;
import com.epam.jwd_final.web.command.page.ShowBookmakerPage;
import com.epam.jwd_final.web.domain.Match;
import com.epam.jwd_final.web.domain.Multiplier;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.MultiplierService;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;
import com.epam.jwd_final.web.service.impl.MultiplierServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public enum CreateMatchCommand implements Command {

    INSTANCE;

    private final MatchService matchService;
    private final MultiplierService multiplierService;

    CreateMatchCommand() {
        this.multiplierService = MultiplierServiceImpl.INSTANCE;
        this.matchService = MatchServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final String firstTeam = req.getStringParameter(Parameter.FIRST_TEAM.getValue());
            final String secondTeam = req.getStringParameter(Parameter.SECOND_TEAM.getValue());
            if (firstTeam.equals(secondTeam)) {
                return ShowBookmakerPage.INSTANCE.execute(req);
            }
            final String start = req.getStringParameter(Parameter.START_TIME.getValue());
            matchService.saveMatch(matchService.createMatch(start, firstTeam, secondTeam));

            final int matchId = matchService
                    .findMatchIdByStartAndFirstTeamAndSecondTeam(LocalDateTime.parse(start), firstTeam, secondTeam);

            final BigDecimal firstTeamCoefficient =
                    new BigDecimal(req.getStringParameter(Parameter.FIRST_TEAM_COEFFICIENT.getValue()));
            final BigDecimal secondTeamCoefficient =
                    new BigDecimal(req.getStringParameter(Parameter.SECOND_TEAM_COEFFICIENT.getValue()));
            final BigDecimal drawCoefficient =
                    new BigDecimal(req.getStringParameter(Parameter.DRAW_COEFFICIENT.getValue()));

            multiplierService.saveMultiplier(
                    multiplierService.createMultiplier(matchId, Result.FIRST_TEAM, firstTeamCoefficient)
            );

            multiplierService.saveMultiplier(
                    multiplierService.createMultiplier(matchId, Result.SECOND_TEAM, secondTeamCoefficient)
            );

            multiplierService.saveMultiplier(
                    multiplierService.createMultiplier(matchId, Result.DRAW, drawCoefficient)
            );

            return ShowMatchesPage.INSTANCE.execute(req);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
