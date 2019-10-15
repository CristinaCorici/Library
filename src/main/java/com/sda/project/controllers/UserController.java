package com.sda.project.controllers;

import com.sda.project.entities.User;
import com.sda.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/")
    public String homePage() {
        User user1 = new User();
        user1.setName("Gigi");
        user1.setEmail("gigi@gmail.com");
        user1.setPassword("123");
        userRepository.save(user1);
        userRepository.findAll();

//        model.addAttribute("appName", appName);
        return "index";
    }
}
