package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.command.page.ShowMainPage;

public enum LogoutCommand implements Command {

    INSTANCE;

    @Override
    public ResponseContext execute(RequestContext req) {
        req.invalidateSession();
        return ShowMainPage.INSTANCE.execute(req);
    }
}
