package com.epam.jwd_final.web.command.page;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Page;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;

public enum ShowErrorPage implements Command {

    INSTANCE;

    private static final ResponseContext ERROR_PAGE_RESPONSE = new ResponseContext() {

        @Override
        public String getPage() {
            return Page.ERROR.getLink();
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
