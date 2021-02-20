package com.epam.jwd_final.web.command.page;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;

public enum ShowLoginPage implements Command {

    INSTANCE;

    private static final ResponseContext LOGIN_PAGE_RESPONSE = new ResponseContext() {

        @Override
        public String getPage() {
            return "/WEB-INF/jsp/login.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) {
        return LOGIN_PAGE_RESPONSE;
    }
}
