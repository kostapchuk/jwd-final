package com.epam.jwd_final.web.dao;

import com.epam.jwd_final.web.connection.ConnectionPool;
import com.epam.jwd_final.web.domain.Entity;
import com.epam.jwd_final.web.exception.DaoException;
import com.epam.jwd_final.web.exception.ModelMapperException;
import com.epam.jwd_final.web.mapper.ModelMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends Entity> implements GeneralDao<T> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractDao.class);

    @Override
    public Optional<T> querySelectOne(String querySQL, List<Object> params) throws DaoException {
        List<T> result = querySelectAll(querySQL, params);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        if (result.size() == 1) {
            T value = result.get(0);
            return Optional.of(value);
        }
        return Optional.empty();
    }

    public boolean queryUpdate(String querySQL, List<Object> params) throws DaoException {
        try {
            PreparedStatement preparedStatement = makeStatementPrepared(querySQL, params);
            final int affectedRows = preparedStatement.executeUpdate();
            return affectedRows != 0;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<T> querySelectAll(String querySQL, List<Object> params) throws DaoException {
        try {
            List<T> objects = new ArrayList<>();
            PreparedStatement preparedStatement = makeStatementPrepared(querySQL, params);
            ResultSet resultSet = preparedStatement.executeQuery();
            ModelMapper<T> mapper = retrieveModelMapper();
            while (resultSet.next()) {
                T item = mapper.mapToEntity(resultSet);
                objects.add(item);
            }
            if (objects.isEmpty()) {
                return Collections.emptyList();
            }
            return objects;
        } catch (SQLException | ModelMapperException e) {
            throw new DaoException(e.getMessage(), e.getCause());
        }
    }

    PreparedStatement makeStatementPrepared(String querySQL, List<Object> parameters)
            throws SQLException {
        final Connection connection = ConnectionPool.getInstance().retrieveConnection();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(querySQL);
            int length = parameters.size();
            for (int i = 0; i < length; i++) {
                preparedStatement.setString(i + 1, String.valueOf(parameters.get(i)));
            }
            return preparedStatement;
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    protected abstract ModelMapper<T> retrieveModelMapper();
}
