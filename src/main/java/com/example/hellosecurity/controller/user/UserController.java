package com.example.hellosecurity.controller.user;

import com.example.hellosecurity.dto.user.UserDto;
import com.example.hellosecurity.model.User;
import com.example.hellosecurity.service.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("userName")
    public UserDto login(@RequestParam("userName") String username, @RequestParam("password") String password) {

        // Here we can verify the userName credentials before granting userName a jwt token
        User user = userService.getUserByNameAndPassword(username, password).orElseThrow(() -> new BadCredentialsException(String.format("User %s not found", username)));
        String token = getJWTToken(user.getUserName(), user.getRole());
        return UserDto.builder().userName(user.getUserName()).token(token).build();

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