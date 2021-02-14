package com.epam.jwd_final.tiger_bet.service.impl;

import com.epam.jwd_final.tiger_bet.dao.UserDao;
import com.epam.jwd_final.tiger_bet.domain.User;
import com.epam.jwd_final.tiger_bet.domain.UserDto;
import com.epam.jwd_final.tiger_bet.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private static final String DUMMY_PASSWORD = "defaultPwd";
    private static final String HASHED_DUMMY_PASSWORD = BCrypt.hashpw(DUMMY_PASSWORD, BCrypt.gensalt());

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<UserDto> login(String name, String password) {
        final Optional<User> user = userDao.findByName(name);
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

    public boolean save(User user) {
        return userDao.save(user);
    }

    public boolean signup(String name, String password) {
        return save(new User(
                name,
                BCrypt.hashpw(password, BCrypt.gensalt())
        ));
    }

    private UserDto convertToDto(User user) {
        return new UserDto(user.getName());
    }
}
