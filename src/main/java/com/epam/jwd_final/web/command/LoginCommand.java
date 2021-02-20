package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.command.page.ShowMainPage;
import com.epam.jwd_final.web.dao.UserDao;
import com.epam.jwd_final.web.domain.UserDto;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.util.Optional;

public enum LoginCommand implements Command {

    INSTANCE;

    private static final String USER_ROLE_ATTRIBUTE = "userRole";
    private static final String USER_NAME_PARAMETER = "userName";
    private static final String USER_PASSWORD_PARAMETER = "userPassword";

    private final UserServiceImpl userService;

    LoginCommand() {
        this.userService = new UserServiceImpl(new UserDao());
    }

    @Override
    public ResponseContext execute(RequestContext req) {
        final String name = String.valueOf(req.getParameter(USER_NAME_PARAMETER));
        final String password = String.valueOf(req.getParameter(USER_PASSWORD_PARAMETER));
        final Optional<UserDto> userDto = userService.login(name, password);
        if (userDto.isPresent()) {
            req.setSessionAttribute(USER_NAME_PARAMETER, name);
            req.setSessionAttribute(USER_ROLE_ATTRIBUTE, userDto.get().getRole());
             // TODO: redirect
        } else {
            req.setSessionAttribute("errorMessage", "invalid credentials");
            // TODO: forward
        }
        return ShowMainPage.INSTANCE.execute(req);
    }
}
