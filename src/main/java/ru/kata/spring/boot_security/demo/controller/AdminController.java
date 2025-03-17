package ru.kata.spring.boot_security.demo.controller;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @PostMapping("/addUser")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam("roleIds") List<Long> roleIds) {
        Set<Role> roles = roleService.findRolesByIds(roleIds);
        user.setRoles(roles);
        userService.add(user);
        return "redirect:/admin";
    }

    @PutMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id,
                             @RequestParam("roleIds") List<Long> roleIds) {
        Set<Role> roles = roleService.findRolesByIds(roleIds);
        user.setRoles(roles);
        user.setId(id);
        userService.update(user);

        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);

        return "redirect:/admin";
    }


    @GetMapping("/admin")
    public String adminPage(Model model) {
        List<User> users = userService.findAllWithRoles();
        model.addAttribute("user", new User());
        model.addAttribute("users", users);
        model.addAttribute("role", roleService.findAllRoles());

        return "admin";
    }

}
