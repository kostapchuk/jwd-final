package com.epam.jwd_final.tiger_bet.command;

import com.epam.jwd_final.tiger_bet.command.page.ShowAllBetsPage;
import com.epam.jwd_final.tiger_bet.command.page.ShowMainPage;

public enum CommandManager {
    LOGIN(LoginCommand.INSTANCE),
    SHOW_MAIN_PAGE(ShowMainPage.INSTANCE),
    SHOW_ALL_BETS(ShowAllBetsPage.INSTANCE),
    JOIN(JoinCommand.INSTANCE),
    SIGN_UP(SignUpCommand.INSTANCE),
    SHOW_ALL_MATCHES(ShowAllMatchesCommand.INSTANCE),
    DEFAULT(ShowMainPage.INSTANCE);

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
