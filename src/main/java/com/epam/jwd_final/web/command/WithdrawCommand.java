package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.command.page.ShowWithdrawPage;
import com.epam.jwd_final.web.dao.UserDao;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.math.BigDecimal;

public enum WithdrawCommand implements Command {

    INSTANCE;

    private final UserService userService;

    WithdrawCommand() {
        this.userService = new UserServiceImpl(new UserDao());
    }

    @Override
    public ResponseContext execute(RequestContext req) {
        final String userName = String.valueOf(req.getSession().getAttribute("userName"));
        final BigDecimal depositMoney = new BigDecimal(String.valueOf(req.getParameter("withdrawMoney")));
        userService.withdrawFromBalance(userName, depositMoney);
        return ShowWithdrawPage.INSTANCE.execute(req);
    }
}
