package com.example.cloudwrite.converter;

import com.example.cloudwrite.model.security.User;
import com.example.cloudwrite.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
public class StringIDToUser implements Converter<String, User> {

    private final UserService userService;

    @Synchronized
    @Nullable
    @Override
    public User convert(String id) {
        return userService.findById(Long.valueOf(id));
    }
}