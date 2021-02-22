package com.epam.jwd_final.web.service.impl;

import com.epam.jwd_final.web.dao.impl.BetDao;
import com.epam.jwd_final.web.dao.impl.MatchDao;
import com.epam.jwd_final.web.dao.impl.MultiplierDao;
import com.epam.jwd_final.web.dao.UserDao;
import com.epam.jwd_final.web.dao.impl.UserDaoImpl;
import com.epam.jwd_final.web.domain.Bet;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.domain.Status;
import com.epam.jwd_final.web.domain.User;
import com.epam.jwd_final.web.domain.UserDto;
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
        this.betDao = new BetDao();
        this.matchDao = new MatchDao();
        this.multiplierDao = new MultiplierDao();
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

    public BigDecimal calculateExpectedWin(String name, int multiplierId) {
        final int userId = userDao.findOneByName(name).orElseThrow(IllegalArgumentException::new).getId();
        final Bet bet = betDao.findOneByUserIdByMultiplierId(userId, multiplierId).orElseThrow(IllegalArgumentException::new);
        final BigDecimal betMoney = bet.getBetMoney();
        return multiplierDao.findOneById(bet.getMultiplierId()).orElseThrow(IllegalArgumentException::new).getCoefficient().multiply(betMoney);
    }

    public boolean isUserWinner(String userName, int matchId) {
        final int userId = userDao.findOneByName(userName).orElseThrow(IllegalArgumentException::new).getId();
        final Result actualResultType = matchDao.findOneById(matchId).orElseThrow(IllegalArgumentException::new).getResultType();
        final int multiplierId = multiplierDao.findOneByMatchIdByResultId(matchId, actualResultType.getId())
                .orElseThrow(IllegalArgumentException::new)
                .getId(); // TODO: redo
        final Bet bet = betDao.findOneByUserIdByMultiplierId(userId, multiplierId).orElseThrow(IllegalArgumentException::new);
        final Status matchStatus = matchDao.findOneById(matchId).orElseThrow(IllegalArgumentException::new).getStatus();
        final Result userResultType = multiplierDao.findOneById(bet.getMultiplierId()).orElseThrow(IllegalArgumentException::new).getResult();
        return userResultType.equals(actualResultType) && matchStatus.equals(Status.FINISHED);
    }

    private UserDto convertToDto(User user) {
        return new UserDto(user.getName(), user.getRole().name(), user.getBalance());
    }
}
