package com.epam.jwd_final.tiger_bet.dao;

import com.epam.jwd_final.tiger_bet.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TeamDao {

    private static final Logger LOGGER = LogManager.getLogger(TeamDao.class);

    public static final String NAME_COLUMN = "name";
    private static final String FIND_TEAM_BY_ID_SQL = "select name from team where id = ?";

    public Optional<String> findTeamById(int id) {
        try {
            final PreparedStatement preparedStatement =
                    ConnectionPool.getInstance().retrieveConnection().prepareStatement(FIND_TEAM_BY_ID_SQL);
            preparedStatement.setInt(1, id);
            final ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            if (rs.isFirst()) {
                return Optional.of(rs.getString(NAME_COLUMN));
            }
        } catch (SQLException e) {
            LOGGER.info("Something went wrong while finding team by id: " + id);
        }
        return Optional.empty();

    }
}
