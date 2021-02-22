package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;

public enum CreateMatchCommand implements Command {

    INSTANCE;

    private static final String SPORT_TYPE_PARAMETER = "sportType";
    private static final String FIRST_TEAM_PARAMETER = "firstTeam";
    private static final String SECOND_TEAM_PARAMETER = "secondTeam";
    private static final String START_TIME_PARAMETER = "startTime";

    private final MatchService matchService;

    CreateMatchCommand() {
        matchService = MatchServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) {
        final String sportType = String.valueOf(req.getParameter(SPORT_TYPE_PARAMETER));
        final String start = String.valueOf(req.getParameter(START_TIME_PARAMETER));
        final String firstTeam = String.valueOf(req.getParameter(FIRST_TEAM_PARAMETER));
        final String secondTeam = String.valueOf(req.getParameter(SECOND_TEAM_PARAMETER));
        matchService.saveMatch(matchService.createMatch(sportType, start, firstTeam, secondTeam));
        return CreateMultiplierCommand.INSTANCE.execute(req);
    }
}
