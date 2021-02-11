package com.epam.jwd_final.tiger_bet.command;

public enum ShowMainPageCommand implements Command {

    INSTANCE;

    private static final ResponseContext MAIN_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB_INF/jsp/main.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) {
        return MAIN_PAGE_RESPONSE;
    }
}
