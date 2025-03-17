package ru.kata.spring.boot_security.demo.controller;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String userProfile(Principal principal, Model model) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        Hibernate.initialize(user.getRoles());

        model.addAttribute("user", user);
        return "user_profile";
    }
}
