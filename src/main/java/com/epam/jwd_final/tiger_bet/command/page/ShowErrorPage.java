package com.epam.jwd_final.tiger_bet.command.page;

import com.epam.jwd_final.tiger_bet.command.Command;
import com.epam.jwd_final.tiger_bet.command.RequestContext;
import com.epam.jwd_final.tiger_bet.command.ResponseContext;

public enum ShowErrorPage implements Command {

    INSTANCE;


    public static final ResponseContext ERROR_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/error.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) {
        return ERROR_PAGE_RESPONSE;
    }
}
