package com.example.demo.services;


import com.example.demo.dto.UserDto;
import com.example.demo.entities.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}


