package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.command.page.ShowAllBetsPage;
import com.epam.jwd_final.web.dao.UserDao;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.math.BigDecimal;

public enum DepositCommand implements Command {

    INSTANCE;

    private final UserService userService;

    DepositCommand() {
        this.userService = UserServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final String userName = String.valueOf(req.getSession().getAttribute("userName"));
            final BigDecimal depositMoney = new BigDecimal(String.valueOf(req.getParameter("depositMoney")));
            userService.topUpBalance(userName, depositMoney);
            final BigDecimal currentBalance = new BigDecimal(String.valueOf(req.getSession().getAttribute("userBalance")));
            req.setSessionAttribute("userBalance", currentBalance.add(depositMoney));
            return ShowAllBetsPage.INSTANCE.execute(req);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}
