package com.epam.jwd_final.web.command;

public enum Page {

    BETS("/WEB-INF/jsp/bets.jsp"),
    BOOKMAKER("/WEB-INF/jsp/bookmaker.jsp"),
    DEPOSIT("/WEB-INF/jsp/deposit.jsp"),
    WITHDRAW("/WEB-INF/jsp/withdraw.jsp"),
    EVENTS("/WEB-INF/jsp/events.jsp"),
    ERROR("/WEB-INF/jsp/error.jsp"),
    USERS("/WEB-INF/jsp/users.jsp");

    private final String link;

    Page(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}
