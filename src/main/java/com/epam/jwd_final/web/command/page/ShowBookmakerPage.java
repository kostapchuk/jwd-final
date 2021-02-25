package com.epam.jwd_final.web.command.page;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;
import com.epam.jwd_final.web.service.impl.TeamService;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.time.LocalDate;
import java.util.Collections;

public enum ShowBookmakerPage implements Command {

    INSTANCE;

    public static final String MATCHES_PARAMETER = "matches";
    public static final String TEAMS_PARAMETER = "teams";

    private final MatchServiceImpl matchService;
    private final TeamService teamService;
    private final UserService userService;

    ShowBookmakerPage() {
        this.matchService = MatchServiceImpl.INSTANCE;
        this.teamService = TeamService.INSTANCE;
        this.userService = UserServiceImpl.INSTANCE;
    }

    public static final ResponseContext BOOKMAKER_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/bookmaker.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final String userName = String.valueOf(req.getSession().getAttribute("userName"));
            req.setSessionAttribute("userBalance", userService.findBalanceById(userService.findUserIdByUserName(userName)));
            req.setAttribute(MATCHES_PARAMETER, matchService.findAllByStartOfDateByResult(LocalDate.now(), Result.NO_RESULT).orElse(Collections.emptyList()));
            req.setAttribute(TEAMS_PARAMETER, teamService.findAll());
            return BOOKMAKER_PAGE_RESPONSE;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

}
