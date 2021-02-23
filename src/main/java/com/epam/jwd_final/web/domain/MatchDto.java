package com.epam.jwd_final.web.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MatchDto {

    private static final String DATE_TIME_FORMATTER = "dd-MM-yyyy HH:mm";

    private final Integer id;
    private final LocalDateTime start;
    private final String firstTeam;
    private final String secondTeam;
    private final Result resultType;

    public MatchDto(Integer id, LocalDateTime start, String firstTeam, String secondTeam, Result resultType) {
        this.id = id;
        this.start = start;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.resultType = resultType;
    }

    public Integer getId() {
        return id;
    }

    public String getStart() {
        return start.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER));
    }

    public String getFirstTeam() {
        return firstTeam;
    }

    public String getSecondTeam() {
        return secondTeam;
    }


    public String getResultType() {
        return resultType.name();
    }
}
