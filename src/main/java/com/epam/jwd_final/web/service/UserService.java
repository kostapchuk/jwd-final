package com.epam.jwd_final.web.service;

import com.epam.jwd_final.web.domain.User;
import com.epam.jwd_final.web.domain.dto.UserDto;
import com.epam.jwd_final.web.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> findAll() throws ServiceException;

    Optional<UserDto> login(String name, String password) throws ServiceException;

    boolean save(User user) throws ServiceException;

    boolean signup(String name, String password) throws ServiceException;

    void updateRole(int id) throws ServiceException;

    void rollbackRole(int id) throws ServiceException;

    void increaseBalance(int id, BigDecimal amount) throws ServiceException;

    void decreaseBalance(int id, BigDecimal amount) throws ServiceException;

    BigDecimal findBalanceById(int id) throws ServiceException;

    BigDecimal calculateToReturn(int id, int multiplierId) throws ServiceException;
}
