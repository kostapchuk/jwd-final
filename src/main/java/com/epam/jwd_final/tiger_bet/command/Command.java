package com.epam.jwd_final.tiger_bet.command;

public interface Command {

    ResponseContext execute(RequestContext req);

    static Command of(String name) {
        return CommandManager.of(name);
    }
}
