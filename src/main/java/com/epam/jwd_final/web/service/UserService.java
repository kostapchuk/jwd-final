package com.epam.jwd_final.web.service;

import com.epam.jwd_final.web.domain.User;
import com.epam.jwd_final.web.domain.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<List<UserDto>> findAll();

    Optional<UserDto> login(String name, String password);

    boolean save(User user);

    boolean signup(String name, String password);

    boolean updateRole(String userName);

    boolean rollbackRole(String userName);

    int findUserIdByUserName(String userName);
}
