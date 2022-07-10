package ru.kata.spring.boot_security.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private  TestEntityManager entityManager;
    @Autowired
    private  UserRepository userRepo;
    @Autowired
    private  RoleRepository roleRepo;

//    @BeforeAll
//    public static void updateUsersAndRoles() {
//        Role user = new Role("User");
//        Role admin = new Role("Admin");
//        Role customer = new Role("Customer");
//
//        roleRepo.saveAll(List.of(user, admin, customer));
//
//        User newUser = new User();
//        newUser.setEmail("ravikumar@gmail.com");
//        newUser.setPassword("ravi2020");
//        newUser.setFirstName("Ravi");
//        newUser.setLastName("Kumar");
//
//        User savedUser = userRepo.save(newUser);
//    }


    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("ravikumar@gmail.com");
        user.setPassword("ravi2020");
        user.setFirstName("Ravi");
        user.setLastName("Kumar");

        User savedUser = userRepo.save(user);

        User existUser = entityManager.find(User.class, savedUser.getId());

        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
    }

    @Test
    public void testAddRoleToNewUser() {
        Role roleAdmin = roleRepo.findByName("Admin");

        User user = new User();
        user.setEmail("mikes.gates@gmail.com");
        user.setPassword("mike2020");
        user.setFirstName("Mike");
        user.setLastName("Gates");
        user.addRole(roleAdmin);

        User savedUser = userRepo.save(user);

        assertThat(savedUser.getRoles().size()).isEqualTo(1);
    }

    @Test
    public void testAddRoleToExistingUser() {
        User user = userRepo.findById(1L).get();
        Role roleUser = roleRepo.findByName("User");
        Role roleCustomer = new Role(3L);

        user.addRole(roleUser);
        user.addRole(roleCustomer);

        User savedUser = userRepo.save(user);

        assertThat(savedUser.getRoles().size()).isEqualTo(2);
    }
}