package ru.kata.spring.boot_security.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class User implements UserDetails {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id")
   private Long id;

   @Column(name = "firstname")
   private String firstName;

   @Column(name = "lastname")
   private String lastName;

   @Column(name = "age")
   private int age;

   @Column(name = "email", unique = true, nullable = false)
   private String username;

   @Column(name = "password")
   private String password;

   @ManyToMany(fetch = FetchType.LAZY)
   @JoinTable(
           name = "users_roles",
           joinColumns = @JoinColumn(name = "user_id"),
           inverseJoinColumns = @JoinColumn(name = "role_id")
   )
   @Fetch(FetchMode.JOIN)
   @ToString.Exclude
   private Set<Role> roles = new HashSet<>();

   public User() {
   }
   public User(String username, String password) {
      this.username = username;
      this.password = password;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return roles;
   }

   @Override
   public String getUsername() {
      return username;
   }
   @Override
   @JsonIgnore
   public boolean isAccountNonExpired() {
      return true;
   }
   @Override
   @JsonIgnore
   public boolean isAccountNonLocked() {
      return true;
   }
   @Override
   @JsonIgnore
   public boolean isCredentialsNonExpired() {
      return true;
   }
   @Override
   @JsonIgnore
   public boolean isEnabled() {
      return true;
   }

   public void setRole(Role role) {
      roles.add(role);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      User user = (User) o;
      return id != null && Objects.equals(id, user.id);
   }

   @Override
   public int hashCode() {
      return getClass().hashCode();
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", firstName='" + firstName + '\'' +
              ", lastName='" + lastName + '\'' +
              ", email='" + username + '\'' +
              ", roleList=" + roles +
              '}';
   }
}
