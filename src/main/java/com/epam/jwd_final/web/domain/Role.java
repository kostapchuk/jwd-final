package com.epam.jwd_final.web.domain;

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
            default:
                role = Role.CLIENT;
                break;
            case (1):
                role = Role.ADMIN;
                break;
            case (2):
                role = Role.BOOKMAKER;
                break;
        }
        return role;
    }
}
