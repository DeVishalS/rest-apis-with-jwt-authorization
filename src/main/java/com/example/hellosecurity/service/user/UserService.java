package com.example.hellosecurity.service.user;

import com.example.hellosecurity.dto.user.UserDto;
import com.example.hellosecurity.model.User;

import java.util.Optional;

public interface UserService {
    User createUser(UserDto userDto);

    Optional<User> getUserByNameAndPassword(String userName, String password);
}
