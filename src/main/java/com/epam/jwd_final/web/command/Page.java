package com.epam.jwd_final.web.command;

public enum Page {

    BETS("/WEB-INF/jsp/bets.jsp"),
    BOOKMAKER("/WEB-INF/jsp/bookmaker.jsp"),
    DEPOSIT("/WEB-INF/jsp/deposit.jsp"),
    WITHDRAW("/WEB-INF/jsp/withdraw.jsp"),
    MATCHES("/WEB-INF/jsp/matches.jsp"),
    USERS("/WEB-INF/jsp/users.jsp");

    private final String link;

    Page(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}
