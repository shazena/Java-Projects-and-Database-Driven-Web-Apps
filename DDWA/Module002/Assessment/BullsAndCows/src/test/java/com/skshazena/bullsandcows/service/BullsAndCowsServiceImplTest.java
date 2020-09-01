package com.skshazena.bullsandcows.service;

import com.skshazena.bullsandcows.TestApplicationConfiguration;
import com.skshazena.bullsandcows.dto.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
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
public class BullsAndCowsServiceImplTest {

    @Autowired
    BullsAndCowsService service;

    public BullsAndCowsServiceImplTest() {
    }

    /**
     * Test of addGameToDB method, of class BullsAndCowsServiceImpl.
     */
    @Test
    public void testAddGameToDB() {
        String answer = "1234";
        Game addedGame = service.addGameToDB(answer);
        assertEquals(answer, addedGame.getAnswer());
    }

    /**
     * Test of generateAnswerForGame method, of class BullsAndCowsServiceImpl.
     */
    @Test
    public void testGenerateAnswerForGame() {
        String answerForGame = service.generateAnswerForGame();

        if (answerForGame.length() != 4) {
            fail("Generated answer does not have four digits");
        }

        //check for repeating digits in generated answer
        for (int i = 0; i < answerForGame.length() - 1; i++) {
//            System.out.println("This is i " + i);
//            System.out.println("This is i+1 " + (i + 1));
            String restOfString = answerForGame.substring(i + 1);
//            System.out.print("Rest of String " + restOfString + ",");
            String singleDigit = answerForGame.substring(i, i + 1);
//            System.out.println("Single digit " + singleDigit);
            if (restOfString.contains(singleDigit)) {
                fail("Digits in generated guess repeat");
            }

        }
    }

    /**
     * Test of validateGuess method, of class BullsAndCowsServiceImpl.
     */
    @Test
    public void testValidateGuess() throws Exception {
        Game addedGame = service.addGameToDB("1234");
        String guess = "5678";
        //completely incorrect guess
        String resultOfGuess = service.validateGuess(addedGame.getAnswer(), guess, addedGame.getGameId());
        assertEquals(resultOfGuess, "e:0:p:0");

        //partially correct guess
        guess = "3264";
        resultOfGuess = service.validateGuess(addedGame.getAnswer(), guess, addedGame.getGameId());
        assertEquals(resultOfGuess, "e:2:p:1");

        //null guess
        guess = null;
        try {
            service.validateGuess(addedGame.getAnswer(), guess, addedGame.getGameId());
        } catch (BullsAndCowsInvalidGuessException e) {
            System.out.println("Correct Exception caught");
        } catch (BullsAndCowsGameIsAlreadyFinishedException e) {
            fail("Incorrect Exception caught.");
        }

        //empty String guess
        guess = "";
        try {
            service.validateGuess(addedGame.getAnswer(), guess, addedGame.getGameId());
        } catch (BullsAndCowsInvalidGuessException e) {
            System.out.println("Correct Exception caught");
        } catch (BullsAndCowsGameIsAlreadyFinishedException e) {
            fail("Incorrect Exception caught.");
        }

        //guess that is not 4 digits
        guess = "12";
        try {
            service.validateGuess(addedGame.getAnswer(), guess, addedGame.getGameId());
        } catch (BullsAndCowsInvalidGuessException e) {
            System.out.println("Correct Exception caught");
        } catch (BullsAndCowsGameIsAlreadyFinishedException e) {
            fail("Incorrect Exception caught.");
        }
        
        //guess that is all letters
        guess = "abcd";
        try {
            service.validateGuess(addedGame.getAnswer(), guess, addedGame.getGameId());
        } catch (BullsAndCowsInvalidGuessException e) {
            System.out.println("Correct Exception caught");
        } catch (BullsAndCowsGameIsAlreadyFinishedException e) {
            fail("Incorrect Exception caught.");
        }
        
        //guess that is letters and numbers
        guess = "a9cd";
        try {
            service.validateGuess(addedGame.getAnswer(), guess, addedGame.getGameId());
        } catch (BullsAndCowsInvalidGuessException e) {
            System.out.println("Correct Exception caught");
        } catch (BullsAndCowsGameIsAlreadyFinishedException e) {
            fail("Incorrect Exception caught.");
        }

        //valid guess for a game that does not exist
        service.updateGameIfFinished(addedGame, "e:4:p:0");
        guess = "3245";
        try {
            service.validateGuess(addedGame.getAnswer(), guess, addedGame.getGameId());
        } catch (BullsAndCowsGameIsAlreadyFinishedException e) {
            System.out.println("Correct Exception caught");
        } catch (BullsAndCowsInvalidGuessException e) {
            fail("Incorrect Exception caught.");
        }
    }

    /**
     * Test of updateGameIfFinished method, of class BullsAndCowsServiceImpl.
     */
    @Test
    public void testUpdateGameIfFinished() {
        Game addedGame = service.addGameToDB("1234");
        Game notFinishedGame = service.updateGameIfFinished(addedGame, "e:3:p:1");
        assertNull(notFinishedGame);

        Game finishedGame = service.updateGameIfFinished(addedGame, "e:4:p:0");
        assertTrue(finishedGame.isStatus());

    }

    /**
     * Test of addRoundToDB method, of class BullsAndCowsServiceImpl.
     */
    @Test
    public void testAddRoundToDB() {
        Game addedGame = service.addGameToDB("1234");
        Round round = new Round();
        round.setGameId(addedGame.getGameId());
        round.setGuess("1234");
        round.setResultOfGuess("e:4:p:0");
        Round addedRound = service.addRoundToDB(round);
        assertNotNull(addedRound.getTimeOfGuess());
    }

    /**
     * Test of validateGameId method, of class BullsAndCowsServiceImpl.
     */
    @Test
    public void testValidateGameId() throws Exception {
        //try a game Id that does exist
        Game addedGame = service.addGameToDB("1234");
        try {
            service.validateGameId(addedGame.getGameId());
        } catch (BullsAndCowsGameDoesNotExistException e) {
            fail("No exception should be thrown");
        }

        //make a gameId that does not exist, catch the exception
        int gameIdThatDoesNotExist = addedGame.getGameId() + 5;
        try {
            service.validateGameId(gameIdThatDoesNotExist);
            fail("Exception should be caught. Game does not exist.");
        } catch (BullsAndCowsGameDoesNotExistException e) {
            System.out.println("That was the correct Exception!");
        }
    }

    /**
     * Test of getAllGames method, of class BullsAndCowsServiceImpl.
     */
    @Test
    public void testGetAllGames() throws Exception {

        Game addedGame = service.addGameToDB("1234");
        GameVM gameVM = service.convertGameToGameVM(addedGame);

        List<GameVM> allGames = service.getAllGames();

        assertTrue(allGames.contains(gameVM));

    }

    /**
     * Test of checkForNoGames method, of class BullsAndCowsServiceImpl.
     */
    @Test
    public void testCheckForNoGames() throws Exception {

        GameVM gameVM = new GameVM();
        gameVM.setGameId(1);
        gameVM.setGameDescription("This is a game");

        List<GameVM> listOfAllGameVMs = new ArrayList<>();

        try {
            service.checkForNoGames(listOfAllGameVMs);
            fail("That list contains no games. It should throw an exception");
        } catch (BullsAndCowsNoGamesException e) {
            System.out.println("Correct Exception caught!");
        }

        listOfAllGameVMs.add(gameVM);

        try {
            service.checkForNoGames(listOfAllGameVMs);
        } catch (BullsAndCowsNoGamesException e) {
            fail("That list contains a game. It should not throw an exception");
        }

    }

    /**
     * Test of getAllRoundsByGameId method, of class BullsAndCowsServiceImpl.
     */
    @Test
    public void testGetAllRoundsByGameId() throws Exception {
        Game addedGame = service.addGameToDB("1234");

        try {
            List<RoundVM> allRoundVMsByGameId = service.getAllRoundsByGameId(addedGame.getGameId());
            fail("No Rounds for Game Exception should have been caught");
        } catch (BullsAndCowsNoRoundsForGameException e) {
            System.out.println("There's the right exception");
        }

        Round round = new Round();
        round.setGameId(addedGame.getGameId());
        round.setGuess("3456");
        round.setResultOfGuess("e:0:p:2");
        Round addedRound = service.addRoundToDB(round);

        RoundVM roundVM = service.convertRoundToRoundVM(round);

        //get list for a valid gameId
        try {
            List<RoundVM> allRoundVMsByGameId = service.getAllRoundsByGameId(addedGame.getGameId());
            assertTrue(allRoundVMsByGameId.contains(roundVM));
            assertTrue(allRoundVMsByGameId.size() == 1);
        } catch (BullsAndCowsNoRoundsForGameException e) {
            fail("No Exception should have been caught");
        }

    }

    /**
     * Test of convertGameToGameVM method, of class BullsAndCowsServiceImpl.
     */
    @Test
    public void testConvertGameToGameVM() {

        Game addedGame = service.addGameToDB("1234");

        GameVM gameVM = new GameVM();
        gameVM.setGameId(addedGame.getGameId());
        gameVM.setGameDescription("Game " + addedGame.getGameId() + " is "
                + "still in progress. Keep playing to find the answer! "
                + addedGame.getListOfRounds().size() + " rounds were played. ");

        GameVM convertedGame = service.convertGameToGameVM(addedGame);
        assertEquals(gameVM, convertedGame);

        Round round = new Round();
        round.setGameId(addedGame.getGameId());
        round.setGuess("1234");
        round.setResultOfGuess("e:4:p:0");
        service.addRoundToDB(round);

        Game finishedGame = service.updateGameIfFinished(addedGame, round.getResultOfGuess());

        GameVM convertedFinishedGame = service.convertGameToGameVM(finishedGame);

        GameVM gameVMFin = new GameVM();
        gameVMFin.setGameId(addedGame.getGameId());
        gameVMFin.setGameDescription("Game " + addedGame.getGameId() + " is "
                + "finished. Answer was " + addedGame.getAnswer() + ". "
                + addedGame.getListOfRounds().size() + " rounds were played. ");

        assertEquals(gameVMFin, convertedFinishedGame);

    }

    /**
     * Test of convertRoundToRoundVM method, of class BullsAndCowsServiceImpl.
     */
    @Test
    public void testConvertRoundToRoundVM() {
        Game addedGame = service.addGameToDB("1234");

        //round with perfect score
        Round round = new Round();
        round.setGameId(addedGame.getGameId());
        round.setGuess("1234");
        round.setResultOfGuess("e:4:p:0");
        Round addedRound = service.addRoundToDB(round);

        RoundVM convertedRound = service.convertRoundToRoundVM(addedRound);

        RoundVM roundVM = new RoundVM();
        roundVM.setGuess(round.getGuess());
        roundVM.setResult("That was the answer. You won! Congrats!");

        assertEquals(roundVM, convertedRound);

        //round with 1 exact match and 1 partial match
        round.setGuess("1389");
        round.setResultOfGuess("e:1:p:1");
        addedRound = service.addRoundToDB(round);

        convertedRound = service.convertRoundToRoundVM(addedRound);

        int exactMatches = Integer.parseInt(round.getResultOfGuess().substring(2, 3));
        int partialMatches = Integer.parseInt(round.getResultOfGuess().substring(6, 7));

        roundVM = new RoundVM();
        roundVM.setGuess(round.getGuess());
        roundVM.setResult("That guess had " + exactMatches + " exact match and "
                + partialMatches + " partial match. It was made on "
                + round.getTimeOfGuess().format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy hh:mm:ss")));

        assertEquals(roundVM, convertedRound);

        //round with multiple exact and 0 partial
        round.setGuess("1289");
        round.setResultOfGuess("e:2:p:0");
        addedRound = service.addRoundToDB(round);

        convertedRound = service.convertRoundToRoundVM(addedRound);

        exactMatches = Integer.parseInt(round.getResultOfGuess().substring(2, 3));
        partialMatches = Integer.parseInt(round.getResultOfGuess().substring(6, 7));

        roundVM = new RoundVM();
        roundVM.setGuess(round.getGuess());
        roundVM.setResult("That guess had " + exactMatches + " exact matches and "
                + partialMatches + " partial matches. It was made on "
                + round.getTimeOfGuess().format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy hh:mm:ss")));

        assertEquals(roundVM, convertedRound);
    }

}
