package com.epam.jwd_final.web.service.impl;

import com.epam.jwd_final.web.dao.impl.BetDaoImpl;
import com.epam.jwd_final.web.dao.BetDao;
import com.epam.jwd_final.web.dao.MultiplierDao;
import com.epam.jwd_final.web.dao.impl.MultiplierDaoImpl;
import com.epam.jwd_final.web.dao.UserDao;
import com.epam.jwd_final.web.dao.impl.UserDaoImpl;
import com.epam.jwd_final.web.domain.Bet;
import com.epam.jwd_final.web.domain.Role;
import com.epam.jwd_final.web.domain.User;
import com.epam.jwd_final.web.domain.UserDto;
import com.epam.jwd_final.web.exception.DaoException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum UserServiceImpl implements UserService {

    INSTANCE;

    private static final int MIN_NAME_LENGTH = 4;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final UserDao userDao;
    private final BetDao betDao;
    private final MultiplierDao multiplierDao;

    private static final String DUMMY_PASSWORD = "defaultPwd";
    private static final String HASHED_DUMMY_PASSWORD = BCrypt.hashpw(DUMMY_PASSWORD, BCrypt.gensalt());

    UserServiceImpl() {
        this.userDao = new UserDaoImpl();
        this.betDao = new BetDaoImpl();
        this.multiplierDao = new MultiplierDaoImpl();
    }

    @Override
    public Optional<List<UserDto>> findAll() throws ServiceException {
        try {
            return userDao.findAll()
                    .map(users ->
                            users.stream()
                                    .map(this::convertToDto)
                                    .collect(Collectors.toList()));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<UserDto> login(String name, String password) throws ServiceException {
        try {
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
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean save(User user) throws ServiceException {
        try {
            return userDao.save(user);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean signup(String name, String password) throws ServiceException {
        try {
            if (name.length() >= MIN_NAME_LENGTH && password.length() >= MIN_PASSWORD_LENGTH) {
                return save(new User(
                        name,
                        BCrypt.hashpw(password, BCrypt.gensalt())
                ));
            }
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
        return false;
    }

    @Override
    public void updateRole(int id) throws ServiceException {
        try {
            final User user = userDao.findOneById(id).orElseThrow(ServiceException::new);
            final int newRoleId = user.getRole().getId() - 1;
            if (Role.CLIENT.getId() >= newRoleId && Role.ADMIN.getId() <= newRoleId) {
                userDao.updateRole(user.getId(), newRoleId);
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void rollbackRole(int id) throws ServiceException {
        try {
            final User user = userDao.findOneById(id).orElseThrow(ServiceException::new);
            final int newRoleId = user.getRole().getId() + 1;
            if (Role.CLIENT.getId() >= newRoleId && Role.ADMIN.getId() < newRoleId) {
                userDao.updateRole(user.getId(), newRoleId);
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void topUpBalance(int id, BigDecimal amount) throws ServiceException {
        try {
            final BigDecimal previousBalance = userDao.findOneById(id)
                    .orElseThrow(ServiceException::new)
                    .getBalance();
            final BigDecimal newBalance = previousBalance.add(amount);
            userDao.updateBalance(id, newBalance);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void withdrawFromBalance(int id, BigDecimal amount) throws ServiceException {
        try {
            final BigDecimal previousBalance = userDao.findOneById(id)
                    .orElseThrow(ServiceException::new)
                    .getBalance();
            final BigDecimal newBalance = previousBalance.subtract(amount);
            if (newBalance.compareTo(BigDecimal.ZERO) >= 0) {
                userDao.updateBalance(id, newBalance);
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public BigDecimal findBalanceById(int id) throws ServiceException {
        try {
            return userDao.findOneById(id)
                    .orElseThrow(ServiceException::new)
                    .getBalance();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public BigDecimal calculateToReturn(int id, int multiplierId) throws ServiceException {
        try {
            final Bet bet = betDao.findOneByUserIdByMultiplierId(id, multiplierId).orElseThrow(ServiceException::new);
            final BigDecimal betMoney = bet.getBetMoney();
            return multiplierDao.findOneById(bet.getMultiplierId()).orElseThrow(ServiceException::new).getCoefficient().multiply(betMoney);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }


    private UserDto convertToDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getRole().name(), user.getBalance());
    }
}
