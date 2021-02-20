package com.epam.jwd_final.web.domain;

import java.time.LocalDateTime;

public class Match extends AbstractEntity {

    private static final long serialVersionUID = -1407601691624011922L;

    private final Sport sportType;
    private final LocalDateTime start;
    private final String firstTeam;
    private final String secondTeam;
    private final Status status;
    private final Result resultType;

    public Match(Integer id, Sport sportType, LocalDateTime start,
                 String firstTeam, String secondTeam, Status status,
                 Result resultType) {
        super(id);
        this.sportType = sportType;
        this.start = start;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.status = status;
        this.resultType = resultType;
    }

    public Match(Sport sportType, LocalDateTime start, String firstTeam,
                 String secondTeam, Status status, Result resultType) {
        this(null, sportType, start, firstTeam, secondTeam, status, resultType);
    }

    public Match(Sport sportType, LocalDateTime start, String firstTeam, String secondTeam) {
        this(null, sportType, start, firstTeam, secondTeam, null, null);
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

    public Status getStatus() {
        return status;
    }

    public Result getResultType() {
        return resultType;
    }
}
