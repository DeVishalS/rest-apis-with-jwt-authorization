package com.example.hellosecurity.controller.user;

import com.example.hellosecurity.dto.error.StandardErrorResponse;
import com.example.hellosecurity.dto.user.UserJwtTokenResponse;
import com.example.hellosecurity.model.User;
import com.example.hellosecurity.service.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import static com.example.hellosecurity.dto.response.StandardResponse.buildFailureResponse;
import static com.example.hellosecurity.dto.response.StandardResponse.buildSuccessResponse;

@RestController
@CrossOrigin("*")
public class UserController {

    public static final String ACCOUNT_LOCKED_DUE_TO_EXCEEDED_INVALID_ATTEMPTS = "ACCOUNT_LOCKED_DUE_TO_EXCEEDED_INVALID_ATTEMPTS";
    public static final String INCORRECT_USER_CREDENTIALS = "INCORRECT_USER_CREDENTIALS";
    @Value("${hello-security.jwt.secret:default-secret-key}")
    private String secretKey;

    @Value("${hello-security.login.allowed.attempts:5}")
    private Integer allowedAttempts;

    @Value("${hello-security.account.lock-duration:24}")
    private Integer lockDuration;

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
    @PostMapping(value = "user")
    @CrossOrigin("*")
    public ResponseEntity login(@RequestParam("userName") String userName, @RequestParam("password") String password) {

        User user = userService.getUserByName(userName).orElse(null);
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(buildFailureResponse(new StandardErrorResponse(4000, INCORRECT_USER_CREDENTIALS,String.format("User %s not found", userName) )));

        }

        if (user.getInvalidAttempts() >= allowedAttempts && (user.getLockedUntil() != null && user.getLockedUntil().isAfter(LocalDateTime.now(ZoneOffset.UTC)))) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                            buildFailureResponse(
                                    new StandardErrorResponse(4000, ACCOUNT_LOCKED_DUE_TO_EXCEEDED_INVALID_ATTEMPTS, String.format("Account is Locked until %s", user.getLockedUntil())))
                    );

        } else {

            if (passwordEncoder.matches(password, user.getPassword())) {

                String token = getJWTToken(user.getUserName(), user.getRole());
                userService.unlockAndResetAttemptsAccount(userName);
                return ResponseEntity.ok(buildSuccessResponse(new UserJwtTokenResponse(userName, token)));

            } else {

                // Checking user account had invalid attempts
                if (user.getInvalidAttempts() + 1 >= allowedAttempts) {

                    LocalDateTime unlockTime = LocalDateTime.now(ZoneOffset.UTC).plusHours(lockDuration);
                    userService.recordInvalidAttemptForUser(userName, unlockTime);
                    return ResponseEntity
                            .status(HttpStatus.UNAUTHORIZED)
                            .body(buildFailureResponse(new StandardErrorResponse(4000, ACCOUNT_LOCKED_DUE_TO_EXCEEDED_INVALID_ATTEMPTS, String.format("Account Locked until %s", unlockTime))));
                } else {

                    userService.recordInvalidAttemptForUser(userName, null);
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(buildFailureResponse(new StandardErrorResponse(4000, INCORRECT_USER_CREDENTIALS, String.format("Invalid Attempt No. %d. You have %d attempts remaining", user.getInvalidAttempts() + 1, allowedAttempts - user.getInvalidAttempts() - 1))));

                }
            }
        }


    }

    @PostMapping("unlock/user/{userName}")
    @SecurityRequirements
    public void unlockUserAccount(@PathVariable("userName") String userName){
        userService.unlockAndResetAttemptsAccount(userName);
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