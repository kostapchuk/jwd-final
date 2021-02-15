package com.epam.jwd_final.tiger_bet.service;

import com.epam.jwd_final.tiger_bet.domain.User;
import com.epam.jwd_final.tiger_bet.domain.UserDto;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> login(String name, String password);

    boolean save(User user);

    boolean signup(String name, String password);
}
