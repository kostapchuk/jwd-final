package com.epam.jwd_final.tiger_bet.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public Object getAttribute(String name) {
        return request.getAttribute(name);
    }

    @Override
    public Object getParameter(String name) {
        return request.getParameter(name);
    }

    @Override
    public void invalidateSession() {
        final HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    @Override
    public void setSessionAttribute(String name, Object obj) {
        final HttpSession session = request.getSession(true);
        session.setAttribute(name, obj);
    }

    @Override
    public HttpSession getSession() {
        return request.getSession(false);
    }

    public static RequestContext of(HttpServletRequest request) {
        return new WrappingRequestContext(request);
    }
}
