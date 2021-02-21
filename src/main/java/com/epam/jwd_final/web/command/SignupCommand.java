package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.command.page.ShowAllMatchesPage;
import com.epam.jwd_final.web.command.page.ShowErrorPage;
import com.epam.jwd_final.web.command.page.ShowMainPage;
import com.epam.jwd_final.web.dao.UserDao;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

public enum SignupCommand implements Command {

    INSTANCE;

    private static final String USER_NAME_PARAMETER = "userName";
    private static final String USER_PASSWORD_PARAMETER = "userPassword";

    private final UserServiceImpl userService;

    SignupCommand() {
        this.userService = new UserServiceImpl(new UserDao());
    }

    @Override
    public ResponseContext execute(RequestContext req) {
        final String name = String.valueOf(req.getParameter(USER_NAME_PARAMETER));
        final String password = String.valueOf(req.getParameter(USER_PASSWORD_PARAMETER));
        ResponseContext result;
        if (userService.signup(name, password)) {
            LoginCommand.INSTANCE.execute(req);
            result = ShowAllMatchesPage.INSTANCE.execute(req);
        } else {
            result = ShowErrorPage.INSTANCE.execute(req);
        }
        return result;
    }
}
