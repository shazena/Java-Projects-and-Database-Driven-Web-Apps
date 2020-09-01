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
public class RoundDaoDBTest {

    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    public RoundDaoDBTest() {
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
     * Test of getRoundById method, of class RoundDaoDB.
     */
    @Test
    public void testAddGetRoundById() {
        Game game = new Game();
        game.setAnswer("3456");
        Game addedGame = gameDao.addGame(game);

        Round round = new Round();
        round.setGameId(addedGame.getGameId());
        round.setGuess("1234");
        round.setTimeOfGuess(LocalDateTime.now().withNano(0));
        round.setResultOfGuess("e:0:p:2");

        Round addedRound = roundDao.addRound(round);
        Round retrievedRound = roundDao.getRoundById(addedRound.getRoundId());

        assertEquals(addedRound, retrievedRound);

    }

    /**
     * Test of getAllRounds method, of class RoundDaoDB.
     */
    @Test
    public void testGetAllRounds() {
        Game game0 = new Game();
        game0.setAnswer("3456");
        Game addedGame0 = gameDao.addGame(game0);

        Game game1 = new Game();
        game1.setAnswer("5678");
        Game addedGame1 = gameDao.addGame(game1);

        Round round0 = new Round();
        round0.setGameId(addedGame0.getGameId());
        round0.setGuess("1234");
        round0.setTimeOfGuess(LocalDateTime.now().withNano(0));
        round0.setResultOfGuess("e:0:p:2");
        Round addedRound0 = roundDao.addRound(round0);

        Round round1 = new Round();
        round1.setGameId(addedGame1.getGameId());
        round1.setGuess("9870");
        round1.setTimeOfGuess(LocalDateTime.now().withNano(0));
        round1.setResultOfGuess("e:0:p:2");
        Round addedRound1 = roundDao.addRound(round1);

        List<Round> listOfAllRounds = roundDao.getAllRounds();

        assertEquals(listOfAllRounds.size(), 2);
        assertTrue(listOfAllRounds.contains(addedRound0));
        assertTrue(listOfAllRounds.contains(addedRound1));
    }

    /**
     * Test of getAllRoundsByGameId method, of class RoundDaoDB.
     */
    @Test
    public void testGetAllRoundsByGameId() {
        Game game0 = new Game();
        game0.setAnswer("3456");
        Game addedGame0 = gameDao.addGame(game0);

        Game game1 = new Game();
        game1.setAnswer("5678");
        Game addedGame1 = gameDao.addGame(game1);

        Round round0 = new Round();
        round0.setGameId(addedGame0.getGameId());
        round0.setGuess("1234");
        round0.setTimeOfGuess(LocalDateTime.now().withNano(0));
        round0.setResultOfGuess("e:0:p:2");
        Round addedRound0 = roundDao.addRound(round0);

        Round round1 = new Round();
        round1.setGameId(addedGame1.getGameId());
        round1.setGuess("9870");
        round1.setTimeOfGuess(LocalDateTime.now().withNano(0));
        round1.setResultOfGuess("e:0:p:2");
        Round addedRound1 = roundDao.addRound(round1);

        List<Round> listOfAllRounds = roundDao.getAllRoundsByGameId(game0.getGameId());

        assertEquals(listOfAllRounds.size(), 1);
        assertTrue(listOfAllRounds.contains(addedRound0));
        assertFalse(listOfAllRounds.contains(addedRound1));
    }

    /**
     * Test of updateRound method, of class RoundDaoDB.
     */
    @Test
    public void testUpdateRound() {
        Game game0 = new Game();
        game0.setAnswer("3456");
        gameDao.addGame(game0);

        Game game1 = new Game();
        game1.setAnswer("5678");
        Game addedGame1 = gameDao.addGame(game1);

        Round round = new Round();
        round.setGameId(game0.getGameId());
        round.setGuess("1234");
        round.setTimeOfGuess(LocalDateTime.now().withNano(0));
        round.setResultOfGuess("e:0:p:2");

        Round addedRound = roundDao.addRound(round);

        Round retrievedRound = roundDao.getRoundById(addedRound.getRoundId());

        round.setGameId(game1.getGameId());
        round.setGuess("5678");
        round.setTimeOfGuess(LocalDateTime.now().minusMinutes(5).withNano(0));
        round.setResultOfGuess("e:4:p:0");

        Round updatedRound = roundDao.updateRound(round);

        assertNotEquals(updatedRound, retrievedRound);

        retrievedRound = roundDao.getRoundById(updatedRound.getRoundId());

        assertEquals(retrievedRound, updatedRound);

    }

    /**
     * Test of deleteRoundById method, of class RoundDaoDB.
     */
    @Test
    public void testDeleteRoundById() {
        Game game0 = new Game();
        game0.setAnswer("3456");
        Game addedGame = gameDao.addGame(game0);

        Round round = new Round();
        round.setGameId(addedGame.getGameId());
        round.setGuess("1234");
        round.setTimeOfGuess(LocalDateTime.now().withNano(0));
        round.setResultOfGuess("e:0:p:2");
        Round addedRound = roundDao.addRound(round);

        Round deletedRound = roundDao.deleteRoundById(round.getRoundId());

        assertEquals(addedRound, deletedRound);
    }

}
