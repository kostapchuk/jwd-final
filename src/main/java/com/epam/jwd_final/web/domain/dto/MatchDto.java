package com.epam.jwd_final.web.domain.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MatchDto {

    private static final String DATE_TIME_FORMATTER = "dd-MM-yyyy HH:mm";

    private final Integer id;
    private final LocalDateTime start;
    private final String firstTeam;
    private final String secondTeam;

    public MatchDto(Integer id, LocalDateTime start, String firstTeam, String secondTeam) {
        this.id = id;
        this.start = start;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
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

}
