package com.epam.jwd_final.web.command.page;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.domain.EventDto;
import com.epam.jwd_final.web.domain.MatchDto;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
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

    public static final String MATCHES_PARAMETER = "matches";
    public static final String EVENTS_PARAMETER = "events";

    private final MatchServiceImpl matchService;
    private final UserService userService;
    private final MultiplierServiceImpl multiplierService;

    ShowMatchesPage() {
        this.matchService = MatchServiceImpl.INSTANCE;
        this.userService = UserServiceImpl.INSTANCE;
        this.multiplierService = MultiplierServiceImpl.INSTANCE;
    }

    private static final ResponseContext ALL_MATCHES_PAGE_RESPONSE = new ResponseContext() {

        @Override
        public String getPage() {
            return "/WEB-INF/jsp/matches.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
//            req.setAttribute(MATCHES_PARAMETER,
//                    matchService.findAllByStartOfDateByResult(LocalDate.now(), Result.NO_RESULT).orElse(Collections.emptyList()));
            if (req.getSession() != null) {
                final String userName = String.valueOf(req.getSession().getAttribute("userName"));
                if (!userName.equals("null")) {
                    req.setSessionAttribute("userBalance", userService.findBalanceById(userService.findUserIdByUserName(userName)));
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
            req.setAttribute(EVENTS_PARAMETER, eventDtos);

            return ALL_MATCHES_PAGE_RESPONSE;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
