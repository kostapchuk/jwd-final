package com.epam.jwd_final.web.command.page;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.dao.MatchDao;
import com.epam.jwd_final.web.dao.TeamDao;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;

import java.util.Collections;

public enum ShowBookmakerPage implements Command {

    INSTANCE;

    public static final String MATCHES_PARAMETER = "matches";

    private final MatchServiceImpl matchService;

    ShowBookmakerPage() {
        this.matchService = MatchServiceImpl.INSTANCE;
    }

    public static final ResponseContext BOOKMAKER_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/bookmaker.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) {
        req.setAttribute(MATCHES_PARAMETER, matchService.findAllUnfinishedMatches().orElse(Collections.emptyList()));
        return BOOKMAKER_PAGE_RESPONSE;
    }
}
