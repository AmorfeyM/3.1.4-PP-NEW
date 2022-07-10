package ru.kata.spring.boot_security.demo.model;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;

   @Column(name = "firstname")
   private String firstName;

   @Column(name = "lastname")
   private String lastName;

   @Column(name = "email")
   private String email;

   public User() {
   }

   public User(String firstName, String lastName, String email) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
   }

   public Long getId() {
      return id;
   }

   public String getFirstName() {
      return firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public String getEmail() {
      return email;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   @Override
   public String toString() {
      return "User{" +
              "firstName='" + firstName + '\'' +
              ", lastName='" + lastName + '\'' +
              ", email='" + email + '\'' +
              '}';
   }
}
