package com.epam.jwd_final.web.observer;

import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.ListenerException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.BetService;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.MultiplierService;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.BetServiceImpl;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;
import com.epam.jwd_final.web.service.impl.MultiplierServiceImpl;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.util.Collections;
import java.util.List;

public class WinUserListener implements EventListener {

    private final UserService userService;
    private final MatchService matchService;
    private final MultiplierService multiplierService;
    private final BetService betService;

    public WinUserListener() {
        this.userService = UserServiceImpl.INSTANCE;
        this.matchService = MatchServiceImpl.INSTANCE;
        this.multiplierService = MultiplierServiceImpl.INSTANCE;
        this.betService = BetServiceImpl.INSTANCE;
    }

    @Override
    public void update(String eventType, int matchId) throws ListenerException {
        try {
            final Result actualResult = matchService.findResultTypeById(matchId);
            final int winMultiplier = multiplierService.findIdByMatchIdAndResult(matchId, actualResult);
            final List<Integer> usersId =
                    betService.findAllUserIdByMultiplierId(winMultiplier).orElse(Collections.emptyList());
            for (Integer userId : usersId) {
                userService.topUpBalance(
                        userId,
                        userService.calculateExpectedWin(userId, winMultiplier)
                );
            }
        } catch (ServiceException e) {
            throw new ListenerException(e.getMessage(), e.getCause());
        }
    }
}
