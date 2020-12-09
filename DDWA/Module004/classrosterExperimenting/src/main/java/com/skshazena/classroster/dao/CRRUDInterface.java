package com.skshazena.classroster.dao;

import java.util.List;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Sep 27, 2020
 */
public interface CRRUDInterface<Item> {

    Item getOneById(int id);

    List<Item> getAll();

    Item add(Item item);

    void update(Item item);

    void deleteById(int id);
}
