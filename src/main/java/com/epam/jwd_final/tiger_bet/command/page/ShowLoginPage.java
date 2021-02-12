package com.epam.jwd_final.tiger_bet.command.page;

import com.epam.jwd_final.tiger_bet.command.context.ResponseContext;

public class ShowLoginPage implements ResponseContext {
    @Override
    public String getPage() {
        return "/WEB-INF/jsp/login.jsp";
    }

    @Override
    public boolean isRedirect() {
        return false;
    }
}
