package com.epam.jwd_final.web.command.page;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Page;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.command.ResponseContextResult;
import com.epam.jwd_final.web.domain.dto.BetDto;
import com.epam.jwd_final.web.domain.dto.PreviousBetDto;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.BetService;
import com.epam.jwd_final.web.service.impl.BetServiceImpl;

import java.util.Collections;
import java.util.List;

public enum ShowBetsPage implements Command {

    INSTANCE;

    private final BetService betService;

    ShowBetsPage() {
        this.betService = BetServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final int userId = req.getIntSessionAttribute(Parameter.USER_ID.getValue());

            final List<BetDto> activeBets =
                    betService.findAllActiveByUserId(userId);
            final List<PreviousBetDto> previousBets =
                    betService.findAllPreviousByUserId(userId);

            req.setAttribute(Parameter.ACTIVE_BETS.getValue(), activeBets);
            req.setAttribute(Parameter.PREVIOUS_BETS.getValue(), previousBets);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
        return ResponseContextResult.forward(Page.BETS.getLink());
    }

}
