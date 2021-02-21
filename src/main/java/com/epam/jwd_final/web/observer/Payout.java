package com.epam.jwd_final.web.observer;

import java.io.File;
import java.math.BigDecimal;

public class Payout {

    public EventManager events;
    private File file;

    public Payout() {
        this.events = new EventManager("payout");
    }

    public void payoutMoney(int matchId) {
        events.notify("payout", matchId);
//        TODO: userService.topUpBalance(userName, amount);
    }
}
