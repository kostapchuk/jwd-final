package com.epam.jwd_final.tiger_bet.domain;

public enum Role implements Entity {

    ADMIN(1),
    BOOKMAKER(2),
    CLIENT(3);

    private final Integer id;

    Role(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public static Role resolveRoleById(int id) {
        Role role;
        switch (id) {
            case (3):
                role = Role.CLIENT;
                break;
            case (2):
                role = Role.BOOKMAKER;
                break;
            case (1):
                role = Role.ADMIN;
                break;
            default:
                throw new IllegalArgumentException("Cannot resolve role");
                // TODO log or throw UnknownEntityException
        }
        return role;
    }
}
