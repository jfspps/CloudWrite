package com.example.cloudwrite.JPARepository;

import com.example.cloudwrite.model.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepo extends JpaRepository<Authority, Long> {
}
