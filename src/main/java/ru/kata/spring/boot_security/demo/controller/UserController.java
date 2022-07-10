package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;


@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String processRegister(User user) {
        userService.registerDefaultUser(user);

        return "register_success";
    }

    @GetMapping(value = "/users")
    public String showAllUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }
    @GetMapping("/viewUser")
    public String newUser(@ModelAttribute("newUser") User user) {
        return "viewUser";
    }
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @DeleteMapping("/users/deleteUser/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping("/users/update/{id}")
    public String update(Model model, @PathVariable("id") Long id) {
        User user = userService.findById(id);
        List<Role> listRoles = userService.listRoles();
        model.addAttribute("userToUpdate", user);
        model.addAttribute("listRoles", listRoles);
        return "updateUser";
    }
    @PatchMapping("/users/updateUser")
    public String updateUser(User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }
}
