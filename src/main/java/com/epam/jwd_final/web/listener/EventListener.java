package com.epam.jwd_final.web.listener;

import com.epam.jwd_final.web.exception.ListenerException;

public interface EventListener {

    void update(String eventType, int anyId) throws ListenerException;
}
