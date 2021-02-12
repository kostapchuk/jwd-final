package com.epam.jwd_final.tiger_bet.command;

import com.epam.jwd_final.tiger_bet.command.context.RequestContext;
import com.epam.jwd_final.tiger_bet.command.context.ResponseContext;
import com.epam.jwd_final.tiger_bet.service.impl.UserService;

public enum SignUpCommand implements Command {

    INSTANCE;

    private static final ResponseContext WELCOME_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/welcome.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) {
        final String name = req.getParameter("name");
        final String email = req.getParameter("email");
        final String password = req.getParameter("password");

        return UserService.INSTANCE.signUp(name, email, password) ? WELCOME_RESPONSE : new ErrorResponse();
    }
}
