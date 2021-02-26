package com.epam.jwd_final.web.domain;

public class Team extends AbstractEntity {

    private static final long serialVersionUID = -235935272697117924L;

    private final String name;

    public Team(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
