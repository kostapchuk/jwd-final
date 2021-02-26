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

public enum ShowDepositPage implements Command {

    INSTANCE;

    private final UserService userService;

    ShowDepositPage() {
        this.userService = UserServiceImpl.INSTANCE;
    }

    private static final ResponseContext DEPOSIT_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return Page.DEPOSIT.getLink();
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final String userName = req.getStringSessionAttribute(Parameter.USER_NAME.getValue());
            req.setSessionAttribute(Parameter.USER_BALANCE.getValue(), userService.findBalanceById(userService.findUserIdByUserName(userName)));
            return DEPOSIT_PAGE_RESPONSE;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
