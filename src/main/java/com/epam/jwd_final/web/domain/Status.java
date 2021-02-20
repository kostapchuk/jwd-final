package com.epam.jwd_final.web.domain;

public enum Status implements Entity {
    PLANNED(1),
    IN_PROGRESS(2),
    FINISHED(3),
    CANCELED(4);

    private final Integer id;

    Status(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public static Status resolveStatusById(int id) {
        Status status;
        switch (id) {
            case (1):
                status = Status.PLANNED;
                break;
            case (2):
                status = Status.IN_PROGRESS;
                break;
            case (3):
                status = Status.FINISHED;
                break;
            case (4):
                status = Status.CANCELED;
                break;
            default:
                throw new IllegalArgumentException("Cannot resolve status");
                // TODO log or throw UnknownEntityException
        }
        return status;
    }
}
