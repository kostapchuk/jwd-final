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

    private static final String CHANGE_ROLE_SQL =
            "update user set role = ? where name = ?";

    private static final String FIND_BY_NAME_SQL =
            "select id, name, password, balance, role from user where name = ?";

    private static final String FIND_BY_ID_SQL =
            "select id, name, password, balance, role from user where id = ?";

    private static final String SAVE_USER_SQL =
            "insert into user (name, password) values (?, ?)";
    private static final String FIND_ALL_SQL = "select id, name, password, balance, role from user";

    private static final String CHANGE_BALANCE_SQL =
            "update user set balance = ? where name = ?";

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

    public int findUserIdByUserName(String userName) {
        return querySelectForSingleResult(FIND_BY_NAME_SQL, Collections.singletonList(userName))
                .orElseThrow(IllegalArgumentException::new).getId();
    }

    public void topUpBalance(String userName, BigDecimal amount) {
        final Optional<User> user = querySelectForSingleResult(FIND_BY_NAME_SQL, Collections.singletonList(userName));
        final BigDecimal previousBalance = user.orElseThrow(IllegalArgumentException::new).getBalance();
        final BigDecimal newBalance = previousBalance.add(amount);
        List<Object> params = new ArrayList<>();
        params.add(newBalance);
        params.add(userName);
        queryUpdate(CHANGE_BALANCE_SQL, params);
    }

    public void withdrawFromBalance(String userName, BigDecimal amount) {
        final Optional<User> user = querySelectForSingleResult(FIND_BY_NAME_SQL, Collections.singletonList(userName));
        final BigDecimal actualBalance = user.orElseThrow(IllegalArgumentException::new).getBalance();
        final BigDecimal expectedBalance = actualBalance.subtract(amount);
        if (expectedBalance.compareTo(new BigDecimal("0.00")) != -1) {
            List<Object> params = new ArrayList<>();
            params.add(expectedBalance);
            params.add(userName);
            queryUpdate(CHANGE_BALANCE_SQL, params);
        } // TODO: else cannot withdraw money

    }

    public User retrieveById(int id) {
        return querySelectForSingleResult(FIND_BY_ID_SQL, Collections.singletonList(id)).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    protected ModelMapper<User> retrieveModelMapper() {
        return new UserModelMapper();
    }
}
