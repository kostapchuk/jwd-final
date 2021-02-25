package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.command.page.ShowAllMatchesPage;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;
import com.epam.jwd_final.web.service.impl.MultiplierServiceImpl;

public enum CreateMultiplierCommand implements Command {

    INSTANCE;

    private static final String FIRST_TEAM_PARAMETER = "firstTeam";
    private static final String SECOND_TEAM_PARAMETER = "secondTeam";
    private static final String START_TIME_PARAMETER = "startTime";

    private static final String FIRST_TEAM_COEFFICIENT_PARAMETER = "firstTeamCoefficient";
    private static final String SECOND_TEAM_COEFFICIENT_PARAMETER = "secondTeamCoefficient";
    private static final String DRAW_COEFFICIENT_PARAMETER = "drawCoefficient";

    private final MultiplierServiceImpl multiplierService;
    private final MatchService matchService;

    CreateMultiplierCommand() {
        this.multiplierService = MultiplierServiceImpl.INSTANCE;
        this.matchService = MatchServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
//        try {


        return ShowAllMatchesPage.INSTANCE.execute(req); // TODO: write successfully create
//        } catch (ServiceException e) {
//            throw new CommandException(e.getMessage(), e.getCause());
//        }
    }
}
