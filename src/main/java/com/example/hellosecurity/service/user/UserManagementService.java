package com.example.hellosecurity.service.user;

import com.example.hellosecurity.dto.user.UserDto;
import com.example.hellosecurity.model.User;
import com.example.hellosecurity.repository.user.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserManagementService implements UserService {

    private final UserRepository userRepo;

    public UserManagementService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User createUser(UserDto userDto){
        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        return userRepo.save(user);
    }
    @Override
    public Optional<User> getUserByName(String userName){
        return userRepo.findByUserNameIgnoreCase(userName);
    }

    @Override
    @Transactional
    public void unlockAndResetAttemptsAccount(String userName) {
        userRepo.unlockAndResetAttemptsCount(userName);
    }

    @Override
    @Transactional
    public void recordInvalidAttemptForUser(String userName, LocalDateTime unlockTime) {
        userRepo.recordInvalidAttemptsForUser(userName,unlockTime);
    }

}
