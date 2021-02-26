package com.epam.jwd_final.web.command.page;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.domain.UserDto;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.UserService;
import com.epam.jwd_final.web.service.impl.UserServiceImpl;

import java.util.Collections;
import java.util.List;

public enum ShowUsersPage implements Command {

    INSTANCE;

    private static final String USERS_PARAMETER = "users";

    private final UserService userService;

    ShowUsersPage() {
        this.userService = UserServiceImpl.INSTANCE;
    }

    private static final ResponseContext ALL_USERS_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/users.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final String userName = String.valueOf(req.getSession().getAttribute("userName"));
            req.setSessionAttribute("userBalance", userService.findBalanceById(userService.findUserIdByUserName(userName)));
            final List<UserDto> userDtos = userService.findAll().orElse(Collections.emptyList());
            req.setAttribute(USERS_PARAMETER, userDtos);
            return ALL_USERS_PAGE_RESPONSE;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}