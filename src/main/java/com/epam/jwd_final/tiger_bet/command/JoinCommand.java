package com.epam.jwd_final.tiger_bet.command;

import com.epam.jwd_final.tiger_bet.command.context.RequestContext;
import com.epam.jwd_final.tiger_bet.command.context.ResponseContext;

public enum JoinCommand implements Command {

    INSTANCE;

    private static final ResponseContext JOIN_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/join.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) {
        return JOIN_RESPONSE;
    }
}
