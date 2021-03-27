package com.example.cloudwrite.JPARepository;

import com.example.cloudwrite.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    // add custom JPA queries here
    User findByUsername(String username);
}
