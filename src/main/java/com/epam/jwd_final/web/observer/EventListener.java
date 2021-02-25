package com.epam.jwd_final.web.observer;

import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.exception.ListenerException;

public interface EventListener {

    void update(String eventType, RequestContext req) throws ListenerException;
}
