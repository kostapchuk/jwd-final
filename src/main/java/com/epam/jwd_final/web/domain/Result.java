package com.epam.jwd_final.web.domain;

public enum Result implements Entity {

    FIRST_TEAM(1), // TODO
    SECOND_TEAM(2), // TODO
    DRAW(3), // TODO
    NO_RESULT(4); // TODO

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
            case (4):
                result = Result.NO_RESULT;
                break;
            default:
                throw new IllegalArgumentException("Cannot resolve result");
                // TODO log or throw UnknownEntityException
        }
        return result;
    }
}
