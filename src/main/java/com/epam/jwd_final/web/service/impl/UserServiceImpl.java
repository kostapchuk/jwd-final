package com.epam.jwd_final.web.service.impl;

import com.epam.jwd_final.web.dao.impl.BetDaoImpl;
import com.epam.jwd_final.web.dao.BetDao;
import com.epam.jwd_final.web.dao.impl.MatchDaoImpl;
import com.epam.jwd_final.web.dao.MatchDao;
import com.epam.jwd_final.web.dao.MultiplierDao;
import com.epam.jwd_final.web.dao.impl.MultiplierDaoImpl;
import com.epam.jwd_final.web.dao.UserDao;
import com.epam.jwd_final.web.dao.impl.UserDaoImpl;
import com.epam.jwd_final.web.domain.Bet;
import com.epam.jwd_final.web.domain.Result;
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

    private final UserDao userDao;
    private final BetDao betDao;
    private final MatchDao matchDao;
    private final MultiplierDao multiplierDao;

    private static final String DUMMY_PASSWORD = "defaultPwd";
    private static final String HASHED_DUMMY_PASSWORD = BCrypt.hashpw(DUMMY_PASSWORD, BCrypt.gensalt());

    UserServiceImpl() {
        this.userDao = new UserDaoImpl();
        this.betDao = new BetDaoImpl();
        this.matchDao = new MatchDaoImpl();
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
            return save(new User(
                    name,
                    BCrypt.hashpw(password, BCrypt.gensalt())
            ));
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void updateRole(int id) throws ServiceException {
        try {
            userDao.updateRole(
                    userDao.findOneById(id)
                            .orElseThrow(ServiceException::new)
            );
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void rollbackRole(int id) throws ServiceException {
        try {
            userDao.rollbackRole(
                    userDao.findOneById(id)
                            .orElseThrow(ServiceException::new)
            );
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public int findUserIdByUserName(String userName) throws ServiceException {
        try {
            return userDao.findOneByName(userName)
                    .orElseThrow(ServiceException::new)
                    .getId();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void topUpBalance(String userName, BigDecimal amount) throws ServiceException {
        final BigDecimal previousBalance;
        try {
            previousBalance = userDao.findOneByName(userName)
                    .orElseThrow(ServiceException::new)
                    .getBalance();
            final BigDecimal newBalance = previousBalance.add(amount);
            userDao.updateBalance(userName, newBalance);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void withdrawFromBalance(String userName, BigDecimal amount) throws ServiceException {
        try {
            final BigDecimal previousBalance = userDao.findOneByName(userName)
                    .orElseThrow(ServiceException::new)
                    .getBalance();
            final BigDecimal newBalance = previousBalance.subtract(amount);
            if (newBalance.compareTo(BigDecimal.ZERO) >= 0) {
                userDao.updateBalance(userName, newBalance);
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
    public BigDecimal calculateExpectedWin(int id, int multiplierId) throws ServiceException {
        try {
            final Bet bet = betDao.findOneByUserIdByMultiplierId(id, multiplierId).orElseThrow(ServiceException::new);
            final BigDecimal betMoney = bet.getBetMoney();
            return multiplierDao.findOneById(bet.getMultiplierId()).orElseThrow(ServiceException::new).getCoefficient().multiply(betMoney);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean isUserWinner(String userName, int matchId) throws ServiceException {
        try {
            final int userId = userDao.findOneByName(userName).orElseThrow(ServiceException::new).getId();
            final Result actualResultType = matchDao.findOneById(matchId).orElseThrow(ServiceException::new).getResultType();
            final int multiplierId = multiplierDao.findOneByMatchIdByResultId(matchId, actualResultType.getId())
                    .orElseThrow(ServiceException::new)
                    .getId(); // TODO: redo
            final Optional<Bet> optionalBet = betDao.findOneByUserIdByMultiplierId(userId, multiplierId);
            if (!optionalBet.isPresent()) {
                return false;
            }
            final Result userResultType = multiplierDao.findOneById(optionalBet.get().getMultiplierId()).orElseThrow(ServiceException::new).getResult();
            return userResultType.equals(actualResultType);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public String findNameById(int userId) throws ServiceException {
        try {
            return userDao.findOneById(userId).orElseThrow(ServiceException::new).getName();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    private UserDto convertToDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getRole().name(), user.getBalance());
    }
}
