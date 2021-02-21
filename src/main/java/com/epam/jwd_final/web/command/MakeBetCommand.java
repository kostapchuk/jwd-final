package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.command.page.ShowAllMatchesPage;
import com.epam.jwd_final.web.dao.BetDao;
import com.epam.jwd_final.web.dao.MatchDao;
import com.epam.jwd_final.web.dao.MultiplierDao;
import com.epam.jwd_final.web.dao.UserDao;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.service.BetService;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.BetServiceImpl;
import com.epam.jwd_final.web.service.impl.MultiplierServiceImpl;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.math.BigDecimal;

public enum MakeBetCommand implements Command {

    INSTANCE;

    private final UserService userService;
    private final MultiplierServiceImpl multiplierService;
    private final BetService betService;

    MakeBetCommand() {
        this.userService = new UserServiceImpl(new UserDao());
        this.multiplierService = new MultiplierServiceImpl(new MultiplierDao());
        this.betService = new BetServiceImpl(new BetDao(new UserDao(), new MultiplierDao(), new MatchDao()));
    }

    @Override
    public ResponseContext execute(RequestContext req) {
        final int matchId = Integer.parseInt(String.valueOf(req.getSession().getAttribute("matchId")));
        final String userResult = String.valueOf(req.getParameter("userResult"));
        final int multiplierId = multiplierService.findIdByMatchIdAndResult(matchId, Result.valueOf(userResult));
        final int userId = userService.findUserIdByUserName(String.valueOf(req.getSession().getAttribute("userName")));

        betService.saveBet(
                betService.createBet(userId, multiplierId, new BigDecimal(String.valueOf(req.getParameter("betMoney")))));
        return ShowAllMatchesPage.INSTANCE.execute(req);
    }
}
