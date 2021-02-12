package com.epam.jwd_final.tiger_bet.command;

public enum CommandManager {
    LOGIN(LoginCommand.INSTANCE),
    SHOW_MAIN_PAGE(ShowMainPageCommand.INSTANCE),
    SHOW_ALL_BETS(ShowAllBetsCommand.INSTANCE),
    SIGN_IN_COMMAND(SignInCommand.INSTANCE),
    DEFAULT(ShowMainPageCommand.INSTANCE);

    private final Command command;

    CommandManager(Command command) {
        this.command = command;
    }

    static Command of(String name) {
        for (CommandManager action : values()) {
            if (action.name().equalsIgnoreCase(name)) {
                return action.command;
            }
        }
        return DEFAULT.command;
    }
}
