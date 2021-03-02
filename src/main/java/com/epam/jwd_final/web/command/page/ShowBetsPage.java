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
            final int id = req.getIntSessionAttribute(Parameter.USER_ID.getValue());
            final List<BetDto> activePlacedBets = betService.findAllActiveByUserId(id).orElse(Collections.emptyList());
            final List<PreviousBetDto> previousPlacedBets = betService.findAllPreviousByUserId(id).orElse(Collections.emptyList());
            req.setAttribute(Parameter.ACTIVE_PLACED_BETS.getValue(), activePlacedBets);
            req.setAttribute(Parameter.PREVIOUS_PLACED_BETS.getValue(), previousPlacedBets);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
        return ResponseContextResult.forward(Page.BETS.getLink());
    }

}
