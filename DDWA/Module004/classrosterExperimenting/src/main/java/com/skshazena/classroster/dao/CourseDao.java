package com.skshazena.classroster.dao;

import com.skshazena.classroster.dto.Course;
import com.skshazena.classroster.dto.Student;
import com.skshazena.classroster.dto.Teacher;
import java.util.List;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Sep 20, 2020
 */
public interface CourseDao {

    Course getCourseById(int id);

    List<Course> getAllCourses();

    Course addCourse(Course course);

    void updateCourse(Course course);

    void deleteCourseById(int id);

    List<Course> getCoursesForTeacher(Teacher teacher);

    List<Course> getCoursesForStudent(Student student);
}
