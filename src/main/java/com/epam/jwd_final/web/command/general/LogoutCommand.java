package com.epam.jwd_final.web.command.general;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
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
