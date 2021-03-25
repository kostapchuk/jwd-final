package com.epam.jwd_final.web.command.general;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.command.ResponseContextResult;
import com.epam.jwd_final.web.domain.dto.UserDto;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.util.Optional;

public enum LoginCommand implements Command {

    INSTANCE;

    private static final String ERROR_MSG = "Invalid credentials";
    private static final String HOME_PAGE = "/controller?command=go_home_page";

    private final UserService userService;

    LoginCommand() {
        this.userService = UserServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final String userName = req.getStringParameter(Parameter.USER_NAME.getValue());
            final String userPassword = req.getStringParameter(Parameter.USER_PASSWORD.getValue());
            final Optional<UserDto> userOptional = userService.login(userName, userPassword);
            if (userOptional.isPresent()) {
                final UserDto user = userOptional.get();
                req.setSessionAttribute(Parameter.USER_ID.getValue(), user.getId());
                req.setSessionAttribute(Parameter.USER_NAME.getValue(), userName);
                req.setSessionAttribute(Parameter.USER_ROLE.getValue(), user.getRole());
                req.setSessionAttribute(Parameter.USER_BALANCE.getValue(), user.getBalance());
                return ResponseContextResult.redirect(HOME_PAGE);
            } else {
                req.setAttribute(Parameter.ERROR.getValue(), ERROR_MSG);
                return ResponseContextResult.forward(HOME_PAGE);
            }
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
