package ru.kata.spring.boot_security.demo.controller;

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
public class MainController {

    private UserService userService;
    private RoleService roleService;

    public MainController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin-panel")
    public String adminInfo(Principal principal, Model model, @ModelAttribute("newUser") User user) {
        User admin = userService.findByEmail(principal.getName());
        model.addAttribute("admin", admin);
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("roles", roleService.findAll());

        return "admin-panel";
    }

    @PostMapping("/admin/addUser")
    public String addUser(@ModelAttribute("newUser") User user,
                          @RequestParam("roles") Set<Role> roles) {
        user.setRoleSet(roles);
        userService.saveUser(user);
        return "redirect:/admin-panel";
    }

    @DeleteMapping("/admin/deleteUser/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin-panel";
    }

    @PatchMapping("/admin/updateUser/{id}")
    public String updateUser(User user, @RequestParam("roles") Set<Role> roles) {
        user.setRoleSet(roles);
        userService.saveUser(user);
        return "redirect:/admin-panel";
    }
}

