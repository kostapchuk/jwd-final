package com.epam.jwd_final.web.command.page;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Page;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

public enum ShowWithdrawPage implements Command {

    INSTANCE;

    private final UserService userService;

    ShowWithdrawPage() {
        this.userService = UserServiceImpl.INSTANCE;
    }


    private static final ResponseContext WITHDRAW_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return Page.WITHDRAW.getLink();
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final String userName = String.valueOf(req.getSession().getAttribute(Parameter.USER_NAME.getParameter()));
            req.setSessionAttribute(Parameter.USER_BALANCE.getParameter(), userService.findBalanceById(userService.findUserIdByUserName(userName)));
            return WITHDRAW_PAGE_RESPONSE;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
