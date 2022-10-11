package com.example.hellosecurity.controller.user;

import com.example.hellosecurity.model.User;
import com.example.hellosecurity.service.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

record UserJwtTokenResponse(String userName, String token) {
}

@RestController
public class UserController {

    @Value("${hello-security.jwt.secret:default-secret-key}")
    private String secretKey;

    @Value("${hello-security.jwt.ttl:120000}")
    private Long ttlDuration;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.passwordEncoder = encoder;
    }

    @Operation
    @SecurityRequirements
    @PostMapping("user")
    public UserJwtTokenResponse login(@RequestParam("userName") String userName, @RequestParam("password") String password) {

        // Here we can verify the userName credentials before granting userName a jwt token
        User user = userService.getUserByName(userName)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, String.format("User %s not found", userName))
                );
        if (passwordEncoder.matches(password, user.getPassword())) {
            String token = getJWTToken(user.getUserName(), user.getRole());
            return new UserJwtTokenResponse(userName, token);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "UserName or Password is incorrect");
        }

    }

    private String getJWTToken(String username, String role) {

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(role);

        String token = Jwts
                .builder()
                .setId("helloSecurityApp")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ttlDuration))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}