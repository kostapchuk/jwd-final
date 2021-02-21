package com.epam.jwd_final.web.observer;

import com.epam.jwd_final.web.dao.BetDao;
import com.epam.jwd_final.web.dao.MatchDao;
import com.epam.jwd_final.web.dao.MultiplierDao;
import com.epam.jwd_final.web.dao.TeamDao;
import com.epam.jwd_final.web.dao.UserDao;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.domain.UserDto;
import com.epam.jwd_final.web.service.BetService;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.BetServiceImpl;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;
import com.epam.jwd_final.web.service.impl.MultiplierServiceImpl;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.math.BigDecimal;
import java.util.List;

public class PayoutNotificationListener implements EventListener {

    private final UserService userService;
    private final MatchService matchService;
    private final MultiplierServiceImpl multiplierService;
    private final BetService betService;

    public PayoutNotificationListener() {
        this.userService = new UserServiceImpl(new UserDao());
        this.matchService = new MatchServiceImpl(new MatchDao(), new TeamDao());
        this.multiplierService = new MultiplierServiceImpl(new MultiplierDao());
        this.betService = new BetServiceImpl(new BetDao(new UserDao(), new MultiplierDao(), new MatchDao()));
    }

    @Override
    public void update(String eventType, int matchId) {
        final List<UserDto> userDtos = userService.findAll().orElseThrow(IllegalArgumentException::new);
        for (UserDto user : userDtos) {
            if (betService.isUserWinner(user.getName(), matchId)) {
                final Result result = matchService.findResultTypeById(matchId);
                final int multiplierId = multiplierService.findIdByMatchIdAndResult(matchId, result);
                final BigDecimal winMoneyAmount = betService.calculateExpectedWin(user.getName(), multiplierId);
                userService.topUpBalance(user.getName(), winMoneyAmount);
            }
        }
    }
}
