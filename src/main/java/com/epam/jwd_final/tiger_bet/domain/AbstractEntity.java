package com.epam.jwd_final.tiger_bet.domain;


public class AbstractEntity implements Entity {

    private static final long serialVersionUID = -7386103938895073542L;

    private final Integer id;

    public AbstractEntity(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

}
