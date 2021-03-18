package com.epam.jwd_final.web.service.impl;

import com.epam.jwd_final.web.dao.UserDao;
import com.epam.jwd_final.web.domain.Role;
import com.epam.jwd_final.web.domain.User;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private final UserService userService = UserServiceImpl.INSTANCE;

    @Mock
    private UserDao userDao;

    private User user;


    @BeforeEach
    void setUp() {
//        user = new User(2, "Kirill", "123qwe123", new BigDecimal("110.90"), Role.ADMIN);
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void increaseBalanceByIdTest() throws ServiceException {
//        doNothing().when(userService).increaseBalance(2, new BigDecimal("0.10"));
//        when(userService.findBalanceById(2)).thenReturn(user.getBalance());
//        doNothing().when(userService).increaseBalance(2, new BigDecimal("0.10"));
//        when(userService).increaseBalance(2, new BigDecimal("0.10"));

//        userService.increaseBalance(2, new BigDecimal("0.10"));
//        final BigDecimal expected = new BigDecimal("111");
//        final BigDecimal actual = userService.findBalanceById(2);

//        assertEquals(expected, actual);
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