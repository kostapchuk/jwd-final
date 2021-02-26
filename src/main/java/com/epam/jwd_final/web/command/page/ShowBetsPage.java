package com.epam.jwd_final.web.command.page;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.ResponseContextResult;
import com.epam.jwd_final.web.command.Page;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.domain.BetDto;
import com.epam.jwd_final.web.domain.Match;
import com.epam.jwd_final.web.domain.PlacedBetDto;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.BetService;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.MultiplierService;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.BetServiceImpl;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;
import com.epam.jwd_final.web.service.impl.MultiplierServiceImpl;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public enum ShowBetsPage implements Command {

    INSTANCE;

    private final UserService userService;
    private final BetService betService;
    private final MultiplierService multiplierService;
    private final MatchService matchService;

    ShowBetsPage() {
        this.userService = UserServiceImpl.INSTANCE;
        this.betService = BetServiceImpl.INSTANCE;
        this.multiplierService = MultiplierServiceImpl.INSTANCE;
        this.matchService = MatchServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final String name = req.getStringSessionAttribute(Parameter.USER_NAME.getValue());
            req.setSessionAttribute(Parameter.USER_BALANCE.getValue(), userService.findBalanceById(userService.findUserIdByUserName(name)));

            final List<BetDto> betDtos = betService.findAllByUserName(name).orElse(Collections.emptyList());
            List<PlacedBetDto> placedBetDtos = new ArrayList<>();
            for (BetDto bet : betDtos) {
                final int multiplierId = betService.findMultiplierIdById(bet.getId());
                final int matchId = multiplierService.findMatchIdByMultiplierId(multiplierId);
                final Match match = matchService.findById(matchId);
                final BigDecimal placedCoefficient = multiplierService.findCoefficientById(multiplierId);
                final Result placedResult = multiplierService.findResultById(multiplierId);
                String placedTeam;
                if (Result.FIRST_TEAM.equals(placedResult)) {
                    placedTeam = match.getFirstTeam();
                } else if (Result.SECOND_TEAM.equals(placedResult)) {
                    placedTeam = match.getSecondTeam();
                } else if (Result.DRAW.equals(placedResult)) {
                    placedTeam = "Draw";
                } else {
                    placedTeam = "";
                }
                placedBetDtos.add(new PlacedBetDto(bet.getId(),
                        match.getStart().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm", Locale.getDefault())),
                        placedTeam, placedCoefficient, userService.calculateExpectedWin(name, multiplierId),
                        match.getFirstTeam() + " - " + match.getSecondTeam()));
            }
            req.setAttribute(Parameter.BETS.getValue(), betDtos);
            req.setAttribute(Parameter.PLACED_BETS.getValue(), placedBetDtos);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
        return ResponseContextResult.forward(Page.BETS.getLink());
    }
}
