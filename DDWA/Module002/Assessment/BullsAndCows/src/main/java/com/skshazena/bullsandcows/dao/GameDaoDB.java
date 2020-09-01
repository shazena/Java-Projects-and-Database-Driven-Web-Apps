package com.skshazena.bullsandcows.dao;

import com.skshazena.bullsandcows.dao.RoundDaoDB.RoundMapper;
import com.skshazena.bullsandcows.dto.Game;
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
public class GameDaoDB implements GameDao {

    private final JdbcTemplate jdbc;

    @Autowired
    public GameDaoDB(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    //TODO SEE WHICH ONE OF THESE NEED TO BE TRANSACTIONAL
    @Override
    @Transactional
    public Game getGameById(int gameId) {
        try {
            final String SELECT_GAME_BY_ID = "SELECT * FROM Game "
                    + "WHERE GameId = ?";
            Game game = jdbc.queryForObject(SELECT_GAME_BY_ID, new GameMapper(), gameId);
            return addRoundsToGame(game);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Game> getAllGames() {
        final String SELECT_ALL_GAMES = "SELECT * FROM Game";
        List<Game> listOfAllGames = jdbc.query(SELECT_ALL_GAMES, new GameMapper());
        for (Game game : listOfAllGames) {
            addRoundsToGame(game);
        }
        return listOfAllGames;
    }

    @Override
    @Transactional
    public Game addGame(Game game) {
        final String ADD_GAME = "INSERT INTO Game (Answer) VALUES (?)";
        jdbc.update(ADD_GAME,
                game.getAnswer());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setGameId(newId);
        game.setStatus(false);
        return game;
    }

    @Override
    @Transactional
    public Game updateGame(Game game) {
        final String UPDATE_GAME = "UPDATE Game SET "
                + "Answer = ?, "
                + "Status = ? "
                + "WHERE GameId = ?";
        jdbc.update(UPDATE_GAME,
                game.getAnswer(),
                game.isStatus(),
                game.getGameId());

        final String SELECT_GAME_BY_ID = "SELECT * FROM Game "
                + "WHERE GameId = ?";

        return jdbc.queryForObject(SELECT_GAME_BY_ID, new GameMapper(), game.getGameId());

    }

    @Override
    @Transactional
    public Game deleteGameById(int gameId) {
        //TODO note that this return does not have the Rounds inside the Game object.

        final String DELETE_ROUNDS_BY_GAMEID = "DELETE FROM Round "
                + "WHERE GameId = ?";
        jdbc.update(DELETE_ROUNDS_BY_GAMEID, gameId);

        final String SELECT_GAME_BY_ID = "SELECT * FROM Game "
                + "WHERE GameId = ?";
        Game gameToDelete = jdbc.queryForObject(SELECT_GAME_BY_ID, new GameMapper(), gameId);

        final String DELETE_GAME_BY_ID = "DELETE FROM Game "
                + "WHERE GameId = ?";
        jdbc.update(DELETE_GAME_BY_ID, gameId);

        return gameToDelete;

    }

    private Game addRoundsToGame(Game game) {
        final String SELECT_ALL_ROUNDS_BY_GAMEID = "SELECT * FROM Round WHERE GameId = ?";
        List<Round> listOfRounds = jdbc.query(SELECT_ALL_ROUNDS_BY_GAMEID, new RoundMapper(), game.getGameId());
        game.setListOfRounds(listOfRounds);
        return game;
    }

    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setGameId(rs.getInt("GameId"));
            game.setAnswer(rs.getString("Answer"));
            game.setStatus(rs.getBoolean("Status"));
            return game;
        }
    }
}
