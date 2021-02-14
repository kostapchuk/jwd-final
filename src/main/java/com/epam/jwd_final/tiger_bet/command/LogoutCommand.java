package com.epam.jwd_final.tiger_bet.command;

import com.epam.jwd_final.tiger_bet.command.context.RequestContext;
import com.epam.jwd_final.tiger_bet.command.context.ResponseContext;
import com.epam.jwd_final.tiger_bet.command.page.ShowMainPage;

public enum LogoutCommand implements Command {

    INSTANCE;

    @Override
    public ResponseContext execute(RequestContext req) {
        req.invalidateSession();
        return ShowMainPage.INSTANCE.execute(req);
    }
}
