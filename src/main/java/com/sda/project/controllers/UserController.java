package com.sda.project.controllers;

import com.sda.project.entities.User;
import com.sda.project.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private UserService userService;
//    private User user;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/")
    public String showUpdateForm(Model model) {
        List<User> users = (List<User>) userService.get();
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "register";
    }

    @GetMapping("/login")
    public String showLogInForm() {
       return "login";
    }

//    @RequestMapping("/login-error")
//    public String loginError(Model model) {
//       model.addAttribute("loginError", true);
//       return "login";
//    }

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model){
        if(result.hasErrors()) {
            return "register";
        }

        userService.addUser(user);
        model.addAttribute("users", userService.get());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id" + id));
        model.addAttribute("user",user);
        return "user-edit";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "user-edit";
        }

        userService.addUser(user);
        model.addAttribute("users", userService.get());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        User user = userService.getById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id" + id));
        userService.delete(id);
        model.addAttribute("users", userService.get());
        return "index";
    }

}
