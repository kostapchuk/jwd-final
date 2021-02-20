package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.command.page.ShowAllBetsPage;
import com.epam.jwd_final.web.dao.BetDao;
import com.epam.jwd_final.web.dao.MultiplierDao;
import com.epam.jwd_final.web.dao.UserDao;
import com.epam.jwd_final.web.service.BetService;
import com.epam.jwd_final.web.service.impl.BetServiceImpl;

public enum CancelBetCommand implements Command {

    INSTANCE;

    private final BetService betService;

    CancelBetCommand() {
        this.betService = new BetServiceImpl(new BetDao(new UserDao(), new MultiplierDao()));
    }

    @Override
    public ResponseContext execute(RequestContext req) {
        final int betId = Integer.parseInt(String.valueOf(req.getParameter("betId")));
        betService.deleteBet(betId);
        return ShowAllBetsPage.INSTANCE.execute(req);
    }
}
