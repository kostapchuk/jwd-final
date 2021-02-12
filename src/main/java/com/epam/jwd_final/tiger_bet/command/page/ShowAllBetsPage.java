package com.epam.jwd_final.tiger_bet.command.page;

import com.epam.jwd_final.tiger_bet.command.Command;
import com.epam.jwd_final.tiger_bet.command.context.RequestContext;
import com.epam.jwd_final.tiger_bet.command.context.ResponseContext;
import com.epam.jwd_final.tiger_bet.dao.BetDAO;

public enum ShowAllBetsPage implements Command {

    INSTANCE;

    private static final ResponseContext SHOW_ALL_BETS_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/bets.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) {
        req.setAttribute("bets", new BetDAO().retrieveAllBetsByUserId(2));
        return SHOW_ALL_BETS_RESPONSE;
    }
}
