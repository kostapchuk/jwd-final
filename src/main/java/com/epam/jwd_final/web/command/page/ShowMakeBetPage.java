package com.epam.jwd_final.web.command.page;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;

public enum ShowMakeBetPage implements Command {

    INSTANCE;

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
    public ResponseContext execute(RequestContext req) {
        req.setSessionAttribute("matchId", req.getParameter("matchId"));
        return MAKE_BET_PAGE_RESPONSE;
    }
}
