package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.command.page.ShowMatchesPage;
import com.epam.jwd_final.web.domain.UserDto;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.util.Optional;

public enum LoginCommand implements Command {

    INSTANCE;

    private final UserServiceImpl userService;

    LoginCommand() {
        this.userService = UserServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final String name = String.valueOf(req.getParameter(Parameter.USER_NAME.getParameter()));
            final String password = String.valueOf(req.getParameter(Parameter.USER_PASSWORD.getParameter()));
            final Optional<UserDto> userDto = userService.login(name, password);
            if (userDto.isPresent()) {
                req.setSessionAttribute(Parameter.USER_NAME.getParameter(), name);
                req.setSessionAttribute(Parameter.USER_ROLE.getParameter(), userDto.get().getRole());
                req.setSessionAttribute(Parameter.USER_BALANCE.getParameter(), userDto.get().getBalance());
                // TODO: redirect
            } else {
                req.setSessionAttribute("errorMessage", "invalid credentials");
                // TODO: forward
            }
            return ShowMatchesPage.INSTANCE.execute(req);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
