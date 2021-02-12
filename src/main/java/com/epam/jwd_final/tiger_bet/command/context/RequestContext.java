package com.epam.jwd_final.tiger_bet.command.context;

public interface RequestContext {

    void setAttribute(String name, Object obj);

    String getParameter(String name);
}
