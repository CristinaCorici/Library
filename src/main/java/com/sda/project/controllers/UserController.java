package com.sda.project.controllers;

import com.sda.project.entities.User;
import com.sda.project.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private UserService userService;
    private User user;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showUpdateForm() {

        return "index";
    }

//    @PostMapping("/")
//    public void getAdmin(Model model) {
//        User userAdmin = new User("Administrator", "admin@gmail.com",
//                "administrator", "ADMIN");
//        if (userAdmin.getEmail().isEmpty()) {
//        userService.addUser(userAdmin);
//        model.addAttribute("users", userService.get());
//        }
//    }

    @GetMapping("/view-users")
    public String showUpdateForm(Model model) {
        List<User> users = (List<User>) userService.get();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "users/register";
    }

    @GetMapping("/login")
    public String showLogInForm(@PathVariable(required = false) String error) {
        return "users/login";
    }

    @GetMapping("/first-page")
    public String showFirstPage() {
        return "first-page";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "users/register";
        }

        user.setRole("USER");
        userService.addUser(user);
        model.addAttribute("users", userService.get());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id" + id));
        model.addAttribute("user", user);
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
