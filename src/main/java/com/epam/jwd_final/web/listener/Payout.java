package com.epam.jwd_final.web.listener;

import com.epam.jwd_final.web.exception.ListenerException;

public class Payout {

    private static final String WIN_USER_EVENT = "winUser";
    private static final String CANCEL_MATCH_EVENT = "cancelMatch";
    EventManager events;

    public Payout() {
        this.events = new EventManager(WIN_USER_EVENT, CANCEL_MATCH_EVENT);
    }

    public void payoutUserWin(int matchId) throws ListenerException {
        events.notify(WIN_USER_EVENT, matchId);
    }

    public void payoutCancelMatch(int matchId) throws ListenerException {
        events.notify(CANCEL_MATCH_EVENT, matchId);
    }
}
