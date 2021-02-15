package com.epam.jwd_final.tiger_bet.command;

import com.epam.jwd_final.tiger_bet.command.page.ShowLoginPage;
import com.epam.jwd_final.tiger_bet.command.page.ShowMainPage;
import com.epam.jwd_final.tiger_bet.dao.UserDao;
import com.epam.jwd_final.tiger_bet.domain.UserDto;
import com.epam.jwd_final.tiger_bet.service.impl.UserServiceImpl;

import java.util.Optional;

public enum LoginCommand implements Command {

    INSTANCE;

    public static final String USER_ROLE_ATTRIBUTE = "userRole";
    public static final String USER_NAME_PARAMETER = "userName";
    public static final String USER_PASSWORD_PARAMETER = "userPassword";

    private final UserServiceImpl userService;

    LoginCommand() {
        this.userService = new UserServiceImpl(new UserDao());
    }

    @Override
    public ResponseContext execute(RequestContext req) {
        final String name = String.valueOf(req.getParameter(USER_NAME_PARAMETER));
        final String password = String.valueOf(req.getParameter(USER_PASSWORD_PARAMETER));
        final Optional<UserDto> userDto = userService.login(name, password);
        ResponseContext result;
        if (userDto.isPresent()) {
            req.setSessionAttribute(USER_NAME_PARAMETER, name);
            req.setSessionAttribute(USER_ROLE_ATTRIBUTE, userDto.get().getRole());
            result = ShowMainPage.INSTANCE.execute(req); // TODO: redirect
        } else {
            req.setSessionAttribute("errorMessage", "invalid credentials");
            result = ShowLoginPage.INSTANCE.execute(req); // TODO: forward
        }
        return result;
    }
}
