package com.epam.jwd_final.web.service.impl;

import com.epam.jwd_final.web.dao.UserDao;
import com.epam.jwd_final.web.domain.User;
import com.epam.jwd_final.web.domain.UserDto;
import com.epam.jwd_final.web.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private static final String DUMMY_PASSWORD = "defaultPwd";
    private static final String HASHED_DUMMY_PASSWORD = BCrypt.hashpw(DUMMY_PASSWORD, BCrypt.gensalt());

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<List<UserDto>> findAll() {
        return userDao.findAll()
                .map(users ->
                        users.stream()
                                .map(this::convertToDto)
                                .collect(Collectors.toList()));
    }

    @Override
    public Optional<UserDto> login(String name, String password) {
        final Optional<User> user = userDao.findOneByName(name);
        if (!user.isPresent()) {
            BCrypt.checkpw(password, HASHED_DUMMY_PASSWORD);
            return Optional.empty();
        }
        final String realPassword = user.get().getPassword();
        if (BCrypt.checkpw(password, realPassword)) {
            return user.map(this::convertToDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean save(User user) {
        return userDao.save(user);
    }

    @Override
    public boolean signup(String name, String password) {
        return save(new User(
                name,
                BCrypt.hashpw(password, BCrypt.gensalt())
        ));
    }

    @Override
    public void updateRole(String userName) {
        userDao.updateRole(
                userDao.findOneByName(userName)
                        .orElseThrow(IllegalArgumentException::new)
        );
    }

    @Override
    public void rollbackRole(String userName) {
        userDao.rollbackRole(
                userDao.findOneByName(userName)
                        .orElseThrow(IllegalArgumentException::new)
        );
    }

    @Override
    public int findUserIdByUserName(String userName) {
        return userDao.findOneByName(userName)
                .orElseThrow(IllegalArgumentException::new)
                .getId();
    }

    @Override
    public void topUpBalance(String userName, BigDecimal amount) {
        final BigDecimal previousBalance = userDao.findOneByName(userName)
                .orElseThrow(IllegalArgumentException::new)
                .getBalance();
        final BigDecimal newBalance = previousBalance.add(amount);
        userDao.updateBalance(userName, newBalance);
    }

    @Override
    public void withdrawFromBalance(String userName, BigDecimal amount) {
        final BigDecimal previousBalance = userDao.findOneByName(userName)
                .orElseThrow(IllegalArgumentException::new)
                .getBalance();
        final BigDecimal newBalance = previousBalance.subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) >= 0) {
            userDao.updateBalance(userName, newBalance);
        }
    }

    @Override
    public BigDecimal findBalanceById(int id) {
        return userDao.findOneById(id)
                .orElseThrow(IllegalArgumentException::new)
                .getBalance();
    }

    private UserDto convertToDto(User user) {
        return new UserDto(user.getName(), user.getRole().name(), user.getBalance());
    }
}
