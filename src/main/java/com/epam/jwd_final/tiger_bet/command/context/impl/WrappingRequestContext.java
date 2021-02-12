package com.epam.jwd_final.tiger_bet.command.context.impl;

import com.epam.jwd_final.tiger_bet.command.context.RequestContext;

import javax.servlet.http.HttpServletRequest;

public class WrappingRequestContext implements RequestContext {

    private final HttpServletRequest request;

    private WrappingRequestContext(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setAttribute(String name, Object obj) {
        request.setAttribute(name, obj);
    }

    @Override
    public String getParameter(String name) {
        return this.request.getParameter(name);
    }

    public static RequestContext of(HttpServletRequest request) {
        return new WrappingRequestContext(request);
    }
}
