package com.example.cloudwrite.service.SDjpa.security;

import com.example.cloudwrite.JPARepository.UserRepo;
import com.example.cloudwrite.model.security.User;
import com.example.cloudwrite.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Profile("SDjpa")
public class UserSDjpaService implements UserService {

    private final UserRepo userRepo;

    public UserSDjpaService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User save(User object) {
        return userRepo.save(object);
    }

    @Override
    public User findById(Long aLong) {
        return userRepo.findById(aLong).orElse(null);
    }

    @Override
    public Set<User> findAll() {
        Set<User> users = new HashSet<>();
        users.addAll(userRepo.findAll());
        return users;
    }

    @Override
    public void delete(User objectT) {
        userRepo.delete(objectT);
    }

    @Override
    public void deleteById(Long aLong) {
        userRepo.deleteById(aLong);
    }

    @Override
    public User findByUserName(String username) {
        return userRepo.findByUsername(username);
    }
}
