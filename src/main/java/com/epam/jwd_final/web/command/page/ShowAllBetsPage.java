package com.epam.jwd_final.web.command.page;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.dao.BetDao;
import com.epam.jwd_final.web.dao.MultiplierDao;
import com.epam.jwd_final.web.dao.UserDao;
import com.epam.jwd_final.web.domain.BetDto;
import com.epam.jwd_final.web.service.impl.BetServiceImpl;

import java.util.Collections;
import java.util.List;

public enum ShowAllBetsPage implements Command {

    INSTANCE;

    public static final String NAME_PARAMETER = "userName";
    public static final String BETS_PARAMETER = "bets";
    private static final String EXPECTED_WIN_PARAMETER = "expectedWin";

    private final BetServiceImpl betService;

    ShowAllBetsPage() {
        this.betService = new BetServiceImpl(new BetDao(new UserDao(), new MultiplierDao()));
    }

    public static final ResponseContext ALL_BETS_PAGE_RESPONSE = new ResponseContext() {

        @Override
        public String getPage() {
            return "/WEB-INF/jsp/allbets.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) {
        final String name = String.valueOf(req.getSession().getAttribute(NAME_PARAMETER));
        if (name == null || name.equals("null")) {
            return ShowErrorPage.INSTANCE.execute(req);
        }
        final List<BetDto> betDtos = betService.findAllBetsByUserName(name).orElse(Collections.emptyList());
        req.setAttribute(BETS_PARAMETER, betDtos);
        req.setAttribute(EXPECTED_WIN_PARAMETER, betService.calculateExpectedWin(name));
        return ALL_BETS_PAGE_RESPONSE;
    }
}
