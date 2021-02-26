package com.epam.jwd_final.web.command.bookmaker;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.command.page.ShowBookmakerPage;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.BetService;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.BetServiceImpl;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;
import com.epam.jwd_final.web.service.impl.MultiplierServiceImpl;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.util.Collections;
import java.util.List;

public enum CancelMatchCommand implements Command {

    INSTANCE;

    private final MatchService matchService;
    private final BetService betService;
    private final MultiplierServiceImpl multiplierService;
    private final UserService userService;

    CancelMatchCommand() {
        this.matchService = MatchServiceImpl.INSTANCE;
        this.betService = BetServiceImpl.INSTANCE;
        this.multiplierService = MultiplierServiceImpl.INSTANCE;
        this.userService = UserServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final int matchId = req.getIntParameter(Parameter.MATCH_ID.getValue());

            final int firstTeamMultiplierId = multiplierService.findIdByMatchIdAndResult(matchId, Result.FIRST_TEAM);
            final int secondTeamMultiplierId = multiplierService.findIdByMatchIdAndResult(matchId, Result.SECOND_TEAM);
            final int drawMultiplierId = multiplierService.findIdByMatchIdAndResult(matchId, Result.DRAW);

            final List<Integer> userIdsFirstTeam = betService.findAllUserIdByMultiplierId(firstTeamMultiplierId).orElse(Collections.emptyList());
            final List<Integer> userIdsSecondTeam = betService.findAllUserIdByMultiplierId(secondTeamMultiplierId).orElse(Collections.emptyList());
            final List<Integer> userIdsDraw = betService.findAllUserIdByMultiplierId(drawMultiplierId).orElse(Collections.emptyList());

            for (Integer userId : userIdsFirstTeam) {
                userService.topUpBalance(
                        userService.findNameById(userId),
                        betService.findBetMoneyByUserIdByMultiplierId(userId, firstTeamMultiplierId)
                );
            }
            for (Integer userId : userIdsSecondTeam) {
                userService.topUpBalance(
                        userService.findNameById(userId),
                        betService.findBetMoneyByUserIdByMultiplierId(userId, secondTeamMultiplierId)
                );
            }
            for (Integer userId : userIdsDraw) {
                userService.topUpBalance(
                        userService.findNameById(userId),
                        betService.findBetMoneyByUserIdByMultiplierId(userId, drawMultiplierId)
                );
            }

            betService.deleteAllByMultiplierId(firstTeamMultiplierId);
            betService.deleteAllByMultiplierId(secondTeamMultiplierId);
            betService.deleteAllByMultiplierId(drawMultiplierId);

            multiplierService.deleteById(firstTeamMultiplierId);
            multiplierService.deleteById(secondTeamMultiplierId);
            multiplierService.deleteById(drawMultiplierId);

            matchService.deleteById(matchId);

            return ShowBookmakerPage.INSTANCE.execute(req);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }

    }
}
