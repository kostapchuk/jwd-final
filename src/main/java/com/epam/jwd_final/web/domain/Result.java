package com.epam.jwd_final.web.domain;

public enum Result implements Entity {

    FIRST_TEAM(1),
    SECOND_TEAM(2),
    DRAW(3);

    private final Integer id;

    Result(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public static Result resolveResultById(int id) {
        Result result;
        switch (id) {
            case (1):
                result = Result.FIRST_TEAM;
                break;
            case (2):
                result = Result.SECOND_TEAM;
                break;
            case (3):
                result = Result.DRAW;
                break;
            default:
                result = null;
                break;
        }
        return result;
    }
}
