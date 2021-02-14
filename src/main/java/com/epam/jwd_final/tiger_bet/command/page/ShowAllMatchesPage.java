package com.epam.jwd_final.tiger_bet.command.page;

import com.epam.jwd_final.tiger_bet.command.Command;
import com.epam.jwd_final.tiger_bet.command.RequestContext;
import com.epam.jwd_final.tiger_bet.command.ResponseContext;
import com.epam.jwd_final.tiger_bet.dao.MatchDao;
import com.epam.jwd_final.tiger_bet.service.impl.MatchService;

import java.util.Collections;

public enum ShowAllMatchesPage implements Command {

    INSTANCE;

    public static final String MATCHES_PARAMETER = "matches";

    private final MatchService matchService;

    ShowAllMatchesPage() {
        this.matchService = new MatchService(new MatchDao());
    }

    public static final ResponseContext ALL_MATCHES_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/allmatches.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) {
        req.setAttribute(MATCHES_PARAMETER, matchService.findAllUnfinishedMatches().orElse(Collections.emptyList()));
        return ALL_MATCHES_PAGE_RESPONSE;
    }
}
