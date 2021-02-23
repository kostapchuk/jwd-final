package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.command.page.ShowAllUsersPage;
import com.epam.jwd_final.web.dao.UserDao;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

public enum UpdateRoleCommand implements Command {

    INSTANCE;

    private static final String USER_NAME_ATTRIBUTE = "userName";
    private final UserService userService;

    UpdateRoleCommand() {
        this.userService = UserServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            //final String userName = String.valueOf(req.getSession().getAttribute(USER_NAME_ATTRIBUTE));
            final String userName = String.valueOf(req.getParameter(USER_NAME_ATTRIBUTE));
            userService.updateRole(userName);
            return ShowAllUsersPage.INSTANCE.execute(req);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
