package com.skshazena.SpringSecurity.daos;

import com.skshazena.SpringSecurity.dtos.User;
import java.util.List;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Oct 14, 2020
 */
public interface UserDao {

    User getUserById(int id);

    User getUserByUsername(String username);

    List<User> getAllUsers();

    void updateUser(User user);

    void deleteUser(int id);

    User createUser(User user);
}
