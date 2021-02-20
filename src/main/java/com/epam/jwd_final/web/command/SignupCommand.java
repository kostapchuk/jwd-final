package com.epam.jwd_final.tiger_bet.command;

import com.epam.jwd_final.tiger_bet.command.page.ShowErrorPage;
import com.epam.jwd_final.tiger_bet.command.page.ShowMainPage;
import com.epam.jwd_final.tiger_bet.dao.UserDao;
import com.epam.jwd_final.tiger_bet.service.impl.UserServiceImpl;

public enum SignupCommand implements Command {

    INSTANCE;

    private final UserServiceImpl userService;

    SignupCommand() {
        this.userService = new UserServiceImpl(new UserDao());
    }

    @Override
    public ResponseContext execute(RequestContext req) {
        final String name = String.valueOf(req.getParameter("userName"));
        final String password = String.valueOf(req.getParameter("userPassword"));
        ResponseContext result;
        if (userService.signup(name, password)) {
            result = ShowMainPage.INSTANCE.execute(req);
        } else {
            result = ShowErrorPage.INSTANCE.execute(req);
        }
        return result;
    }
}
