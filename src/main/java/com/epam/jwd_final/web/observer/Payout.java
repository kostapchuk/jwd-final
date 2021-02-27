package com.epam.jwd_final.web.observer;

import com.epam.jwd_final.web.exception.ListenerException;

public class Payout {

    public EventManager events;

    public Payout() {
        this.events = new EventManager("winUser", "cancelMatch");
    }

    public void payoutUserWin(int matchId) throws ListenerException {
        events.notify("winUser", matchId);
    }

    public void payoutCancelMatch(int matchId) throws ListenerException {
        events.notify("cancelMatch", matchId);
    }
}
