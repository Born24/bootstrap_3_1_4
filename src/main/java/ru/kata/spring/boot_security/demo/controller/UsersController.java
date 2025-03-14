package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class UsersController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "login";
    }

    @PostMapping("/addUser")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam("roleIds") List<Long> roleIds) {
        Set<Role> roles = roleService.findRolesByIds(roleIds);
        user.setRoles(roles);
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/update/{id}")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);

        return "edit";
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


    @GetMapping("/user")
    public String userProfile(Principal principal, Model model) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);

        if (user.getRoles().stream().anyMatch(role -> role.getRole().equals("ROLE_ADMIN"))) {
            return "admin_user";
        } else {
            return "user";
        }

    }

    @GetMapping("/addUser")
    public String showAddUserForm(Model model) {

        model.addAttribute("user", new User()); // Передаем новый объект User

        model.addAttribute("role", roleService.findAllRoles()); // Передаем список всех ролей

        return "addUser";
    }


}

