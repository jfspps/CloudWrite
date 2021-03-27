package com.example.cloudwrite.service;

import com.example.cloudwrite.model.security.User;

import java.util.Optional;

public interface UserService extends BaseService<User, Long>{
    User findByUserName(String username);

}
