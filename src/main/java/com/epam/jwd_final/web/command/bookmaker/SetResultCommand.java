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
import com.epam.jwd_final.web.observer.PayoutUserWinListener;
import com.epam.jwd_final.web.service.BetService;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.MultiplierService;
import com.epam.jwd_final.web.service.impl.BetServiceImpl;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;
import com.epam.jwd_final.web.service.impl.MultiplierServiceImpl;

public enum SetResultCommand implements Command {

    INSTANCE;

    private final MatchService matchService;
    private final MultiplierService multiplierService;
    private final BetService betService;

    SetResultCommand() {
        this.matchService = MatchServiceImpl.INSTANCE;
        this.multiplierService = MultiplierServiceImpl.INSTANCE;
        this.betService = BetServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final int matchId = req.getIntParameter(Parameter.MATCH_ID.getValue());
            final String userResult = req.getStringParameter(Parameter.RESULT_TYPE.getValue());

            final Match match = matchService.findById(matchId);

            Result newResult = parseResult(match,userResult);
            matchService.updateResult(matchId, newResult);

            new PayoutUserWinListener().update("payoutUserWin", req); // TODO: add to everyone setting userBalance

            deleteAllBets(matchId);

            return ShowBookmakerPage.INSTANCE.execute(req);
        } catch (ListenerException | ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

    Result parseResult(Match match, String userResult) {
        Result result;
        if (match.getFirstTeam().equals(userResult)) {
            result = Result.FIRST_TEAM;
        } else if (match.getSecondTeam().equals(userResult)) {
            result = Result.SECOND_TEAM;
        } else if (Result.DRAW.name().equals(userResult.toUpperCase())) {
            result = Result.DRAW;
        } else {
            result = Result.NO_RESULT;
        }
        return result;
    }

    void deleteAllBets(int matchId) throws ServiceException {
        for (Result value : Result.values()) {
            if (value.equals(Result.NO_RESULT)) {
                break;
            }
            betService.deleteAllByMultiplierId(
                    multiplierService.findIdByMatchIdAndResult(matchId, value)
            );
        }
    }
}
