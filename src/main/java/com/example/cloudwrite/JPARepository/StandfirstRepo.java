package com.example.cloudwrite.JPARepository;

import com.example.cloudwrite.model.Standfirst;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StandfirstRepo extends JpaRepository<Standfirst, Long> {
}
