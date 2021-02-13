package com.epam.jwd_final.tiger_bet.command.page;

import com.epam.jwd_final.tiger_bet.command.Command;
import com.epam.jwd_final.tiger_bet.command.context.RequestContext;
import com.epam.jwd_final.tiger_bet.command.context.ResponseContext;

public enum ShowSignupPage implements Command {

    INSTANCE;

    public static final ResponseContext SIGNUP_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/signup.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) {
        return SIGNUP_PAGE_RESPONSE;
    }
}
