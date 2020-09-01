
package com.skshazena.sakiladao.DTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Shazena Khan 
 * 
Date Created: Aug 11, 2020
 */
public class Actor {
    private int actorId;
    private String firstName;
    private String lastName;
    private LocalDateTime lastUpdate;
    private List<Film> films;

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }
    
}
