package com.epam.jwd_final.web.dao;

import com.epam.jwd_final.web.domain.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<List<User>> findAll();

    Optional<User> findOneByName(String name);

    Optional<User> findOneById(int id);

    boolean save(User user);

    void updateRole(User user);

    void rollbackRole(User user);

    void updateBalance(String userName, BigDecimal balance);
}
