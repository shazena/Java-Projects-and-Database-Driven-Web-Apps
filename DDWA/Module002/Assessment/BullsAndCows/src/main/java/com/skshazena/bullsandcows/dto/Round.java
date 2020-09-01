package com.skshazena.bullsandcows.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Aug 19, 2020
 */
public class Round {

    private int roundId;
    private int gameId;
    private String guess;
    private LocalDateTime timeOfGuess;
    private String resultOfGuess;

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public LocalDateTime getTimeOfGuess() {
        return timeOfGuess;
    }

    public void setTimeOfGuess(LocalDateTime timeOfGuess) {
        this.timeOfGuess = timeOfGuess;
    }

    public String getResultOfGuess() {
        return resultOfGuess;
    }

    public void setResultOfGuess(String resultOfGuess) {
        this.resultOfGuess = resultOfGuess;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.roundId;
        hash = 23 * hash + this.gameId;
        hash = 23 * hash + Objects.hashCode(this.guess);
        hash = 23 * hash + Objects.hashCode(this.timeOfGuess);
        hash = 23 * hash + Objects.hashCode(this.resultOfGuess);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Round other = (Round) obj;
        if (this.roundId != other.roundId) {
            return false;
        }
        if (this.gameId != other.gameId) {
            return false;
        }
        if (!Objects.equals(this.guess, other.guess)) {
            return false;
        }
        if (!Objects.equals(this.resultOfGuess, other.resultOfGuess)) {
            return false;
        }
        if (!Objects.equals(this.timeOfGuess, other.timeOfGuess)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Round{" + "roundId=" + roundId + ", gameId=" + gameId + ", guess=" + guess + ", timeOfGuess=" + timeOfGuess + ", resultOfGuess=" + resultOfGuess + '}';
    }

}
