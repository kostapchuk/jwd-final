package com.epam.jwd_final.web.command.user;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.command.page.ShowWithdrawPage;
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
            final Integer userId = req.getIntSessionAttribute(Parameter.USER_ID.getValue());
            final BigDecimal depositMoney = new BigDecimal(req.getStringParameter(Parameter.WITHDRAW_MONEY.getValue()));
            final BigDecimal currentBalance = new BigDecimal(req.getStringSessionAttribute(Parameter.USER_BALANCE.getValue()));
            if (currentBalance.subtract(depositMoney).compareTo(BigDecimal.ZERO) >= 0) {
                userService.withdrawFromBalance(userId, depositMoney);
            }
            return ShowWithdrawPage.INSTANCE.execute(req);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
