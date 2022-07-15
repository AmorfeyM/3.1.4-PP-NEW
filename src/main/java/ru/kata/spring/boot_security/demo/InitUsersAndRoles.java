package ru.kata.spring.boot_security.demo;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class InitUsersAndRoles {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public InitUsersAndRoles(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void createUsers() {
        Role user = new Role("ROLE_USER");
        Role admin = new Role("ROLE_ADMIN");

        roleRepository.save(user);
        roleRepository.save(admin);

        User user1 = new User("zer@mail.ru", "$2a$12$54rDWKSismZ9uGff8bWwvetMn/YqhjzHl0P3D7JxY8GSyXeI2zM9e");//111
        User user2 = new User("morty@mail.ru","$2a$12$54rDWKSismZ9uGff8bWwvetMn/YqhjzHl0P3D7JxY8GSyXeI2zM9e");//111
        User user3 = new User("gena@mail.ru", "$2a$12$54rDWKSismZ9uGff8bWwvetMn/YqhjzHl0P3D7JxY8GSyXeI2zM9e");//111

        user1.setRole(roleRepository.findByName("ROLE_ADMIN"));
        user1.setRole(roleRepository.findByName("ROLE_USER"));
        user2.setRole(roleRepository.findByName("ROLE_ADMIN"));
        user3.setRole(roleRepository.findByName("ROLE_USER"));

        user3.setFirstName("Crocodil");
        user3.setLastName("Aligatorov");
        user3.setAge(40);


        user1.setFirstName("Sergey");
        user1.setLastName("Popov");
        user1.setAge(35);


        user2.setFirstName("Morty");
        user2.setLastName("Smite");
        user2.setAge(14);


        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

    }
}

