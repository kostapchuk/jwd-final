package com.epam.jwd_final.web.command.user;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.command.page.ShowEventsPage;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.BetService;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.MultiplierService;
import com.epam.jwd_final.web.service.impl.BetServiceImpl;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;
import com.epam.jwd_final.web.service.impl.MultiplierServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public enum PlaceBetCommand implements Command {

    INSTANCE;

    private static final String ERROR_MSG = "Cannot place bet. Match has already been started";
    private final MultiplierService multiplierService;
    private final BetService betService;
    private final MatchService matchService;

    PlaceBetCommand() {
        this.multiplierService = MultiplierServiceImpl.INSTANCE;
        this.betService = BetServiceImpl.INSTANCE;
        this.matchService = MatchServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final int userId = req.getIntSessionAttribute(Parameter.USER_ID.getValue());
            final String result = req.getStringParameter(Parameter.RESULT.getValue());
            final int matchId = req.getIntParameter(Parameter.MATCH_ID.getValue());
            final BigDecimal betMoney = new BigDecimal(req.getStringParameter(Parameter.BET_MONEY.getValue()));

            final int multiplierId = multiplierService.findIdByMatchIdByResult(matchId, Result.valueOf(result));

            if (LocalDateTime.now().isAfter(matchService.findById(multiplierService.findMatchIdByMultiplierId(multiplierId)).getStart())) {
                req.setAttribute(Parameter.ERROR.getValue(), ERROR_MSG);
                return ShowEventsPage.INSTANCE.execute(req);
            }
            betService.placeBet(userId, multiplierId, betMoney);
            return ShowEventsPage.INSTANCE.execute(req);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
