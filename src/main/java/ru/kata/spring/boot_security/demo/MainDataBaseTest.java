package ru.kata.spring.boot_security.demo;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class MainDataBaseTest {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    public MainDataBaseTest(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void createUsers() {
        Role user = new Role("ROLE_USER");
        Role admin = new Role("ROLE_ADMIN");

        roleRepository.save(user);
        roleRepository.save(admin);

        User user1 = new User("Amorfeym", "$2a$12$54rDWKSismZ9uGff8bWwvetMn/YqhjzHl0P3D7JxY8GSyXeI2zM9e");//111
        User user2 = new User("Morty","$2a$12$54rDWKSismZ9uGff8bWwvetMn/YqhjzHl0P3D7JxY8GSyXeI2zM9e");//111
        User user3 = new User("Gena", "$2a$12$54rDWKSismZ9uGff8bWwvetMn/YqhjzHl0P3D7JxY8GSyXeI2zM9e");//111

        user1.addRole(roleRepository.findByName("ROLE_ADMIN"));
        user1.addRole(roleRepository.findByName("ROLE_USER"));
        user2.addRole(roleRepository.findByName("ROLE_ADMIN"));
        user3.addRole(roleRepository.findByName("ROLE_USER"));

        user3.setFirstName("Crocodil");
        user3.setLastName("Aligatorov");
        user3.setEmail("gena@mail.ru");

        user1.setFirstName("Sergey");
        user1.setLastName("Popov");
        user1.setEmail("zer@mail.ru");

        user2.setFirstName("Morty");
        user2.setLastName("Smite");
        user2.setEmail("morty@mail.ru");

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        System.out.println(userRepository.findByUsername("Gena"));
    }
}

