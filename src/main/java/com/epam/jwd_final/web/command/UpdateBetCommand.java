package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.command.page.ShowAllMatchesPage;
import com.epam.jwd_final.web.exception.CommandException;

public enum UpdateBetCommand implements Command {

    INSTANCE;

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        CancelBetCommand.INSTANCE.execute(req);
        return ShowAllMatchesPage.INSTANCE.execute(req);
    }
}
