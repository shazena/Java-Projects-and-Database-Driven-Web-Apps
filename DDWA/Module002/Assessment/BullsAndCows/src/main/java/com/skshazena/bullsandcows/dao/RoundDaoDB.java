package com.skshazena.bullsandcows.dao;

import com.skshazena.bullsandcows.dto.Round;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Aug 19, 2020
 */
@Repository
public class RoundDaoDB implements RoundDao {

    private final JdbcTemplate jdbc;

    @Autowired
    public RoundDaoDB(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    //TODO SEE WHICH ONE OF THESE NEED TO BE TRANSACTIONAL
    @Override
    public Round getRoundById(int roundId) {
        try {
            final String SELECT_ROUND_BY_ID = "SELECT * FROM Round WHERE RoundId = ?";
            return jdbc.queryForObject(SELECT_ROUND_BY_ID, new RoundMapper(), roundId);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Round> getAllRounds() {
        final String SELECT_ALL_ROUNDS = "SELECT * FROM Round";
        return jdbc.query(SELECT_ALL_ROUNDS, new RoundMapper());
    }

    @Override
    public List<Round> getAllRoundsByGameId(int gameId) {
        final String SELECT_ALL_ROUNDS_BY_GAMEID = "SELECT * FROM Round WHERE GameId = ?";
        return jdbc.query(SELECT_ALL_ROUNDS_BY_GAMEID, new RoundMapper(), gameId);
    }

    @Override
    @Transactional
    public Round addRound(Round round) {
        final String INSERT_ROUND = "INSERT INTO Round"
                + "(GameId, Guess, TimeOfGuess, ResultOfGuess) "
                + "VALUES(?,?,?,?)";

        jdbc.update(INSERT_ROUND,
                round.getGameId(),
                round.getGuess(),
                round.getTimeOfGuess().withNano(0),
                round.getResultOfGuess());

        Integer newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setRoundId(newId);
        return round;
    }

    @Override
    @Transactional
    public Round updateRound(Round round) {
        final String UPDATE_ROUND = "UPDATE Round SET "
                + "GameId = ?,"
                + "Guess = ?,"
                + "TimeOfGuess = ?,"
                + "ResultOfGuess = ?"
                + "WHERE RoundId = ?";
        jdbc.update(UPDATE_ROUND,
                round.getGameId(),
                round.getGuess(),
                round.getTimeOfGuess().withNano(0),
                round.getResultOfGuess(),
                round.getRoundId());

        final String SELECT_UPDATED_ROUND = "SELECT * FROM Round "
                + "WHERE RoundId = ?";
        return jdbc.queryForObject(SELECT_UPDATED_ROUND, new RoundMapper(), round.getRoundId());

    }

    @Override
    @Transactional
    public Round deleteRoundById(int roundId) {
        try {
            final String SELECT_ROUND_BY_ID = "SELECT * FROM Round WHERE RoundId = ?";
            Round roundToDelete = jdbc.queryForObject(SELECT_ROUND_BY_ID, new RoundMapper(), roundId);

            final String DELETE_ROUND_BY_ID = "DELETE FROM Round "
                    + "WHERE GameId = ?";
            jdbc.update(DELETE_ROUND_BY_ID, roundId);

            return roundToDelete;
        } catch (DataAccessException e) {
            return null;
        }

    }

    public static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setRoundId(rs.getInt("RoundId"));
            round.setGameId(rs.getInt("GameId"));
            round.setGuess(rs.getString("Guess"));
            round.setTimeOfGuess(rs.getTimestamp("TimeOfGuess").toLocalDateTime());
            round.setResultOfGuess(rs.getString("ResultOfGuess"));
            return round;
        }
    }

}
