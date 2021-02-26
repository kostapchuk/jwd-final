package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.command.page.ShowMatchesPage;
import com.epam.jwd_final.web.exception.CommandException;

public enum LogoutCommand implements Command {

    INSTANCE;

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        req.invalidateSession();
        return ShowMatchesPage.INSTANCE.execute(req);
    }
}
