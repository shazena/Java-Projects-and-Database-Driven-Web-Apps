package com.skshazena.classroster.dao;

import com.skshazena.classroster.dto.Teacher;
import java.util.List;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Sep 20, 2020
 */
public interface TeacherDao {

    Teacher getTeacherById(int id);

    List<Teacher> getAllTeachers();

    Teacher addTeacher(Teacher teacher);

    void updateTeacher(Teacher teacher);

    void deleteTeacherById(int id);
}
