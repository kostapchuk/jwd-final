package com.epam.jwd_final.tiger_bet.command.page;

import com.epam.jwd_final.tiger_bet.command.Command;
import com.epam.jwd_final.tiger_bet.command.RequestContext;
import com.epam.jwd_final.tiger_bet.command.ResponseContext;
import com.epam.jwd_final.tiger_bet.dao.UserDao;
import com.epam.jwd_final.tiger_bet.domain.UserDto;
import com.epam.jwd_final.tiger_bet.service.UserService;
import com.epam.jwd_final.tiger_bet.service.impl.UserServiceImpl;

import java.util.Collections;
import java.util.List;

public enum ShowAllUsersPage implements Command {

    INSTANCE;

    private static final String USERS_PARAMETER = "users";

    private final UserService userService;

    ShowAllUsersPage() {
        this.userService = new UserServiceImpl(new UserDao());
    }

    private static final ResponseContext ALL_USERS_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/allusers.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) {
        final List<UserDto> userDtos = userService.findAll().orElse(Collections.emptyList());
        req.setAttribute(USERS_PARAMETER, userDtos);
        return ALL_USERS_PAGE_RESPONSE;
    }
}
