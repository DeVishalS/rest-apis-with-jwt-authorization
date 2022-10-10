package com.example.hellosecurity.controller.user;

import com.example.hellosecurity.model.User;
import com.example.hellosecurity.service.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation
    @SecurityRequirements
    @PostMapping("user")
    public UserJwtTokenResponse login(@RequestParam("userName") String userName, @RequestParam("password") String password) {

        // Here we can verify the userName credentials before granting userName a jwt token
        User user = userService.getUserByNameAndPassword(userName).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, String.format("User %s not found", userName)));
        if (new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            String token = getJWTToken(user.getUserName(), user.getRole());
            return new UserJwtTokenResponse(userName, token);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "UserName or Password is incorrect");
        }

    }

    private String getJWTToken(String username, String role) {
        String secretKey = "my-secret-key";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(role);

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}