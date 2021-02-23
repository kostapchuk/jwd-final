package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.command.page.ShowAllBetsPage;
import com.epam.jwd_final.web.command.page.ShowAllMatchesPage;
import com.epam.jwd_final.web.command.page.ShowAllUsersPage;
import com.epam.jwd_final.web.command.page.ShowBookmakerPage;
import com.epam.jwd_final.web.command.page.ShowDepositPage;
import com.epam.jwd_final.web.command.page.ShowErrorPage;
import com.epam.jwd_final.web.command.page.ShowMainPage;
import com.epam.jwd_final.web.command.page.ShowMakeBetPage;
import com.epam.jwd_final.web.command.page.ShowWithdrawPage;

public enum CommandManager {

    LOGIN(LoginCommand.INSTANCE),
    LOGOUT(LogoutCommand.INSTANCE),
    SIGNUP(SignupCommand.INSTANCE),
    UPDATE_ROLE(UpdateRoleCommand.INSTANCE),
    ROLLBACK_ROLE(RollbackRoleCommand.INSTANCE),
    CREATE_MATCH(CreateMatchCommand.INSTANCE),
    SET_RESULT(SetResultCommand.INSTANCE),
    CANCEL_BET(CancelBetCommand.INSTANCE),
    MAKE_BET(MakeBetCommand.INSTANCE),
    UPDATE_BET(UpdateBetCommand.INSTANCE),
    DEPOSIT(DepositCommand.INSTANCE),
    WITHDRAW(WithdrawCommand.INSTANCE),
    SHOW_WITHDRAW_PAGE(ShowWithdrawPage.INSTANCE),
    SHOW_DEPOSIT_PAGE(ShowDepositPage.INSTANCE),
    SHOW_MAKE_BET_PAGE(ShowMakeBetPage.INSTANCE),
    SHOW_ALL_USERS_PAGE(ShowAllUsersPage.INSTANCE),
    SHOW_BOOKMAKER_PAGE(ShowBookmakerPage.INSTANCE),
    SHOW_ALL_BETS_PAGE(ShowAllBetsPage.INSTANCE),
    SHOW_MAIN_PAGE(ShowMainPage.INSTANCE),
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
