package com.epam.jwd_final.tiger_bet.command;

import com.epam.jwd_final.tiger_bet.command.page.ShowAllMatchesPage;
import com.epam.jwd_final.tiger_bet.dao.MatchDao;
import com.epam.jwd_final.tiger_bet.dao.TeamDao;
import com.epam.jwd_final.tiger_bet.domain.Result;
import com.epam.jwd_final.tiger_bet.service.MatchService;
import com.epam.jwd_final.tiger_bet.service.impl.MatchServiceImpl;

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
        return ShowAllMatchesPage.INSTANCE.execute(req);
    }
}
