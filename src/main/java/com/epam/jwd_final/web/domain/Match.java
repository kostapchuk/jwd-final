package com.epam.jwd_final.web.domain;

import java.time.LocalDateTime;

public class Match extends AbstractEntity {

    private static final long serialVersionUID = -1407601691624011922L;

    private final LocalDateTime start;
    private final String firstTeam;
    private final String secondTeam;
    private final Result resultType;

    public Match(Integer id, LocalDateTime start, String firstTeam, String secondTeam, Result resultType) {
        super(id);
        this.start = start;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.resultType = resultType;
    }

    public Match(LocalDateTime start, String firstTeam, String secondTeam) {
        this(null, start, firstTeam, secondTeam, null);
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

    public Result getResultType() {
        return resultType;
    }
}
