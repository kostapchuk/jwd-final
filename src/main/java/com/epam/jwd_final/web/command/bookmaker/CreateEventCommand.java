package com.epam.jwd_final.web.command.bookmaker;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.command.page.ShowMatchesPage;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.impl.EventService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public enum CreateEventCommand implements Command {

    INSTANCE;

    private final EventService eventService;

    CreateEventCommand() {
        this.eventService = EventService.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final String firstTeam = req.getStringParameter(Parameter.FIRST_TEAM.getValue());
            final String secondTeam = req.getStringParameter(Parameter.SECOND_TEAM.getValue());
            final LocalDateTime start = LocalDateTime.parse(req.getStringParameter(Parameter.START_TIME.getValue()));

            Map<Result, BigDecimal> coefficients = createCoefficientsMap(
                    new BigDecimal(req.getStringParameter(Parameter.FIRST_TEAM_COEFFICIENT.getValue())),
                    new BigDecimal(req.getStringParameter(Parameter.SECOND_TEAM_COEFFICIENT.getValue())),
                    new BigDecimal(req.getStringParameter(Parameter.DRAW_COEFFICIENT.getValue()))
            );

            eventService.createEvent(start, firstTeam, secondTeam, coefficients);
            return ShowMatchesPage.INSTANCE.execute(req);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    Map<Result, BigDecimal> createCoefficientsMap(BigDecimal firstTeamCoefficient,
                                                  BigDecimal secondTeamCoefficient,
                                                  BigDecimal drawCoefficient) {
        Map<Result, BigDecimal> coefficients = new HashMap<>();

        coefficients.put(Result.FIRST_TEAM, firstTeamCoefficient);
        coefficients.put(Result.SECOND_TEAM, secondTeamCoefficient);
        coefficients.put(Result.DRAW, drawCoefficient);

        return coefficients;
    }
}
