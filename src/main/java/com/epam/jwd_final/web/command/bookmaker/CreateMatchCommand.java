package com.epam.jwd_final.web.command.bookmaker;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.command.page.ShowMatchesPage;
import com.epam.jwd_final.web.command.page.ShowBookmakerPage;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.MultiplierService;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;
import com.epam.jwd_final.web.service.impl.MultiplierServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
            final LocalDateTime start = LocalDateTime.parse(req.getStringParameter(Parameter.START_TIME.getValue()));

            matchService.saveMatch(matchService.createMatch(start, firstTeam, secondTeam));

            Map<Result, BigDecimal> coefficients = new HashMap<>();
            coefficients.put(
                    Result.FIRST_TEAM,
                    new BigDecimal(req.getStringParameter(Parameter.FIRST_TEAM_COEFFICIENT.getValue()))
            );
            coefficients.put(
                    Result.SECOND_TEAM,
                    new BigDecimal(req.getStringParameter(Parameter.SECOND_TEAM_COEFFICIENT.getValue()))
            );
            coefficients.put(
                    Result.DRAW,
                    new BigDecimal(req.getStringParameter(Parameter.DRAW_COEFFICIENT.getValue()))
            );

            createMultipliers(
                    coefficients,
                    matchService.findMatchIdByStartByFirstTeamBySecondTeam(start, firstTeam, secondTeam)
            );
            return ShowMatchesPage.INSTANCE.execute(req);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    void createMultipliers(Map<Result, BigDecimal> coefficients, int matchId) throws ServiceException {
        for (Result result : coefficients.keySet()) {
            multiplierService.saveMultiplier(
                    multiplierService.createMultiplier(matchId, result, coefficients.get(result)));
        }
    }
}
