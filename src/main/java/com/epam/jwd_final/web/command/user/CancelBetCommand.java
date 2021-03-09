package com.epam.jwd_final.web.command.user;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.command.page.ShowBetsPage;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.BetService;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.BetServiceImpl;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.math.BigDecimal;

public enum CancelBetCommand implements Command {

    INSTANCE;

    private final BetService betService;
    private final UserService userService;

    CancelBetCommand() {
        this.betService = BetServiceImpl.INSTANCE;
        this.userService = UserServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final int betId = req.getIntParameter(Parameter.BET_ID.getValue());
            final Integer userId = req.getIntSessionAttribute(Parameter.USER_ID.getValue());
            final BigDecimal betMoney = betService.findBetMoneyById(betId);

            userService.increaseBalance(userId, betMoney);
            betService.deleteById(betId);

            return ShowBetsPage.INSTANCE.execute(req);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
