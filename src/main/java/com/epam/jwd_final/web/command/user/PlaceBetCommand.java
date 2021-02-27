package com.epam.jwd_final.web.command.user;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.command.page.ShowEventsPage;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.BetService;
import com.epam.jwd_final.web.service.MultiplierService;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.BetServiceImpl;
import com.epam.jwd_final.web.service.impl.MultiplierServiceImpl;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.math.BigDecimal;

public enum PlaceBetCommand implements Command {

    INSTANCE;

    private final UserService userService;
    private final MultiplierService multiplierService;
    private final BetService betService;

    PlaceBetCommand() {
        this.userService = UserServiceImpl.INSTANCE;
        this.multiplierService = MultiplierServiceImpl.INSTANCE;
        this.betService = BetServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final int matchId = req.getIntParameter(Parameter.MATCH_ID.getValue());
            final String userResult = req.getStringParameter(Parameter.RESULT.getValue());
            final int multiplierId = multiplierService.findIdByMatchIdAndResult(matchId, Result.valueOf(userResult));
            final String userName = req.getStringSessionAttribute(Parameter.USER_NAME.getValue());
            if ("null".equals(userName)) {
                return ShowEventsPage.INSTANCE.execute(req);
            }
            final int userId = userService.findUserIdByUserName(userName);
            if (!betService.isBetExist(userId, multiplierId)) {
                final BigDecimal betMoney = new BigDecimal(req.getStringParameter(Parameter.BET_MONEY.getValue()));
                final BigDecimal currentBalance = userService.findBalanceById(userId);
                final BigDecimal finalBalance = currentBalance.subtract(betMoney);

                if (finalBalance.compareTo(BigDecimal.ZERO) >= 0) {
                    betService.save(
                            betService.createBet(userId, multiplierId, betMoney));
                    userService.withdrawFromBalance(userId, betMoney); // TODO: try to make more general
                }
            }
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
        return ShowEventsPage.INSTANCE.execute(req);
    }
}
