package com.epam.jwd_final.web.command.page;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.domain.EventDto;
import com.epam.jwd_final.web.domain.Match;
import com.epam.jwd_final.web.domain.MultiplierDto;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;
import com.epam.jwd_final.web.service.impl.MultiplierServiceImpl;

import java.math.BigDecimal;

public enum ShowMakeBetPage implements Command {

    INSTANCE;

    private final MultiplierServiceImpl multiplierService;
    private final MatchService matchService;

    ShowMakeBetPage() {
        this.multiplierService = MultiplierServiceImpl.INSTANCE;
        this.matchService = MatchServiceImpl.INSTANCE;
    }

    private static final ResponseContext MAKE_BET_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/makebet.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final int matchId = Integer.parseInt(String.valueOf(req.getParameter("matchId")));
            req.setSessionAttribute("matchId", matchId);
            final Match match = matchService.findById(matchId);
            final BigDecimal firstTeamCoef = multiplierService.findCoefficientByMatchIdByResult(matchId, Result.FIRST_TEAM);
            final BigDecimal secondTeamCoef = multiplierService.findCoefficientByMatchIdByResult(matchId, Result.SECOND_TEAM);
            final BigDecimal drawCoef = multiplierService.findCoefficientByMatchIdByResult(matchId, Result.DRAW);
            req.setSessionAttribute("event", new EventDto(matchId, match.getStart().toString(), match.getFirstTeam(), match.getSecondTeam(), firstTeamCoef, secondTeamCoef, drawCoef));
            
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
        return MAKE_BET_PAGE_RESPONSE;
    }
}
