package com.epam.jwd_final.web.observer;

import com.epam.jwd_final.web.dao.impl.MultiplierDao;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.domain.UserDto;
import com.epam.jwd_final.web.exception.ListenerException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.BetService;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.BetServiceImpl;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;
import com.epam.jwd_final.web.service.impl.MultiplierServiceImpl;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.math.BigDecimal;
import java.util.List;

public class PayoutUserWinListener implements EventListener {

    private final UserService userService;
    private final MatchService matchService;
    private final MultiplierServiceImpl multiplierService;
    private final BetService betService;

    public PayoutUserWinListener() {
        this.userService = UserServiceImpl.INSTANCE;
        this.matchService = MatchServiceImpl.INSTANCE;
        this.multiplierService = MultiplierServiceImpl.INSTANCE;
        this.betService = BetServiceImpl.INSTANCE;
    }

    @Override
    public void update(String eventType, int matchId) throws ListenerException {
        try {
            final List<UserDto> userDtos = userService.findAll().orElseThrow(IllegalArgumentException::new);
            for (UserDto user : userDtos) {
                if (userService.isUserWinner(user.getName(), matchId)) {
                    final Result result = matchService.findResultTypeById(matchId);
                    final int multiplierId = multiplierService.findIdByMatchIdAndResult(matchId, result);
                    final BigDecimal winMoneyAmount = userService.calculateExpectedWin(user.getName(), multiplierId);
                    userService.topUpBalance(user.getName(), winMoneyAmount);
                }
//            else {
//                final Result result = matchService.findResultTypeById(matchId);
//                final int multiplierId = multiplierService.findIdByMatchIdAndResult(matchId, result);
//                userService.topUpBalance(
//                        "admin",
//                        betService.findBetMoneyByUserIdAndMultiplierId(userService.findUserIdByUserName(user.getName()), multiplierId));
//            }
            }
        } catch (ServiceException e) {
            throw new ListenerException(e.getMessage(), e.getCause());
        }
    }
}
