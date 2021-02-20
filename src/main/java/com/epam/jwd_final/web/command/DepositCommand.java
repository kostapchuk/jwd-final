package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.command.page.ShowAllBetsPage;
import com.epam.jwd_final.web.dao.UserDao;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.math.BigDecimal;

public enum DepositCommand implements Command {

    INSTANCE;

    private final UserService userService;

    DepositCommand() {
        this.userService = new UserServiceImpl(new UserDao());
    }

    @Override
    public ResponseContext execute(RequestContext req) {
        final String userName = String.valueOf(req.getSession().getAttribute("userName"));
        final BigDecimal depositMoney = new BigDecimal(String.valueOf(req.getParameter("depositMoney")));
        userService.topUpBalance(userName, depositMoney);
        return ShowAllBetsPage.INSTANCE.execute(req);
    }
}
