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
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }
    @GetMapping("/admin/addUserForm")
    public String addUserForm(@ModelAttribute("newUser") User user) {
        return "newUser";
    }

    @PostMapping("/admin/addUser")
    public String addUser(@ModelAttribute("newUser") User user,
                          @ModelAttribute("roles") List<Role> roles) {
        userService.saveUser(user);
        user.setRoleList(roles);
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
        List<Role> listRoles = userService.listRoles();
        model.addAttribute("userToUpdate", user);
        model.addAttribute("listRoles", listRoles);
        return "updateUser";
    }
    @PatchMapping("/admin/updateUser")
    public String updateUser(User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }


}
