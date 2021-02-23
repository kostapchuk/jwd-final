package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.command.page.ShowBookmakerPage;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ListenerException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.observer.PayoutUserWinListener;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;

public enum SetResultCommand implements Command {

    INSTANCE;

    private static final String MATCH_ID_PARAMETER = "matchId";
    private static final String RESULT_TYPE_PARAMETER = "resultType";
    private final MatchService matchService;

    SetResultCommand() {
        this.matchService = MatchServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final int matchId = Integer.parseInt(String.valueOf(req.getParameter(MATCH_ID_PARAMETER)));
            final Result newResult = Result.valueOf(String.valueOf(req.getParameter(RESULT_TYPE_PARAMETER)).toUpperCase());
            matchService.updateResult(matchId, newResult);
            new PayoutUserWinListener().update("payoutUserWin", matchId);
            return ShowBookmakerPage.INSTANCE.execute(req);
        } catch (ListenerException | ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
