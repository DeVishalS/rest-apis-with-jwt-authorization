package com.example.hellosecurity.controller.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloController {

    @GetMapping("userName")
    public String doHelloUser() {
        return "Hello User";
    }

    @GetMapping("admin")
    public String doHelloAdmin() {
        return "Hello Admin";

    }
}