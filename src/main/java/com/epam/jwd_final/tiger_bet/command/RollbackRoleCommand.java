package com.epam.jwd_final.tiger_bet.command;

import com.epam.jwd_final.tiger_bet.command.page.ShowAdminPage;
import com.epam.jwd_final.tiger_bet.dao.UserDao;
import com.epam.jwd_final.tiger_bet.service.UserService;
import com.epam.jwd_final.tiger_bet.service.impl.UserServiceImpl;

public enum RollbackRoleCommand implements Command {

    INSTANCE;

    private static final String USER_NAME_ATTRIBUTE = "userName";

    private final UserService userService;

    RollbackRoleCommand() {
        this.userService = new UserServiceImpl(new UserDao());
    }

    @Override
    public ResponseContext execute(RequestContext req) {
        final String userName = String.valueOf(req.getSession().getAttribute(USER_NAME_ATTRIBUTE));
        userService.rollbackRole(userName);
        return ShowAdminPage.INSTANCE.execute(req);
    }
}
