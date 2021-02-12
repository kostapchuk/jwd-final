package com.epam.jwd_final.tiger_bet.command;

import com.epam.jwd_final.tiger_bet.command.context.ResponseContext;

public final class ErrorResponse implements ResponseContext {

    @Override
    public String getPage() {
        return "/WEB-INF/jsp/error.jsp";
    }

    @Override
    public boolean isRedirect() {
        return false;
    }
}
