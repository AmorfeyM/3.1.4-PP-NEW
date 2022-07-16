package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {

    private UserService userService;
    private RoleRepository roleRepository;

    public MainController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/admin-panel")
    public String adminInfo(Principal principal, Model model, @ModelAttribute("newUser") User user) {
        User admin = userService.findByEmail(principal.getName());
        model.addAttribute("admin", admin);
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("roles", roleRepository.findAll());

        return "admin-panel";
    }

    @PostMapping("/admin/addUser")
    public String addUser(@ModelAttribute("newUser") User user,
                          @RequestParam("roles") List<Role> roles) {
        user.setRoleList(roles);
        userService.saveUser(user);
        return "redirect:/admin-panel";
    }

    @DeleteMapping("/admin/deleteUser/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin-panel";
    }

    @PatchMapping("/admin/updateUser/{id}")
    public String updateUser(User user, @RequestParam("roles") List<Role> roles) {
        user.setRoleList(roles);
        userService.saveUser(user);
        return "redirect:/admin-panel";
    }
}

