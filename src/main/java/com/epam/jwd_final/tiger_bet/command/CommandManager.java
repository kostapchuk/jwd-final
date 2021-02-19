package com.epam.jwd_final.tiger_bet.command;

import com.epam.jwd_final.tiger_bet.command.page.ShowAllBetsPage;
import com.epam.jwd_final.tiger_bet.command.page.ShowAllMatchesPage;
import com.epam.jwd_final.tiger_bet.command.page.ShowAllUsersPage;
import com.epam.jwd_final.tiger_bet.command.page.ShowBookmakerPage;
import com.epam.jwd_final.tiger_bet.command.page.ShowErrorPage;
import com.epam.jwd_final.tiger_bet.command.page.ShowLoginPage;
import com.epam.jwd_final.tiger_bet.command.page.ShowMainPage;
import com.epam.jwd_final.tiger_bet.command.page.ShowSignupPage;

public enum CommandManager {

    LOGIN(LoginCommand.INSTANCE),
    LOGOUT(LogoutCommand.INSTANCE),
    SIGNUP(SignupCommand.INSTANCE),
    UPDATE_ROLE(UpdateRoleCommand.INSTANCE),
    ROLLBACK_ROLE(RollbackRoleCommand.INSTANCE),
    CREATE_MATCH(CreateMatchCommand.INSTANCE),
    SET_RESULT(SetResultCommand.INSTANCE),
    SHOW_ALL_USERS_PAGE(ShowAllUsersPage.INSTANCE),
    SHOW_BOOKMAKER_PAGE(ShowBookmakerPage.INSTANCE),
    SHOW_ALL_BETS_PAGE(ShowAllBetsPage.INSTANCE),
    SHOW_MAIN_PAGE(ShowMainPage.INSTANCE),
    SHOW_LOGIN_PAGE(ShowLoginPage.INSTANCE),
    SHOW_SIGNUP_PAGE(ShowSignupPage.INSTANCE),
    SHOW_ALL_MATCHES_PAGE(ShowAllMatchesPage.INSTANCE),
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
