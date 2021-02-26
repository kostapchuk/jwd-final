package com.epam.jwd_final.web.command.general;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.command.page.ShowMatchesPage;
import com.epam.jwd_final.web.command.page.ShowErrorPage;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

public enum SignupCommand implements Command {

    INSTANCE;

    private final UserServiceImpl userService;

    SignupCommand() {
        this.userService = UserServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final String name = String.valueOf(req.getParameter(Parameter.USER_NAME.getParameter()));
            final String password = String.valueOf(req.getParameter(Parameter.USER_PASSWORD.getParameter()));
            ResponseContext result;
            if (userService.signup(name, password)) {
                result = ShowMatchesPage.INSTANCE.execute(req);
            } else {
                result = ShowErrorPage.INSTANCE.execute(req);
            }
            return result;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
