package com.epam.jwd_final.web.command.page;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.ResponseContextResult;
import com.epam.jwd_final.web.command.Page;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.domain.EventDto;
import com.epam.jwd_final.web.domain.MatchDto;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.MultiplierService;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;
import com.epam.jwd_final.web.service.impl.MultiplierServiceImpl;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public enum ShowMatchesPage implements Command {

    INSTANCE;

    private final MatchService matchService;
    private final UserService userService;
    private final MultiplierService multiplierService;

    ShowMatchesPage() {
        this.matchService = MatchServiceImpl.INSTANCE;
        this.userService = UserServiceImpl.INSTANCE;
        this.multiplierService = MultiplierServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            if (req.getSession() != null) {
                final String userName = req.getStringSessionAttribute(Parameter.USER_NAME.getValue());
                if (!userName.equals("null")) {
                    req.setSessionAttribute(Parameter.USER_BALANCE.getValue(), userService.findBalanceById(userService.findUserIdByUserName(userName)));
                }
            }

            final Optional<List<MatchDto>> matchDtos = matchService.findAllByStartOfDateByResult(LocalDate.now(), Result.NO_RESULT);
            List<EventDto> eventDtos = new ArrayList<>();
            if (matchDtos.isPresent()) {
                for (MatchDto matchDto : matchDtos.get()) {
                    final BigDecimal firstTeamCoef = multiplierService.findCoefficientByMatchIdByResult(matchDto.getId(), Result.FIRST_TEAM);
                    final BigDecimal secondTeamCoef = multiplierService.findCoefficientByMatchIdByResult(matchDto.getId(), Result.SECOND_TEAM);
                    final BigDecimal drawCoef = multiplierService.findCoefficientByMatchIdByResult(matchDto.getId(), Result.DRAW);
                    eventDtos.add(new EventDto(
                            matchDto.getId(), matchDto.getStart(), matchDto.getFirstTeam(),
                            matchDto.getSecondTeam(), firstTeamCoef, secondTeamCoef, drawCoef)
                    );

                }
            } else {
                eventDtos = Collections.emptyList();
            }
            req.setAttribute(Parameter.EVENTS.getValue(), eventDtos);

            return ResponseContextResult.forward(Page.MATCHES.getLink());
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
