package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    void saveUser(User user);
    void deleteById(Long id);

    User findByEmail(String email);
    User findById(Long id);

    void updateUser(User user);


}
