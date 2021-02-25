package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;

public interface Command {

    ResponseContext execute(RequestContext req) throws CommandException;

    static Command of(String name) {
        return CommandManager.of(name);
    }
}
