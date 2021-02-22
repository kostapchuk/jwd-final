package com.epam.jwd_final.web.command.page;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.dao.BetDao;
import com.epam.jwd_final.web.dao.MatchDao;
import com.epam.jwd_final.web.dao.MultiplierDao;
import com.epam.jwd_final.web.dao.UserDao;
import com.epam.jwd_final.web.domain.BetDto;
import com.epam.jwd_final.web.service.BetService;
import com.epam.jwd_final.web.service.impl.BetServiceImpl;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.util.Collections;
import java.util.List;

public enum ShowAllBetsPage implements Command {

    INSTANCE;

    public static final String NAME_PARAMETER = "userName";
    public static final String BETS_PARAMETER = "bets";
    private static final String EXPECTED_WIN_PARAMETER = "expectedWin";

    private final UserServiceImpl userService;
    private final BetService betService;

    ShowAllBetsPage() {
            this.userService = UserServiceImpl.INSTANCE;
            this.betService = BetServiceImpl.INSTANCE;
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
        for (BetDto bet : betDtos) {
            bet.setExpectedWin(userService.calculateExpectedWin(name, betService.findMultiplierIdById(bet.getId())));
        }
        req.setAttribute(BETS_PARAMETER, betDtos);
        return ALL_BETS_PAGE_RESPONSE;
    }
}
