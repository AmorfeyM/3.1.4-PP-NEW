package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository repository;
    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return repository.findAll();
    }


    @Transactional
    public void saveUser(User user) {
        repository.save(user);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
