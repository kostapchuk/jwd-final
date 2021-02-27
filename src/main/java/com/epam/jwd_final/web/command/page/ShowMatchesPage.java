package com.epam.jwd_final.web.command.page;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.ResponseContextResult;
import com.epam.jwd_final.web.command.Page;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.domain.EventDto;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.impl.EventService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public enum ShowMatchesPage implements Command {

    INSTANCE;

    private final EventService eventService;

    ShowMatchesPage() {
        this.eventService = EventService.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final LocalDate yesterday = LocalDateTime.now().minusDays(1).toLocalDate();
            final LocalDate tomorrow = LocalDateTime.now().plusDays(1).toLocalDate();

            final List<EventDto> events =
                    eventService.findAllUnfinishedByDateBetween(yesterday, tomorrow).orElse(Collections.emptyList());
            req.setAttribute(Parameter.EVENTS.getValue(), events);
            return ResponseContextResult.forward(Page.MATCHES.getLink());
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
