package com.example.hellosecurity.controller.user;

import com.example.hellosecurity.model.User;
import com.example.hellosecurity.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Mock
    PasswordEncoder passwordEncoder;
    private User user = new User(101L, "someuser", "somepwd", "ROLE_USER");;

    @Test
    void test_Login_when_user_found_with_correct_credentials() {
        when(userService.getUserByName(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(),anyString())).thenReturn(true);
        UserJwtTokenResponse jwtTokenResponse = userController.login("someuser", "somepwd");
        assertNotNull(jwtTokenResponse);
        assertEquals("someuser", jwtTokenResponse.userName());
    }

    @Test
    void test_Login_when_user_found_with_incorrect_credentials() {

        when(userService.getUserByName(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(),anyString())).thenReturn(false);
        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> userController.login("someuser", "somepwd"));
        assertEquals(HttpStatus.UNAUTHORIZED, responseStatusException.getStatus());
        assertEquals("UserName or Password is incorrect", responseStatusException.getReason());
    }

    @Test
    void test_Login_when_user_not_found() {
        when(userService.getUserByName(anyString())).thenReturn(Optional.empty());
        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> userController.login("someuser", "somepwd"));
        assertEquals(HttpStatus.UNAUTHORIZED, responseStatusException.getStatus());
        assertEquals("User someuser not found", responseStatusException.getReason());
    }



}