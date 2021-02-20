package com.epam.jwd_final.web.domain;

public enum Sport implements Entity {

    FOOTBALL(1);

    private final Integer id;

    Sport(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public static Sport resolveSportById(int id) {
        Sport sport;
        switch (id) {
            case 1:
                sport = Sport.FOOTBALL;
                break;
            default:
                // TODO log "no such sport"
                throw new IllegalArgumentException();
        }
        return sport;
    }
}
