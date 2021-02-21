package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.command.page.ShowAllBetsPage;
import com.epam.jwd_final.web.dao.BetDao;
import com.epam.jwd_final.web.dao.MatchDao;
import com.epam.jwd_final.web.dao.MultiplierDao;
import com.epam.jwd_final.web.dao.UserDao;
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
        this.betService = new BetServiceImpl(new BetDao(new UserDao(), new MultiplierDao(), new MatchDao()));
        this.userService = new UserServiceImpl(new UserDao());
    }

    @Override
    public ResponseContext execute(RequestContext req) {
        final int betId = Integer.parseInt(String.valueOf(req.getParameter("betId")));
        final String userName = String.valueOf(req.getSession().getAttribute("userName"));
        final BigDecimal betMoney = betService.findBetMoneyById(betId);
        userService.topUpBalance(userName, betMoney);
        betService.deleteBet(betId);
        return ShowAllBetsPage.INSTANCE.execute(req);
    }
}