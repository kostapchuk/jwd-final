package com.epam.jwd_final.web.dao;

import com.epam.jwd_final.web.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TeamDao {

    private static final Logger LOGGER = LogManager.getLogger(TeamDao.class);

    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    private static final String FIND_TEAM_BY_ID_SQL = "select name from team where id = ?";
    private static final String FIND_TEAM_BY_NAME_SQL = "select id from team where name = ?";

    public Optional<String> findTeamById(int id) {  // TODO: redo method
        final Connection connection = ConnectionPool.getInstance().retrieveConnection();
        try {
            final PreparedStatement preparedStatement =
                    connection.prepareStatement(FIND_TEAM_BY_ID_SQL);
            preparedStatement.setInt(1, id);
            final ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            if (rs.isFirst()) {
                return Optional.of(rs.getString(NAME_COLUMN));
            }
        } catch (SQLException e) {
            LOGGER.info("Something went wrong while finding team by id: " + id);
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return Optional.empty();
    }

    public Optional<Integer> findIdByName(String teamName) {  // TODO: redo method
        final Connection connection = ConnectionPool.getInstance().retrieveConnection();
        try {
            final PreparedStatement preparedStatement =
                    connection.prepareStatement(FIND_TEAM_BY_NAME_SQL);
            preparedStatement.setString(1, teamName);
            final ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            if (rs.isFirst()) {
                return Optional.of(rs.getInt(ID_COLUMN));
            }
        } catch (SQLException e) {
            LOGGER.info("Something went wrong while finding team by name: " + teamName);
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return Optional.empty();
    }
}
