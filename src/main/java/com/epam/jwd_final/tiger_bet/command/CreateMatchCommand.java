package com.epam.jwd_final.tiger_bet.command;

import com.epam.jwd_final.tiger_bet.dao.MatchDao;
import com.epam.jwd_final.tiger_bet.service.MatchService;
import com.epam.jwd_final.tiger_bet.service.impl.MatchServiceImpl;

import java.time.LocalDateTime;

public enum CreateMatchCommand implements Command {

    INSTANCE;

    private static final String SPORT_TYPE_PARAMETER = "sportType";
    private static final String FIRST_TEAM_PARAMETER = "firstTeam";
    private static final String SECOND_TEAM_PARAMETER = "secondTeam";
    private static final String START_TIME_PARAMETER = "startTime";

    private final MatchService matchService;

    CreateMatchCommand() {
        matchService = new MatchServiceImpl(new MatchDao());
    }

    @Override
    public ResponseContext execute(RequestContext req) {
        final String sportType = String.valueOf(req.getAttribute(SPORT_TYPE_PARAMETER));
        final String start = String.valueOf(req.getAttribute(START_TIME_PARAMETER));
        final String firstTeam = String.valueOf(req.getAttribute(FIRST_TEAM_PARAMETER));
        final String secondTeam = String.valueOf(req.getAttribute(SECOND_TEAM_PARAMETER));
        matchService.createMatch(sportType, start, firstTeam, secondTeam);
        return CreateMultiplierCommand.INSTANCE.execute(req);
    }
}
