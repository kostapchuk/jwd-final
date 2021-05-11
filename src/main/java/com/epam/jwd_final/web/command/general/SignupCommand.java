package com.epam.jwd_final.web.command.general;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.command.page.ShowEventsPage;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.EmailException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.util.EmailUtils;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.util.impl.EmailUtilsImpl;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

public enum SignupCommand implements Command {

    INSTANCE;

    private static final String ERROR_MSG = "User with such name already exists";
    private static final String SUCCESS_MSG = "You've successfully joined our community!";
    private static final String GMAIL_COM = "@gmail.com";

    private final UserService userService;
    private final EmailUtils emailUtils;

    SignupCommand() {
        this.userService = UserServiceImpl.INSTANCE;
        this.emailUtils = EmailUtilsImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final String userName = req.getStringParameter(Parameter.USER_NAME.getValue());
            final String userPassword = req.getStringParameter(Parameter.USER_PASSWORD.getValue());
            if (userService.signup(userName, userPassword)) {
                req.setAttribute(Parameter.SUCCESS.getValue(), SUCCESS_MSG);
                emailUtils.sendEmailTo(userName + GMAIL_COM);
            } else {
                req.setAttribute(Parameter.ERROR.getValue(), ERROR_MSG);
            }
            return ShowEventsPage.INSTANCE.execute(req);
        } catch (ServiceException | EmailException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
