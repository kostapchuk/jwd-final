package com.epam.jwd_final.web.observer;

import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.ListenerException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.BetService;
import com.epam.jwd_final.web.service.MultiplierService;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.BetServiceImpl;
import com.epam.jwd_final.web.service.impl.MultiplierServiceImpl;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.util.Collections;
import java.util.List;

public class CancelMatchListener implements EventListener {

    private final UserService userService;
    private final BetService betService;
    private final MultiplierService multiplierService;

    public CancelMatchListener() {
        this.userService = UserServiceImpl.INSTANCE;
        this.betService = BetServiceImpl.INSTANCE;
        this.multiplierService = MultiplierServiceImpl.INSTANCE;
    }

    @Override
    public void update(String eventType, int matchId) throws ListenerException {
        try {
            for (Result value : Result.values()) {
                final int multiplierId = multiplierService.findIdByMatchIdByResult(matchId, value);
                final List<Integer> userIds = betService
                        .findAllUserIdByMultiplierId(multiplierId).orElse(Collections.emptyList());

                for (Integer userId : userIds) {
                    userService.increaseBalance(
                            userId,
                            betService.findBetMoneyByUserIdByMultiplierId(userId, multiplierId)
                    );
                }
            }
        } catch (ServiceException e) {
            throw new ListenerException(e.getMessage(), e.getCause());
        }
    }
}
