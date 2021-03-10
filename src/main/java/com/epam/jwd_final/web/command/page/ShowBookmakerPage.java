package com.epam.jwd_final.web.command.page;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Page;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.command.ResponseContextResult;
import com.epam.jwd_final.web.domain.dto.MatchDto;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.TeamService;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;
import com.epam.jwd_final.web.service.impl.TeamServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public enum ShowBookmakerPage implements Command {

    INSTANCE;

    private final MatchService matchService;
    private final TeamService teamService;

    ShowBookmakerPage() {
        this.matchService = MatchServiceImpl.INSTANCE;
        this.teamService = TeamServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final LocalDateTime yesterday = LocalDateTime.now().minusDays(1).toLocalDate().atStartOfDay();
            final LocalDateTime tomorrow = LocalDateTime.now().plusDays(2).toLocalDate().atStartOfDay().minusSeconds(1);

            final List<MatchDto> matches =
                    matchService.findAllUnfinishedByDateBetween(yesterday, tomorrow);

            req.setAttribute(Parameter.MATCHES.getValue(), matches);
            req.setAttribute(Parameter.TEAMS.getValue(), teamService.findAll());
            return ResponseContextResult.forward(Page.BOOKMAKER.getLink());
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }

}
