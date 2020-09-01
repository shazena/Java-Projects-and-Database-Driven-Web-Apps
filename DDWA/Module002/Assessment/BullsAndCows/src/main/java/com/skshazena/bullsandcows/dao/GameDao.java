package com.skshazena.bullsandcows.dao;

import com.skshazena.bullsandcows.dto.Game;
import java.util.List;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Aug 19, 2020
 */
public interface GameDao {

    Game getGameById(int gameId);

    List<Game> getAllGames();

    Game addGame(Game game);

    Game updateGame(Game game);

    Game deleteGameById(int gameId);

}
