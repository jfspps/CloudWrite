package com.example.cloudwrite.service.SDjpa.security;

import com.example.cloudwrite.JPARepository.AuthorityRepo;
import com.example.cloudwrite.model.security.Authority;
import com.example.cloudwrite.service.AuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Profile("SDjpa")
public class AuthoritySDjpaService implements AuthorityService {

    private final AuthorityRepo authorityRepo;

    public AuthoritySDjpaService(AuthorityRepo authorityRepo) {
        this.authorityRepo = authorityRepo;
    }

    @Override
    public Authority save(Authority object) {
        return authorityRepo.save(object);
    }

    @Override
    public Authority findById(Long aLong) {
        return authorityRepo.findById(aLong).orElse(null);
    }

    @Override
    public Set<Authority> findAll() {
        Set<Authority> authorities = new HashSet<>();
        authorities.addAll(authorityRepo.findAll());
        return authorities;
    }

    @Override
    public void delete(Authority objectT) {
        authorityRepo.delete(objectT);
    }

    @Override
    public void deleteById(Long aLong) {
        authorityRepo.deleteById(aLong);
    }
}
