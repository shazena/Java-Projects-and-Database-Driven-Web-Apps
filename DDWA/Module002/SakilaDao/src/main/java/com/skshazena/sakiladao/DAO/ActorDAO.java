package com.skshazena.sakiladao.DAO;

import com.skshazena.sakiladao.DTO.Actor;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Aug 11, 2020
 */
public interface ActorDAO {

    public Actor createActor(Actor actor);

    List<Actor> ReadAll();
    Actor ReadById(int actorId);
}
