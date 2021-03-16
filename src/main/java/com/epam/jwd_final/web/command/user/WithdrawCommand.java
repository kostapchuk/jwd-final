package com.epam.jwd_final.web.command.user;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Page;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.command.ResponseContextResult;
import com.epam.jwd_final.web.command.page.ShowWithdrawPage;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.math.BigDecimal;

public enum WithdrawCommand implements Command {

    INSTANCE;

    private static final String WITHDRAW_PAGE = "/controller?command=show_withdraw_page";
    private static final String WITHDREW_AMOUNT = "withdrewAmount";
    private static final String NOT_ENOUGH_MONEY_ERROR = "notEnoughMoneyError";

    private final UserService userService;

    WithdrawCommand() {
        this.userService = UserServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final Integer userId = req.getIntSessionAttribute(Parameter.USER_ID.getValue());
            final BigDecimal toWithdrawAmount =
                    new BigDecimal(req.getStringParameter(Parameter.WITHDRAW_MONEY.getValue()));

            final BigDecimal previousBalance = userService.findBalanceById(userId);

            final BigDecimal newBalance = previousBalance.subtract(toWithdrawAmount);
            if (newBalance.compareTo(BigDecimal.ZERO) >= 0) {
                userService.decreaseBalance(userId, toWithdrawAmount);
                req.setAttribute(WITHDREW_AMOUNT, toWithdrawAmount.toString());
                return ResponseContextResult.redirect(WITHDRAW_PAGE);
            } else {
                req.setAttribute(NOT_ENOUGH_MONEY_ERROR, toWithdrawAmount.toString());
                return ShowWithdrawPage.INSTANCE.execute(req);
            }
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
