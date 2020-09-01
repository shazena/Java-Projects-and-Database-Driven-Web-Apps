package com.skshazena.bullsandcows.dto;

import java.util.Objects;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Aug 24, 2020
 */
public class GameVM {

    private int gameId;
    private String gameDescription;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.gameId;
        hash = 53 * hash + Objects.hashCode(this.gameDescription);
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
        final GameVM other = (GameVM) obj;
        if (this.gameId != other.gameId) {
            return false;
        }
        if (!Objects.equals(this.gameDescription, other.gameDescription)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GameVM{" + "gameId=" + gameId + ", gameDescription=" + gameDescription + '}';
    }

}
