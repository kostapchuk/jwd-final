package com.epam.jwd_final.tiger_bet.command;

import com.epam.jwd_final.tiger_bet.command.context.RequestContext;
import com.epam.jwd_final.tiger_bet.command.context.ResponseContext;

public enum LoginCommand implements Command {

    INSTANCE;

    @Override
    public ResponseContext execute(RequestContext req) {
        return null;
    }
}
