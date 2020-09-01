package com.skshazena.bullsandcows.controller;

import com.skshazena.bullsandcows.dto.Game;
import com.skshazena.bullsandcows.dto.GameVM;
import com.skshazena.bullsandcows.dto.Round;
import com.skshazena.bullsandcows.dto.RoundVM;
import com.skshazena.bullsandcows.service.BullsAndCowsService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Aug 19, 2020
 */
@RestController
@RequestMapping("/api/bac")
public class BullsAndCowsController {

    private final BullsAndCowsService service;

    public BullsAndCowsController(BullsAndCowsService service) {
        this.service = service;
    }

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int beginGame() {
        /*
        This will generate a number for the game(service1). Then enter the new game in
        the GameDaoDB by using addGame (service2).
        It needs to return the newly generated gameId (bring whole game, extract just Id. 
         */
        String answer = service.generateAnswerForGame();
        Game newlyAddedGame = service.addGameToDB(answer);
        return newlyAddedGame.getGameId();
    }

    @PostMapping("/guess")
    public RoundVM makeAGuess(@RequestBody Round round) throws Exception {
        /**
         * This will take in the round object from the user. It will contain the
         * guess and the gameId. It will then take that gameId and get the game
         * from the gameDaoDB, so it can see the answer. Then run the validation
         * to see how it compares to the answer of the game. It will then set
         * the result in the round object, as well as the timeOfGuess. Then it
         * will add that to the roundDaoDB. Then if the resultOfGuess is
         * "e:4:p:0", it will update the GameDaoDB to have a status of finished.
         * Then it will return the round object with the results filled in.
         */
        Game theGame = service.validateGameId(round.getGameId());
        String resultOfGuess = service.validateGuess(theGame.getAnswer(), round.getGuess(), round.getGameId());
        round.setResultOfGuess(resultOfGuess);
        Round addedRound = service.addRoundToDB(round);
        service.updateGameIfFinished(theGame, resultOfGuess);
        return service.convertRoundToRoundVM(addedRound);
    }

    @GetMapping("/game")
    public List<GameVM> getAllGames() throws Exception {
        /**
         * this will call on the GameDao to getAllGames. Then run through each
         * game and if the game status is still in progress, it should change
         * the answer to "hidden".
         */
        List<GameVM> listOfAllGameVMs = service.getAllGames();
        service.checkForNoGames(listOfAllGameVMs);
        return listOfAllGameVMs;
    }

    @GetMapping("/game/{gameId}")
    public GameVM getGameById(@PathVariable int gameId) throws Exception {
        /**
         * takes in a gameId and gets the game from the GameDaoDB. Then it
         * checks to see what the status of this game is. IF the status is not
         * finished, it will remove the answer from it.
         */
        Game theGame = service.validateGameId(gameId);
        return service.convertGameToGameVM(theGame);
    }

    @GetMapping("/rounds/{gameId}")
    public List<RoundVM> getAllRoundsByGameId(@PathVariable int gameId) throws Exception {
        /**
         * calls on GameDaoDB to get a game object which will have a list of
         * rounds in it. Then it will extract this list and sort the list by the
         * time and then return this list.
         */
        service.validateGameId(gameId);
        return service.getAllRoundsByGameId(gameId);
    }
}
