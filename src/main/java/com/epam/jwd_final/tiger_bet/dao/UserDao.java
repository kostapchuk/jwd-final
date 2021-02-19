package com.epam.jwd_final.tiger_bet.dao;

import com.epam.jwd_final.tiger_bet.domain.Role;
import com.epam.jwd_final.tiger_bet.domain.User;
import com.epam.jwd_final.tiger_bet.domain.UserDto;
import com.epam.jwd_final.tiger_bet.mapper.ModelMapper;
import com.epam.jwd_final.tiger_bet.mapper.impl.UserModelMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserDao extends AbstractDao<User> {

    private static final String CHANGE_ROLE_SQL =
            "update user set role = ? where name = ?";

    private static final String FIND_BY_NAME_SQL =
            "select id, name, password, balance, role from user where name = ?";

    private static final String SAVE_USER_SQL =
            "insert into user (name, password) values (?, ?)";
    private static final String FIND_ALL_SQL = "select id, name, password, balance, role from user";

    public Optional<List<User>> findAll() {
        return querySelect(FIND_ALL_SQL, Collections.emptyList());
    }

    public Optional<User> findByName(String name) {
        return querySelectForSingleResult(FIND_BY_NAME_SQL, Collections.singletonList(name));
    }

    public boolean save(User user) {
        return queryUpdate(SAVE_USER_SQL,
                new ArrayList<>(Arrays.asList(user.getName(), user.getPassword())));
    }

    public boolean updateRole(User user) {
        List<Object> params = new ArrayList<>();
        if (Role.CLIENT.equals(user.getRole())) {
            params.add(Role.BOOKMAKER.getId());
        } else if (Role.BOOKMAKER.equals(user.getRole())) {
            params.add(Role.ADMIN.getId());
        } else {
            return false;
        }
        params.add(user.getName());
        return queryUpdate(CHANGE_ROLE_SQL, params);
    }

    public boolean rollbackRole(User user) {
        List<Object> params = new ArrayList<>();
        if (Role.ADMIN.equals(user.getRole())) {
            params.add(Role.BOOKMAKER.getId());
        } else if (Role.BOOKMAKER.equals(user.getRole())) {
            params.add(Role.CLIENT.getId());
        } else {
            return false;
        }
        params.add(user.getName());
        return queryUpdate(CHANGE_ROLE_SQL, params);
    }

    @Override
    protected ModelMapper<User> retrieveModelMapper() {
        return new UserModelMapper();
    }
}
