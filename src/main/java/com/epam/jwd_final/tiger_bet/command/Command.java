package com.epam.jwd_final.tiger_bet.command;

import com.epam.jwd_final.tiger_bet.command.context.RequestContext;
import com.epam.jwd_final.tiger_bet.command.context.ResponseContext;

public interface Command {

    ResponseContext execute(RequestContext req);

    static Command of(String name) {
        return CommandManager.of(name);
    }
}
