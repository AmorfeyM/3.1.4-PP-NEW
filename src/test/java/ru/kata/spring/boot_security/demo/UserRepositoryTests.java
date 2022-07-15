package ru.kata.spring.boot_security.demo;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    public void testCreateUser() {
        User user1 = new User("AmorfeyM", "111");

        User savedUser = userRepo.save(user1);

        User existUser = entityManager.find(User.class, savedUser.getId());

        assertThat(user1.getUsername()).isEqualTo(existUser.getUsername());
    }

    @Test
    public void testAddRoleToNewUser() {
        Role roleAdmin = roleRepo.findByName("Admin");

        User user2 = new User("Rick", "222");
        user2.setRole(roleAdmin);

        User savedUser = userRepo.save(user2);

        assertThat(savedUser.getRoleList().size()).isEqualTo(1);
    }

    @Test
    public void testAddRoleToExistingUser() {
        Role user = new Role("USER");
        Role admin = new Role("ADMIN");

        roleRepo.save(user);
        roleRepo.save(admin);

        User user1 = new User("Amorfeym", "$2a$12$54rDWKSismZ9uGff8bWwvetMn/YqhjzHl0P3D7JxY8GSyXeI2zM9e");//111
        User user2 = new User("Morty","$2a$12$54rDWKSismZ9uGff8bWwvetMn/YqhjzHl0P3D7JxY8GSyXeI2zM9e");//111
        User user3 = new User("Gena", "$2a$12$54rDWKSismZ9uGff8bWwvetMn/YqhjzHl0P3D7JxY8GSyXeI2zM9e");//111


        user1.setRole(roleRepo.findByName("ADMIN"));
        user1.setRole(roleRepo.findByName("USER"));
        user2.setRole(roleRepo.findByName("ADMIN"));
        user3.setRole(roleRepo.findByName("USER"));

        userRepo.save(user1);
        userRepo.save(user2);
        userRepo.save(user3);

        User userTest = userRepo.findByUsername("Gena");
        Role roleUser = roleRepo.findByName("User");
        Role roleAdmin = roleRepo.findByName("Admin");

        userTest.setRole(roleUser);
        userTest.setRole(roleAdmin);

        User savedUser = userRepo.save(userTest);

        List<Role> roleList = userRepo.findByUsername("Gena").getRoleList();

        assertThat(savedUser.getRoleList().size()).isEqualTo(3);
        assertThat(roleList.size()).isEqualTo(3);

    }
}