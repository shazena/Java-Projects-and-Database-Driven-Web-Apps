package com.skshazena.bullsandcows.dao;

import com.skshazena.bullsandcows.dto.Round;
import java.util.List;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Aug 19, 2020
 */
public interface RoundDao {

    Round getRoundById(int roundId);

    List<Round> getAllRounds();

    List<Round> getAllRoundsByGameId(int gameId);

    Round addRound(Round round);

    Round updateRound(Round round);

    Round deleteRoundById(int roundId);

}
