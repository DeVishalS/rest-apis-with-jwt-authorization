package com.example.hellosecurity.dto.user;

import lombok.Builder;

@Builder
public record UserDto(String userName, String password, String token, String role){}