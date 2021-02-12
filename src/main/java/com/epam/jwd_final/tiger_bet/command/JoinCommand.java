package com.epam.jwd_final.tiger_bet.command;

public enum JoinCommand implements Command {

    INSTANCE;

    private static final ResponseContext JOIN_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/signin.jsp";
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
