package com.epam.jwd_final.tiger_bet.dao;

import com.epam.jwd_final.tiger_bet.connection.ConnectionPool;
import com.epam.jwd_final.tiger_bet.domain.Entity;
import com.epam.jwd_final.tiger_bet.mapper.Mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDAO<T extends Entity> implements GeneralDAO<T> {

    @Override
    public Optional<T> queryForSingleResult(String querySQL, List<Object> params) {
        List<T> result = query(querySQL, params);
        if (result.size() == 1) {
            T value = result.get(0);
            return Optional.of(value);
        }
        return Optional.empty();
    }

    @Override
    public List<T> query(String querySQL, List<Object> params) {
        List<T> objects = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = prepareStatementForDeleteOrSelectQuery(querySQL, params);
            ResultSet resultSet = preparedStatement.executeQuery();
            Mapper<T> mapper = retrieveMapper();
            while (resultSet.next()) {
                T item = mapper.mapFrom(resultSet);
                objects.add(item);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException();
        }

        return objects;
    }

    private PreparedStatement prepareStatementForDeleteOrSelectQuery(String querySQL, List<Object> parameters)
            throws SQLException {
        PreparedStatement preparedStatement =
                ConnectionPool.getInstance().retrieveConnection().prepareStatement(querySQL);
        int length = parameters.size();
        for (int i = 0; i < length; i++) {
            preparedStatement.setString(i + 1, String.valueOf(parameters.get(i)));
        }
        return preparedStatement;
    }

    protected abstract Mapper<T> retrieveMapper();
}
