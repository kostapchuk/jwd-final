package com.epam.jwd_final.web.dao;

import com.epam.jwd_final.web.domain.Role;
import com.epam.jwd_final.web.domain.User;
import com.epam.jwd_final.web.mapper.ModelMapper;
import com.epam.jwd_final.web.mapper.impl.UserModelMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserDao extends AbstractDao<User> {

    private static final String FIND_ALL_SQL =
            "select id, name, password, balance, role from user";

    private static final String FIND_BY_ID_SQL =
            "select id, name, password, balance, role from user where id = ?";

    private static final String FIND_BY_NAME_SQL =
            "select id, name, password, balance, role from user where name = ?";

    private static final String SAVE_USER_SQL =
            "insert into user (name, password) values (?, ?)";

    private static final String UPDATE_ROLE_SQL =
            "update user set role = ? where name = ?";

    private static final String UPDATE_BALANCE_SQL =
            "update user set balance = ? where name = ?";

    public Optional<List<User>> findAll() {
        return querySelectAll(
                FIND_ALL_SQL,
                Collections.emptyList()
        );
    }

    public Optional<User> findOneByName(String name) {
        return querySelectOne(
                FIND_BY_NAME_SQL,
                Collections.singletonList(name)
        );
    }

    public Optional<User> findOneById(int id) {
        return querySelectOne(
                FIND_BY_ID_SQL,
                Collections.singletonList(id)
        );
    }

    public boolean save(User user) {
        return queryUpdate(
                SAVE_USER_SQL,
                Arrays.asList(user.getName(), user.getPassword())
        );
    }

    public void updateRole(User user) {
        int newRoleId;
        switch (user.getRole()) {
            case CLIENT:
                newRoleId = Role.BOOKMAKER.getId();
                break;
            case ADMIN:
            case BOOKMAKER:
                newRoleId = Role.ADMIN.getId();
                break;
            default:
                newRoleId = Role.CLIENT.getId();
        }
        queryUpdate(
                UPDATE_ROLE_SQL,
                Arrays.asList(newRoleId, user.getName())
        );
    }

    public void rollbackRole(User user) {
        int newRoleId;
        switch (user.getRole()) {
            case ADMIN:
                newRoleId = Role.BOOKMAKER.getId();
                break;
            case BOOKMAKER:
            case CLIENT:
            default:
                newRoleId = Role.CLIENT.getId();
        }
        queryUpdate(
                UPDATE_ROLE_SQL,
                Arrays.asList(newRoleId, user.getName())
        );
    }

    public void updateBalance(String userName, BigDecimal balance) {
        queryUpdate(
                UPDATE_BALANCE_SQL,
                Arrays.asList(userName, balance)
        );
    }

    @Override
    protected ModelMapper<User> retrieveModelMapper() {
        return new UserModelMapper();
    }
}
