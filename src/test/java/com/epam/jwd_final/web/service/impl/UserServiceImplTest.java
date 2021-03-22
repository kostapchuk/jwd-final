package com.epam.jwd_final.web.service.impl;

import com.epam.jwd_final.web.dao.UserDao;
import com.epam.jwd_final.web.domain.Role;
import com.epam.jwd_final.web.domain.User;
import com.epam.jwd_final.web.exception.DaoException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {

    @InjectMocks
    private final UserService userService = UserServiceImpl.INSTANCE;

    @Mock
    private UserDao userDao;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveTest() throws ServiceException, DaoException {
        user = new User(2, "Kirill", "123qwe123", new BigDecimal("110.90"), Role.ADMIN);

//        UserDao userDao = mock(UserDao.class);

        when(userDao.save(user)).thenReturn(true);

        boolean actual = userService.save(user);

        verify(userDao, times(1)).save(user);
        assertTrue(actual);
    }

    @Test
    void increaseBalanceByIdTest() throws ServiceException, DaoException {
        user = new User(2, "Kirill", "123qwe123", new BigDecimal("110.90"), Role.ADMIN);

//        when(userDao.findOneById(2)).thenReturn(Optional.of(user));
//
//        userService.increaseBalance(2, new BigDecimal("0.10"));
//        verify(userDao, times(1)).findOneById(2);
    }


    @Test
    void findBalanceByIdTest_ShouldPass() throws ServiceException {
        when(userService.findBalanceById(2)).thenReturn(user.getBalance());

        final BigDecimal expected = new BigDecimal("110.90");
        final BigDecimal actual = userService.findBalanceById(2);

        assertEquals(expected, actual);
    }

    @Test()
    void findBalanceByIdTest_ShouldThrowException() throws ServiceException {
        when(userService.findBalanceById(3)).thenThrow(new ServiceException());

        assertThrows(ServiceException.class, () -> userService.findBalanceById(3));
    }
}