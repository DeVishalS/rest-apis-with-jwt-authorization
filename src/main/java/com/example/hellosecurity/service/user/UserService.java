package com.example.hellosecurity.service.user;

import com.example.hellosecurity.dto.user.UserDto;
import com.example.hellosecurity.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserService {
    User createUser(UserDto userDto);

    Optional<User> getUserByName(String userName);

    void unlockAndResetAttemptsAccount(String userName);

    void recordInvalidAttemptForUser(String userName, LocalDateTime unlockTime);
}
