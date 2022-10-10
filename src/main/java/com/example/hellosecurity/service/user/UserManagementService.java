package com.example.hellosecurity.service.user;

import com.example.hellosecurity.dto.user.UserDto;
import com.example.hellosecurity.model.User;
import com.example.hellosecurity.repository.user.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public Optional<User> getUserByNameAndPassword(String userName, String password){
        return userRepo.findByUserNameIgnoreCaseAndPassword(userName, new BCryptPasswordEncoder().encode(password));
    }
}
