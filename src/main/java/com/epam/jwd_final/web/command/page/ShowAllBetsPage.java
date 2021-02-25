package com.epam.jwd_final.web.command.page;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.domain.BetDto;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.BetService;
import com.epam.jwd_final.web.service.impl.BetServiceImpl;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.util.Collections;
import java.util.List;

public enum ShowAllBetsPage implements Command {

    INSTANCE;

    public static final String NAME_PARAMETER = "userName";
    public static final String BETS_PARAMETER = "bets";

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
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final String name = String.valueOf(req.getSession().getAttribute(NAME_PARAMETER));
            req.setSessionAttribute("userBalance", userService.findBalanceById(userService.findUserIdByUserName(name)));
            final List<BetDto> betDtos = betService.findAllByUserName(name).orElse(Collections.emptyList());
            for (BetDto bet : betDtos) {
                bet.setExpectedWin(userService.calculateExpectedWin(name, betService.findMultiplierIdById(bet.getId())));
            }
            req.setAttribute(BETS_PARAMETER, betDtos);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
        return ALL_BETS_PAGE_RESPONSE;
    }
}
