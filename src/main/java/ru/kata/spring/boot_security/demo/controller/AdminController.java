package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
public class AdminController {

    private UserService userService;
    private RoleRepository roleRepository;

    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }
    @GetMapping("/admin/addUserForm")
    public String addUserForm(@ModelAttribute("newUser") User user, Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        return "newUser";
    }

    @PostMapping("/admin/addUser")
    public String addUser(@ModelAttribute("newUser") User user,
                          @RequestParam("roles") List<Role> roles) {
        user.setRoleList(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/deleteUser/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/update/{id}")
    public String update(Model model, @PathVariable("id") Long id) {
        User user = userService.findById(id);
        model.addAttribute("userToUpdate", user);
        model.addAttribute("roles", roleRepository.findAll());
        return "updateUser";
    }
    @PatchMapping("/admin/updateUser")
    public String updateUser(User user, @RequestParam("roles") List<Role> roles) {
        user.setRoleList(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }
}
