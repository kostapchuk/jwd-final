package com.epam.jwd_final.tiger_bet.command;

import com.epam.jwd_final.tiger_bet.command.page.ShowAllBetsPage;
import com.epam.jwd_final.tiger_bet.dao.BetDao;
import com.epam.jwd_final.tiger_bet.dao.UserDao;
import com.epam.jwd_final.tiger_bet.service.BetService;
import com.epam.jwd_final.tiger_bet.service.impl.BetServiceImpl;

public enum CancelBetCommand implements Command {

    INSTANCE;

    private final BetService betService;

    CancelBetCommand() {
        this.betService = new BetServiceImpl(new BetDao(new UserDao()));
    }

    @Override
    public ResponseContext execute(RequestContext req) {
        final int betId = Integer.parseInt(String.valueOf(req.getParameter("betId")));
        betService.deleteBet(betId);
        return ShowAllBetsPage.INSTANCE.execute(req);
    }
}
