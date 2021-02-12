package com.epam.jwd_final.tiger_bet.command;

import com.epam.jwd_final.tiger_bet.command.context.RequestContext;
import com.epam.jwd_final.tiger_bet.command.context.ResponseContext;
import com.epam.jwd_final.tiger_bet.command.page.ShowErrorPage;
import com.epam.jwd_final.tiger_bet.service.impl.UserService;

public enum LoginCommand implements Command {

    INSTANCE;

    @Override
    public ResponseContext execute(RequestContext req) {
        final String email = req.getParameter("email");
        final String password = req.getParameter("password");

        return UserService.INSTANCE.logIn(email, password) ? ALL_MATCHES_RESPONSE : new ShowErrorPage();
    }
}
