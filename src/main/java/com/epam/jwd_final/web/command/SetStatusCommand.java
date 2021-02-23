package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.command.page.ShowBookmakerPage;
import com.epam.jwd_final.web.domain.Status;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.DaoException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;

public enum SetStatusCommand implements Command {

    INSTANCE;

    private static final String MATCH_ID_PARAMETER = "matchId";
    private static final String STATUS_TYPE_PARAMETER = "statusType";
    private final MatchService matchService;

    SetStatusCommand() {
        this.matchService = MatchServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final int matchId = Integer.parseInt(String.valueOf(req.getParameter(MATCH_ID_PARAMETER)));
            matchService.updateStatus(matchId,
                    Status.valueOf(String.valueOf(req.getParameter(STATUS_TYPE_PARAMETER)).toUpperCase()));
            // TODO: it supports only exact enum type in any case but with underscore
            SetResultCommand.INSTANCE.execute(req);
            return ShowBookmakerPage.INSTANCE.execute(req);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
