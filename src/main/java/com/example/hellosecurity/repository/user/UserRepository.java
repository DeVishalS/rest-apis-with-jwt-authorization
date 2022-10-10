package com.example.hellosecurity.repository.user;

import com.example.hellosecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserNameIgnoreCaseAndPassword(String userName, String password);
}