package com.epam.jwd_final.tiger_bet.command;

import com.epam.jwd_final.tiger_bet.command.page.ShowBookmakerPage;
import com.epam.jwd_final.tiger_bet.dao.MatchDao;
import com.epam.jwd_final.tiger_bet.dao.TeamDao;
import com.epam.jwd_final.tiger_bet.domain.Result;
import com.epam.jwd_final.tiger_bet.domain.Status;
import com.epam.jwd_final.tiger_bet.service.MatchService;
import com.epam.jwd_final.tiger_bet.service.impl.MatchServiceImpl;

public enum SetStatusCommand implements Command {

    INSTANCE;

    private static final String MATCH_ID_PARAMETER = "matchId";
    private static final String STATUS_TYPE_PARAMETER = "statusType";
    private final MatchService matchService;

    SetStatusCommand() {
        this.matchService = new MatchServiceImpl(new MatchDao(), new TeamDao());
    }

    @Override
    public ResponseContext execute(RequestContext req) {
        final int matchId = Integer.parseInt(String.valueOf(req.getParameter(MATCH_ID_PARAMETER)));
        matchService.updateStatus(matchId,
                Status.valueOf(String.valueOf(req.getParameter(STATUS_TYPE_PARAMETER)).toUpperCase()));
        // TODO: it supports only exact enum type in any case but with underscore
        return ShowBookmakerPage.INSTANCE.execute(req);
    }
}
