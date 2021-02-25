package com.epam.jwd_final.web.observer;

import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.exception.ListenerException;

public class Payout {

    public EventManager events;

    public Payout() {
        this.events = new EventManager("payoutUserWin");
    }

    public void payoutUserWin(RequestContext req) throws ListenerException {
        events.notify("payoutUserWin", req);
    }
}
