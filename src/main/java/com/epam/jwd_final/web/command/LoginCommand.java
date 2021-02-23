package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.command.page.ShowMainPage;
import com.epam.jwd_final.web.dao.UserDao;
import com.epam.jwd_final.web.domain.UserDto;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.math.BigDecimal;
import java.util.Optional;

public enum LoginCommand implements Command {

    INSTANCE;

    private static final String USER_ROLE_ATTRIBUTE = "userRole";
    private static final String USER_NAME_PARAMETER = "userName";
    private static final String USER_PASSWORD_PARAMETER = "userPassword";
    private static final String USER_BALANCE_ATTRIBUTE = "userBalance";

    private final UserServiceImpl userService;

    LoginCommand() {
        this.userService = UserServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final String name = String.valueOf(req.getParameter(USER_NAME_PARAMETER));
            final String password = String.valueOf(req.getParameter(USER_PASSWORD_PARAMETER));
            final Optional<UserDto> userDto = userService.login(name, password);
            if (userDto.isPresent()) {
                req.setSessionAttribute(USER_NAME_PARAMETER, name);
                req.setSessionAttribute(USER_ROLE_ATTRIBUTE, userDto.get().getRole());
                req.setSessionAttribute(USER_BALANCE_ATTRIBUTE, userDto.get().getBalance());
                // TODO: redirect
            } else {
                req.setSessionAttribute("errorMessage", "invalid credentials");
                // TODO: forward
            }
            return ShowMainPage.INSTANCE.execute(req);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
