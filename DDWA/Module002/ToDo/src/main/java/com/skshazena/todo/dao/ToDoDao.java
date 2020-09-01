
package com.skshazena.todo.dao;

import com.skshazena.todo.dto.ToDo;
import java.util.List;

/**
 *
 * @author Shazena Khan 
 * 
Date Created: Aug 17, 2020
 */
public interface ToDoDao {

    ToDo add(ToDo todo);

    List<ToDo> getAll();

    ToDo findById(int id);

    // true if item exists and is updated
    boolean update(ToDo todo);

    // true if item exists and is deleted
    boolean deleteById(int id);
}
