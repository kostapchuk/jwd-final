package com.epam.jwd_final.web.command.page;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Page;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.command.ResponseContextResult;
import com.epam.jwd_final.web.domain.dto.EventDto;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.impl.EventServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

public enum ShowEventsPage implements Command {

    INSTANCE;

    private final EventServiceImpl eventService;

    ShowEventsPage() {
        this.eventService = EventServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
            final LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);

            final List<EventDto> events =
                    eventService.findAllUnfinishedByDateBetween(yesterday, tomorrow);

            req.setAttribute(Parameter.EVENTS.getValue(), events);
            return ResponseContextResult.forward(Page.EVENTS.getLink());
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
