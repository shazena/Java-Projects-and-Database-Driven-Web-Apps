package com.skshazena.bullsandcows.service;

import com.skshazena.bullsandcows.dao.*;
import com.skshazena.bullsandcows.dto.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Aug 19, 2020
 */
@Service
public class BullsAndCowsServiceImpl implements BullsAndCowsService {

    GameDao gameDao;
    RoundDao roundDao;

    @Autowired
    public BullsAndCowsServiceImpl(GameDao gameDao, RoundDao roundDao) {
        this.gameDao = gameDao;
        this.roundDao = roundDao;
    }

    /**
     * addGameToDB - takes in an answer string and adds a game to the db with
     * that answer. It returns the newly added game.
     *
     * @param answer {String} 4-digit answer
     * @return {Game} the fully added game
     */
    @Override
    public Game addGameToDB(String answer) {

        Game game = new Game();
        game.setAnswer(answer);

        Game addedGame = gameDao.addGame(game);

        return addedGame;
    }

    /**
     * generateAnswerForGame - generates a four digit number that is
     * non-repeating. No math is performed on the number so it is stored as a
     * string.
     *
     * @return {String}
     */
    @Override
    public String generateAnswerForGame() {

        Random r = new Random();
        Set<Integer> nonRepeatingDigits = new HashSet<>();
        while (nonRepeatingDigits.size() < 4) {
            nonRepeatingDigits.add(r.nextInt(10));
        }

        ArrayList<Integer> listOfNums = new ArrayList<Integer>(nonRepeatingDigits);

        Collections.shuffle(listOfNums);

        StringBuilder answer = new StringBuilder("");
        for (Integer i : listOfNums) {
            answer.append(i);
        }
        return answer.toString();
    }

    /**
     * validateGuess - checks to see if the game exists, then if the guess the
     * user entered is valid and then calculates the result of the guess and
     * returns the String needed to be stored in the database.
     *
     * @param answer {String} answer to game
     * @param guess {String} guess by user
     * @param gameId {int} id of game to see if it exists
     * @return {String} result of guess, formatted for database
     * @throws BullsAndCowsInvalidGuessException
     * @throws BullsAndCowsGameIsAlreadyFinishedException
     */
    @Override
    public String validateGuess(String answer, String guess, int gameId)
            throws BullsAndCowsInvalidGuessException,
            BullsAndCowsGameIsAlreadyFinishedException {

        Game game = gameDao.getGameById(gameId);
        if (game.isStatus()) {
            throw new BullsAndCowsGameIsAlreadyFinishedException("That game is already finished. You cannot make another guess.");
        }

        if (guess == null
                || guess.isBlank()
                || !guess.matches("[0-9]{4}")) {
            throw new BullsAndCowsInvalidGuessException("That guess is not valid");
        }

        int partialsCounter = 0;
        int exactMatches = 0;

        for (int i = 0; i < 4; i++) {
            String answerSubString = answer.substring(i, i + 1);
            String guessSubString = guess.substring(i, i + 1);
            if (guessSubString.equals(answerSubString)) {
                exactMatches++;
            } else if (guess.contains(answerSubString)) {
                partialsCounter++;
            }
        }

        String resultOfGuess = "e:" + exactMatches + ":p:" + partialsCounter;

        return resultOfGuess;
    }

    /**
     * updateGameIfFinished - this method takes in the current Game and the
     * result of the current round and checks to see if the game is finished. If
     * the game is finished, it will update it in the database.
     *
     * @param game {Game} game being played
     * @param resultOfGuess {String} result of Guess to see if it is a win
     * @return {Game} updated Game object with finished status
     */
    @Override
    public Game updateGameIfFinished(Game game, String resultOfGuess) {
        if (resultOfGuess.equals("e:4:p:0")) {
            game.setStatus(true);
            return gameDao.updateGame(game);
        } else {
            return null;
        }
    }

    /**
     * addRoundToDB - sets the time in the round and adds the round to the
     * database
     *
     * @param round {Round} round object with guess, gameId, and result filled
     * in
     * @return {Round} the completely filled Round object
     */
    @Override
    public Round addRoundToDB(Round round) {
        round.setTimeOfGuess(LocalDateTime.now());
        Round addedRound = roundDao.addRound(round);
        return addedRound;
    }

    /**
     * validateGameId - checks to see if the gameId actually exists. Throws an
     * exception if it does not.
     *
     * @param gameId {int}
     * @return {Game} the Game object if it exists
     * @throws BullsAndCowsGameDoesNotExistException
     */
    @Override
    public Game validateGameId(int gameId)
            throws BullsAndCowsGameDoesNotExistException {

        Game game = gameDao.getGameById(gameId);
        if (game == null) {
            throw new BullsAndCowsGameDoesNotExistException("That game id does not exist");
        }
        return game;
    }

    /**
     * getAllGames - gets all Games from the Database and converts them to the
     * view model
     *
     * @return {List<GameVM>} List of games, converted to View Model
     */
    @Override
    public List<GameVM> getAllGames() {
        List<Game> listOfAllGames = gameDao.getAllGames();
        List<GameVM> listOfAllGameVMs = new ArrayList<>();
        for (Game game : listOfAllGames) {
            listOfAllGameVMs.add(convertGameToGameVM(game));
        }
        return listOfAllGameVMs;
    }

    /**
     * checkForNoGames - checks to see if there are any games in a given list of
     * GameVMs. Throws exception if list is empty
     *
     * @param listOfAllGameVMs {List<GameVM>} list of GameVMs
     * @throws BullsAndCowsNoGamesException
     */
    @Override
    public void checkForNoGames(List<GameVM> listOfAllGameVMs) throws BullsAndCowsNoGamesException {
        if (listOfAllGameVMs.isEmpty()) {
            throw new BullsAndCowsNoGamesException("No saved game data.");
        }
    }

    /**
     * getAllRoundsByGameId - takes in a gameId and then converts them to the
     * RoundVM. If the list is empty, then throw an exception
     *
     * @param gameId {int}
     * @return {List<RoundVM>} list of round VMs
     * @throws BullsAndCowsNoRoundsForGameException
     */
    @Override
    public List<RoundVM> getAllRoundsByGameId(int gameId) throws BullsAndCowsNoRoundsForGameException {

        List<Round> allRoundsByGameId = roundDao.getAllRoundsByGameId(gameId);

        List<RoundVM> allRoundVMsByGameId = new ArrayList<>();

        for (Round round : allRoundsByGameId) {
            allRoundVMsByGameId.add(convertRoundToRoundVM(round));
        }

        if (allRoundVMsByGameId.isEmpty()) {
            throw new BullsAndCowsNoRoundsForGameException("That game has no rounds yet");
        } else {
            return allRoundVMsByGameId;
        }
    }

    /**
     * convertGameToGameVM - converts Game to GameVM, will hide answer if the
     * game is not finished.
     *
     * @param game {Game} Game Object
     * @return {GameVM} GameVM for user
     */
    @Override
    public GameVM convertGameToGameVM(Game game) {
        GameVM gameVM = new GameVM();

        gameVM.setGameId(game.getGameId());

        String gameDescription = "Game " + game.getGameId() + " is ";
        if (game.isStatus()) {
            gameDescription += "finished. Answer was " + game.getAnswer() + ". ";
        } else {
            gameDescription += "still in progress. Keep playing to find the answer! ";
        }

        gameDescription += game.getListOfRounds().size() + " rounds were played. ";

        gameVM.setGameDescription(gameDescription);

        return gameVM;
    }

    /**
     * convertRoundToRoundVM - converts Round to RoundVM. Will tell user if the
     * game is finished and they won.
     *
     * @param round {Round} Round Object
     * @return {RoundVM} RoundVM for user
     */
    @Override
    public RoundVM convertRoundToRoundVM(Round round) {
        RoundVM roundVM = new RoundVM();
        roundVM.setGuess(round.getGuess());
        int exactMatches = Integer.parseInt(round.getResultOfGuess().substring(2, 3));
        int partialMatches = Integer.parseInt(round.getResultOfGuess().substring(6, 7));

        String result = "";
        if (exactMatches == 4) {
            result = "That was the answer. You won! Congrats!";
        } else {
            switch (exactMatches) {
                case 1:
                    result += "That guess had " + exactMatches + " exact match and ";
                    break;
                default:
                    result += "That guess had " + exactMatches + " exact matches and ";
                    break;
            }

            switch (partialMatches) {
                case 1:
                    result += partialMatches + " partial match.";
                    break;
                default:
                    result += partialMatches + " partial matches.";
                    break;
            }
            result += " It was made on " + round.getTimeOfGuess().format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy hh:mm:ss"));
        }
        roundVM.setResult(result);
        return roundVM;
    }

}
