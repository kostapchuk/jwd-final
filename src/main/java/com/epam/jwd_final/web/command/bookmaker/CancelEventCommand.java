package com.epam.jwd_final.web.command.bookmaker;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.command.page.ShowBookmakerPage;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ListenerException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.impl.EventServiceImpl;

import static com.epam.jwd_final.web.listener.ApplicationListener.payout;


public enum CancelEventCommand implements Command {

    INSTANCE;

    private final EventServiceImpl eventService;

    CancelEventCommand() {
        this.eventService = EventServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final int matchId = req.getIntParameter(Parameter.MATCH_ID.getValue());
            payout.payoutCancelMatch(matchId);
            eventService.cancel(matchId);
            return ShowBookmakerPage.INSTANCE.execute(req);
        } catch (ServiceException | ListenerException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
