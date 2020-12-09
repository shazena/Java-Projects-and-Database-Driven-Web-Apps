package com.skshazena.SpringSecurity.daos;

import com.skshazena.SpringSecurity.dtos.Role;
import java.util.List;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Oct 14, 2020
 */
public interface RoleDao {

    Role getRoleById(int id);

    Role getRoleByRole(String role);

    List<Role> getAllRoles();

    void deleteRole(int id);

    void updateRole(Role role);

    Role createRole(Role role);
}
