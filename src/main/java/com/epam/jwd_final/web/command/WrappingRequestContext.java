package com.epam.jwd_final.web.command;

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
    public String getStringParameter(String name) {
        return String.valueOf(getParameter(name));
    }

    @Override
    public Integer getIntParameter(String name) {
        return Integer.parseInt(getStringParameter(name));
    }

    @Override
    public Object getSessionAttribute(String name) {
        return getSession().getAttribute(name);
    }

    @Override
    public String getStringSessionAttribute(String name) {
        return String.valueOf(getSessionAttribute(name));
    }

    @Override
    public Integer getIntSessionAttribute(String name) {
        return Integer.parseInt(getStringSessionAttribute(name));
    }

    @Override
    public HttpSession getSession() {
        return request.getSession(false);
    }

    public static RequestContext of(HttpServletRequest request) {
        return new WrappingRequestContext(request);
    }
}
