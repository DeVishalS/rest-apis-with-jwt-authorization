package com.example.hellosecurity.controller.hello;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloController {

//    @Operation(security = { @SecurityRequirement(name = "bearer-jwt") })
    @GetMapping("user")
    public String doHelloUser() {
        return "Hello User";
    }

    @GetMapping("admin")
    public String doHelloAdmin() {
        return "Hello Admin";

    }
}