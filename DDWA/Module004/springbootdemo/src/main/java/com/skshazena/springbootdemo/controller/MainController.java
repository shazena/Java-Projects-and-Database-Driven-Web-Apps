package com.skshazena.springbootdemo.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Sep 20, 2020
 */
@Controller
public class MainController {

    String name = "John";
    int number = 42;

    @GetMapping("test")
    public String testPage(Model model) {

        model.addAttribute("number", number);
        model.addAttribute("firstName", name);

        return "test";

    }

    @PostMapping("testForm")
    public String testForm(HttpServletRequest request) {
        name = request.getParameter("formFirstName");
        number = Integer.parseInt(request.getParameter("formNumber"));

        return "redirect:/test";
    }
}