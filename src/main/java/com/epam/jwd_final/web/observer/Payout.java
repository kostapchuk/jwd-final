package com.epam.jwd_final.web.observer;

public class Payout {

    public EventManager events;

    public Payout() {
        this.events = new EventManager("payoutUserWin");
    }

    public void payoutUserWin(int matchId) {
        events.notify("payoutUserWin", matchId);
    }
}
