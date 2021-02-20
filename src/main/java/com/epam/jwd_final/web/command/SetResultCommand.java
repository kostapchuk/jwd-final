package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.command.page.ShowBookmakerPage;
import com.epam.jwd_final.web.dao.MatchDao;
import com.epam.jwd_final.web.dao.TeamDao;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;

public enum SetResultCommand implements Command {

    INSTANCE;

    private static final String MATCH_ID_PARAMETER = "matchId";
    private static final String RESULT_TYPE_PARAMETER = "resultType";
    private final MatchService matchService;

    SetResultCommand() {
        this.matchService = new MatchServiceImpl(new MatchDao(), new TeamDao());
    }

    @Override
    public ResponseContext execute(RequestContext req) {
        final int matchId = Integer.parseInt(String.valueOf(req.getParameter(MATCH_ID_PARAMETER)));
        matchService.updateResult(matchId,
                Result.valueOf(String.valueOf(req.getParameter(RESULT_TYPE_PARAMETER)).toUpperCase()));
        // TODO: it supports only exact enum type in any case but with underscore
        return ShowBookmakerPage.INSTANCE.execute(req);
    }
}
