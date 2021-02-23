package com.epam.jwd_final.web.command.page;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;

import java.time.LocalDate;
import java.util.Collections;

public enum ShowAllMatchesPage implements Command {

    INSTANCE;

    public static final String MATCHES_PARAMETER = "matches";

    private final MatchServiceImpl matchService;

    ShowAllMatchesPage() {
        this.matchService = MatchServiceImpl.INSTANCE;
    }

    private static final ResponseContext ALL_MATCHES_PAGE_RESPONSE = new ResponseContext() {

        @Override
        public String getPage() {
            return "/WEB-INF/jsp/allmatches.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            req.setAttribute(MATCHES_PARAMETER, matchService.findAllByStartOfDateByResult(LocalDate.now(), Result.NO_RESULT).orElse(Collections.emptyList()));
            return ALL_MATCHES_PAGE_RESPONSE;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
