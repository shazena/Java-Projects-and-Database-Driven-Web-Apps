/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skshazena.bullsandcows.service;

import com.skshazena.bullsandcows.dto.Game;
import com.skshazena.bullsandcows.dto.GameVM;
import com.skshazena.bullsandcows.dto.Round;
import com.skshazena.bullsandcows.dto.RoundVM;
import java.util.List;

/**
 *
 * @author Shazena Khan
 *
Date Created: Aug 28, 2020
 */
public interface BullsAndCowsService {

    /**
     * addGameToDB - takes in an answer string and adds a game to the db with
     * that answer. It returns the newly added game.
     *
     * @param answer {String} 4-digit answer
     * @return {Game} the fully added game
     */
    Game addGameToDB(String answer);

    /**
     * addRoundToDB - sets the time in the round and adds the round to the
     * database
     *
     * @param round {Round} round object with guess, gameId, and result filled
     * in
     * @return {Round} the completely filled Round object
     */
    Round addRoundToDB(Round round);

    /**
     * checkForNoGames - checks to see if there are any games in a given list of
     * GameVMs. Throws exception if list is empty
     *
     * @param listOfAllGameVMs {List<GameVM>} list of GameVMs
     * @throws BullsAndCowsNoGamesException
     */
    void checkForNoGames(List<GameVM> listOfAllGameVMs) throws BullsAndCowsNoGamesException;

    /**
     * convertGameToGameVM - converts Game to GameVM, will hide answer if the
     * game is not finished.
     *
     * @param game {Game} Game Object
     * @return {GameVM} GameVM for user
     */
    GameVM convertGameToGameVM(Game game);

    /**
     * convertRoundToRoundVM - converts Round to RoundVM. Will tell user if the
     * game is finished and they won.
     *
     * @param round {Round} Round Object
     * @return {RoundVM} RoundVM for user
     */
    RoundVM convertRoundToRoundVM(Round round);

    /**
     * generateAnswerForGame - generates a four digit number that is
     * non-repeating. No math is performed on the number so it is stored as a
     * string.
     *
     * @return {String}
     */
    String generateAnswerForGame();

    /**
     * getAllGames - gets all Games from the Database and converts them to the
     * view model
     *
     * @return {List<GameVM>} List of games, converted to View Model
     */
    List<GameVM> getAllGames();

    /**
     * getAllRoundsByGameId - takes in a gameId and then converts them to the
     * RoundVM. If the list is empty, then throw an exception
     *
     * @param gameId {int}
     * @return {List<RoundVM>} list of round VMs
     * @throws BullsAndCowsNoRoundsForGameException
     */
    List<RoundVM> getAllRoundsByGameId(int gameId) throws BullsAndCowsNoRoundsForGameException;

    /**
     * updateGameIfFinished - this method takes in the current Game and the
     * result of the current round and checks to see if the game is finished. If
     * the game is finished, it will update it in the database.
     *
     * @param game {Game} game being played
     * @param resultOfGuess {String} result of Guess to see if it is a win
     * @return {Game} updated Game object with finished status
     */
    Game updateGameIfFinished(Game game, String resultOfGuess);

    /**
     * validateGameId - checks to see if the gameId actually exists. Throws an
     * exception if it does not.
     *
     * @param gameId {int}
     * @return {Game} the Game object if it exists
     * @throws BullsAndCowsGameDoesNotExistException
     */
    Game validateGameId(int gameId) throws BullsAndCowsGameDoesNotExistException;

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
    String validateGuess(String answer, String guess, int gameId) throws BullsAndCowsInvalidGuessException, BullsAndCowsGameIsAlreadyFinishedException;
    
}
