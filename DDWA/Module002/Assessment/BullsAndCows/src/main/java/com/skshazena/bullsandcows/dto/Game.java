package com.skshazena.bullsandcows.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Aug 19, 2020
 */
public class Game {

    private int gameId;
    private String answer;
    private boolean status;
    private List<Round> listOfRounds = new ArrayList<>();

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Round> getListOfRounds() {
        return listOfRounds;
    }

    public void setListOfRounds(List<Round> listOfRounds) {
        this.listOfRounds = listOfRounds;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.gameId;
        hash = 29 * hash + Objects.hashCode(this.answer);
        hash = 29 * hash + (this.status ? 1 : 0);
        hash = 29 * hash + Objects.hashCode(this.listOfRounds);
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
        final Game other = (Game) obj;
        if (this.gameId != other.gameId) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.answer, other.answer)) {
            return false;
        }
        if (!Objects.equals(this.listOfRounds, other.listOfRounds)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Game{" + "gameId=" + gameId + ", answer=" + answer + ", status=" + status + ", listOfRounds=" + listOfRounds + '}';
    }

}
