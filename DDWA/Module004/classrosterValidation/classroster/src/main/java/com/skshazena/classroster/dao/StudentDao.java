package com.skshazena.classroster.dao;

import com.skshazena.classroster.dto.Student;
import java.util.List;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Sep 20, 2020
 */
public interface StudentDao {

    Student getStudentById(int id);

    List<Student> getAllStudents();

    Student addStudent(Student student);

    void updateStudent(Student student);

    void deleteStudentById(int id);
}
