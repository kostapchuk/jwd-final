package com.epam.jwd_final.web.command.bookmaker;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.command.page.ShowBookmakerPage;
import com.epam.jwd_final.web.domain.Match;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ListenerException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;

import static com.epam.jwd_final.web.controller.Controller.payout;

public enum FinishEventCommand implements Command {

    INSTANCE;

    private final MatchService matchService;

    FinishEventCommand() {
        this.matchService = MatchServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final int matchId = req.getIntParameter(Parameter.MATCH_ID.getValue());
            final String bookmakerResult = req.getStringParameter(Parameter.RESULT_TYPE.getValue());
            final Match match = matchService.findById(matchId);
            final Result newResult = parseResult(match, bookmakerResult);

            matchService.updateResult(matchId, newResult);
            payout.payoutUserWin(matchId);

            return ShowBookmakerPage.INSTANCE.execute(req);
        } catch (ListenerException | ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    Result parseResult(Match match, String bookmakerResult) {
        Result result = null;
        if (match.getFirstTeam().equals(bookmakerResult)) {
            result = Result.FIRST_TEAM;
        } else if (match.getSecondTeam().equals(bookmakerResult)) {
            result = Result.SECOND_TEAM;
        } else if (Result.DRAW.name().equals(bookmakerResult.toUpperCase())) {
            result = Result.DRAW;
        }
        return result;
    }
}
