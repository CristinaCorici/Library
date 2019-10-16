package com.sda.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FragmentsController {

    @GetMapping("/index")
    public String getHome() {
        return "index.html";
    }

    @GetMapping("/register")
    public String markupPage() {
        return "register.html";
    }

    @GetMapping("/user-edit")
    public String paramsPage() {
        return "user-edit.html";
    }

//    @GetMapping("/other")
//    public String otherPage(Model model) {
//        model.addAttribute("data", StudentUtils.buildStudents());
//        return "other.html";
//    }
}