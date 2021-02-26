package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.command.page.ShowWithdrawPage;
import com.epam.jwd_final.web.dao.UserDao;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.math.BigDecimal;

public enum WithdrawCommand implements Command {

    INSTANCE;

    private final UserService userService;

    WithdrawCommand() {
        this.userService = UserServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final String userName = String.valueOf(req.getSession().getAttribute(Parameter.USER_NAME.getParameter()));
            final BigDecimal depositMoney = new BigDecimal(String.valueOf(req.getParameter(Parameter.WITHDRAW_MONEY.getParameter())));
            final BigDecimal currentBalance = new BigDecimal(String.valueOf(req.getSession().getAttribute(Parameter.USER_BALANCE.getParameter())));
            if (currentBalance.subtract(depositMoney).compareTo(BigDecimal.ZERO) >= 0) {
                userService.withdrawFromBalance(userName, depositMoney);
            }
            return ShowWithdrawPage.INSTANCE.execute(req);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
