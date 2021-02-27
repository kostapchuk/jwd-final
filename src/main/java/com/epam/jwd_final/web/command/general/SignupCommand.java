package com.epam.jwd_final.web.command.general;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.command.page.ShowEventsPage;
import com.epam.jwd_final.web.command.page.ShowErrorPage;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

public enum SignupCommand implements Command {

    INSTANCE;

    private final UserService userService;

    SignupCommand() {
        this.userService = UserServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final String name = req.getStringParameter(Parameter.USER_NAME.getValue());
            final String password = req.getStringParameter(Parameter.USER_PASSWORD.getValue());
            ResponseContext result;
            if (userService.signup(name, password)) {
                result = ShowEventsPage.INSTANCE.execute(req);
            } else {
                result = ShowErrorPage.INSTANCE.execute(req);
            }
            return result;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
