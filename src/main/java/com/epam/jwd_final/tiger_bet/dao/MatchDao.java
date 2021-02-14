package com.epam.jwd_final.tiger_bet.dao;

import com.epam.jwd_final.tiger_bet.connection.ConnectionPool;
import com.epam.jwd_final.tiger_bet.domain.Match;
import com.epam.jwd_final.tiger_bet.domain.Role;
import com.epam.jwd_final.tiger_bet.domain.Sport;
import com.epam.jwd_final.tiger_bet.domain.Status;
import com.epam.jwd_final.tiger_bet.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatchDao {

    public static final String FIRST_TEAM_ID_COLUMN = "first_team_id";
    public static final String SECOND_TEAM_ID_COLUMN = "second_team_id";
    public static final String STATUS_ID_COLUMN = "status_id";
    public static final String START_COLUMN = "start";
    private final TeamDao teamDao = new TeamDao();

    private static final Logger LOGGER = LogManager.getLogger(MatchDao.class);

    private static final String FIND_BY_STATUS_SQL =
            "select sport_type_id, start, first_team_id, second_team_id, status_id from `match` where status_id = ?";
    public static final String SPORT_TYPE_ID_COLUMN = "sport_type_id";

    public Optional<List<Match>> findAllMatchesByStatus(Status status) {
        List<Match> matches = new ArrayList<>();
        try {
            final PreparedStatement preparedStatement =
                    ConnectionPool.getInstance().retrieveConnection().prepareStatement(FIND_BY_STATUS_SQL);
            preparedStatement.setInt(1, status.getId());
            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                matches.add(convertToMatch(rs));
            }
            if (matches.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(matches);
            }
        } catch (SQLException e) {
            LOGGER.info("Something went wrong while finding all matches by status: " + status.name());
        }
        return Optional.empty();
    }

    private Match convertToMatch(ResultSet rs) throws SQLException {
        final int sportTypeId = rs.getInt(SPORT_TYPE_ID_COLUMN);
        final LocalDateTime startTime = rs.getTimestamp(START_COLUMN).toLocalDateTime();
        final int firstTeamId = rs.getInt(FIRST_TEAM_ID_COLUMN);
        final int secondTeamId = rs.getInt(SECOND_TEAM_ID_COLUMN);
        final int statusId = rs.getInt(STATUS_ID_COLUMN);
        return new Match(
                Sport.resolveSportById(sportTypeId),
                startTime,
                teamDao.findTeamById(firstTeamId).orElse("No team"),
                teamDao.findTeamById(secondTeamId).orElse("No team"),
                Status.resolveStatusById(statusId)
        );

    }
}
