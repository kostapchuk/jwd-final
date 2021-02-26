package com.epam.jwd_final.web.command.page;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.ResponseContextResult;
import com.epam.jwd_final.web.command.Page;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.TeamService;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;
import com.epam.jwd_final.web.service.impl.TeamServiceImpl;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.time.LocalDate;
import java.util.Collections;

public enum ShowBookmakerPage implements Command {

    INSTANCE;

    private final MatchService matchService;
    private final TeamService teamService;
    private final UserService userService;

    ShowBookmakerPage() {
        this.matchService = MatchServiceImpl.INSTANCE;
        this.teamService = TeamServiceImpl.INSTANCE;
        this.userService = UserServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final String userName = req.getStringSessionAttribute(Parameter.USER_NAME.getValue());
            req.setSessionAttribute(Parameter.USER_BALANCE.getValue(), userService.findBalanceById(userService.findUserIdByUserName(userName)));
            req.setAttribute(Parameter.MATCHES.getValue(), matchService.findAllByStartOfDateByResult(LocalDate.now(), Result.NO_RESULT).orElse(Collections.emptyList()));
            req.setAttribute(Parameter.TEAMS.getValue(), teamService.findAll());
            return ResponseContextResult.redirect(Page.BOOKMAKER.getLink());
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

}
