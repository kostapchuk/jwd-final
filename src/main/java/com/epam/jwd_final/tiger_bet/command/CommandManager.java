package com.epam.jwd_final.tiger_bet.command;

import com.epam.jwd_final.tiger_bet.command.page.ShowErrorPage;
import com.epam.jwd_final.tiger_bet.command.page.ShowLoginPage;
import com.epam.jwd_final.tiger_bet.command.page.ShowMainPage;
import com.epam.jwd_final.tiger_bet.command.page.ShowSignupPage;

public enum CommandManager {
    LOGIN(LoginCommand.INSTANCE),
    LOGOUT(LogoutCommand.INSTANCE),
    SIGNUP(SignupCommand.INSTANCE),
    SHOW_MAIN_PAGE(ShowMainPage.INSTANCE),
    SHOW_LOGIN_PAGE(ShowLoginPage.INSTANCE),
    SHOW_SIGNUP_PAGE(ShowSignupPage.INSTANCE),
    SHOW_ERROR_PAGE(ShowErrorPage.INSTANCE),
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
