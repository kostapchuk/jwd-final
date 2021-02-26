package com.epam.jwd_final.web.command;

import javax.servlet.http.HttpSession;

public interface RequestContext {

    void setAttribute(String name, Object obj);

    Object getAttribute(String name);

    Object getParameter(String name);

    void invalidateSession();

    void setSessionAttribute(String name, Object obj);

    String getStringParameter(String name);

    Integer getIntParameter(String name);

    Object getSessionAttribute(String name);

    String getStringSessionAttribute(String name);

    Integer getIntSessionAttribute(String name);

    HttpSession getSession();
}
