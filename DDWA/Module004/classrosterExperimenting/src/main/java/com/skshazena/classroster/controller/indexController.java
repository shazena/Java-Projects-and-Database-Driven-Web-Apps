package com.skshazena.classroster.controller;

import com.skshazena.classroster.dao.TeacherDao;
import com.skshazena.classroster.dto.Teacher;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Sep 24, 2020
 */
@Controller
public class indexController {

    @Autowired
    TeacherDao teacherDao;

    @GetMapping("/")
    public String showTeachersOnHomePage(Model model) {
        List<Teacher> teachers = teacherDao.getAllTeachers();
        model.addAttribute("teachers", teachers);
        return "index";
    }
}
