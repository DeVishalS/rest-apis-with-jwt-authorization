package com.example.hellosecurity;

import com.example.hellosecurity.dto.user.UserDto;
import com.example.hellosecurity.service.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class HelloSecurityApplication {

    public static final Logger logger = LogManager.getLogger(HelloSecurityApplication.class);
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    public static void main(String[] args) {
        SpringApplication.run(HelloSecurityApplication.class, args);
    }


    @EventListener(ApplicationReadyEvent.class)
    private void setUpUsers() {
        logger.info("Creating Basic Users for System");

        userService.createUser(
                UserDto.builder()
                        .userName("Admin").password(encoder.encode("admin")).role("ROLE_ADMIN")
                        .active(true).invalidAttempts(0).lockedUntil(null)
                        .build()
        );
        userService.createUser(
                UserDto.builder()
                        .userName("user1").password(encoder.encode("user1")).role("ROLE_USER")
                        .active(true).invalidAttempts(0).lockedUntil(null)
                        .build()
        );
        userService.createUser(
                UserDto.builder()
                        .userName("user2").password(encoder.encode("user2")).role("ROLE_USER")
                        .active(true).invalidAttempts(0).lockedUntil(null)
                        .build());

        logger.info("System Users Created");
    }
}

