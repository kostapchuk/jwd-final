package com.epam.jwd_final.tiger_bet.domain;

import java.time.LocalDateTime;

public class Match extends AbstractEntity {

    private static final long serialVersionUID = -1407601691624011922L;

    private final Sport sportType;
    private final LocalDateTime start;
    private final String firstTeam;
    private final String secondTeam;

    public Match(Integer id, Sport sportType, LocalDateTime start, String firstTeam, String secondTeam) {
        super(id);
        this.sportType = sportType;
        this.start = start;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
    }

    public Sport getSportType() {
        return sportType;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public String getFirstTeam() {
        return firstTeam;
    }

    public String getSecondTeam() {
        return secondTeam;
    }
}
