package com.epam.jwd_final.tiger_bet.command;

import com.epam.jwd_final.tiger_bet.command.context.RequestContext;
import com.epam.jwd_final.tiger_bet.command.context.ResponseContext;
import com.epam.jwd_final.tiger_bet.command.page.ShowLoginPage;
import com.epam.jwd_final.tiger_bet.command.page.ShowMainPage;
import com.epam.jwd_final.tiger_bet.dao.UserDao;
import com.epam.jwd_final.tiger_bet.domain.UserDto;
import com.epam.jwd_final.tiger_bet.service.impl.UserService;

import java.util.Optional;

public enum LoginCommand implements Command {

    INSTANCE;

    private final UserService userService;

    public static final String USER_NAME_PARAMETER = "userName";
    public static final String USER_PASSWORD_PARAMETER = "userPassword";

    LoginCommand() {
        this.userService = new UserService(new UserDao());
    }

    @Override
    public ResponseContext execute(RequestContext req) {
        final String name = String.valueOf(req.getParameter(USER_NAME_PARAMETER));
        final String password = String.valueOf(req.getParameter(USER_PASSWORD_PARAMETER));
        final Optional<UserDto> userDto = userService.login(name, password);
        ResponseContext result;
        if (userDto.isPresent()) {
            req.setAttribute(USER_NAME_PARAMETER, name);
            result = ShowMainPage.INSTANCE.execute(req); // TODO: redirect
        } else {
            req.setAttribute("errorMessage", "invalid credentials");
            result = ShowLoginPage.INSTANCE.execute(req); // TODO: forward
        }
        return result;
    }
}
