package com.epam.jwd_final.tiger_bet.command;

public interface Command {

    void execute();

    static Command of(String name) {
        return CommandManager.of(name);
    }
}
