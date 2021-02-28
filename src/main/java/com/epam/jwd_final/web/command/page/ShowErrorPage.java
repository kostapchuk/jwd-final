package com.epam.jwd_final.web.command.page;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Page;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.command.ResponseContextResult;

public enum ShowErrorPage implements Command {

    INSTANCE;

    @Override
    public ResponseContext execute(RequestContext req) {
        return ResponseContextResult.forward(Page.ERROR.getLink());
    }
}
