/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skshazena.bullsandcows.dao;

import com.skshazena.bullsandcows.TestApplicationConfiguration;
import com.skshazena.bullsandcows.dto.Game;
import com.skshazena.bullsandcows.dto.Round;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Shazena
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GameDaoDBTest {

    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    public GameDaoDBTest() {
    }

    @Before
    public void setUp() {

        List<Round> rounds = roundDao.getAllRounds();
        for (Round round : rounds) {
            roundDao.deleteRoundById(round.getRoundId());
        }

        List<Game> games = gameDao.getAllGames();
        for (Game game : games) {
            gameDao.deleteGameById(game.getGameId());
        }
    }

    /**
     * Test of getGameById method, of class GameDaoDB.
     */
    @Test
    public void testAddGetGameById() {
        Game game0 = new Game();
        game0.setAnswer("3456");

        Game addedGame = gameDao.addGame(game0);
        Game retrievedGame = gameDao.getGameById(addedGame.getGameId());

        assertEquals(addedGame, retrievedGame);
    }

    /**
     * Test of getAllGames method, of class GameDaoDB.
     */
    @Test
    public void testGetAllGames() {
        Game game0 = new Game();
        game0.setAnswer("3456");

        Game game1 = new Game();
        game1.setAnswer("1234");

        Game addedGame0 = gameDao.addGame(game0);
        Game addedGame1 = gameDao.addGame(game1);

        List<Game> listOfAllGames = gameDao.getAllGames();

        assertEquals(listOfAllGames.size(), 2);
        assertTrue(listOfAllGames.contains(addedGame0));
        assertTrue(listOfAllGames.contains(addedGame1));
    }

    /**
     * Test of updateGame method, of class GameDaoDB.
     */
    @Test
    public void testUpdateGame() {
        Game game0 = new Game();
        game0.setAnswer("3456");

        Game addedGame = gameDao.addGame(game0);

        Game retrievedGame = gameDao.getGameById(addedGame.getGameId());

        assertEquals(addedGame, retrievedGame);

        game0.setAnswer("7890");

        Game updatedGame = gameDao.updateGame(game0);

        assertNotEquals(retrievedGame, updatedGame);

        retrievedGame = gameDao.getGameById(game0.getGameId());

        assertEquals(updatedGame, retrievedGame);

    }

    /**
     * Test of deleteGameById method, of class GameDaoDB.
     */
    @Test
    public void testDeleteGameById() {
        Game game = new Game();
        game.setAnswer("3456");
        Game addedGame = gameDao.addGame(game);

        Round round = new Round();
        round.setGameId(addedGame.getGameId());
        round.setGuess("1234");
        round.setTimeOfGuess(LocalDateTime.now().withNano(0));
        round.setResultOfGuess("e:0:p:2");
        roundDao.addRound(round);

        Game deletedGame = gameDao.deleteGameById(addedGame.getGameId());

        assertEquals(deletedGame, addedGame);

        List<Round> listOfAllRounds = roundDao.getAllRounds();

        assertEquals(listOfAllRounds.size(), 0);

    }

}
