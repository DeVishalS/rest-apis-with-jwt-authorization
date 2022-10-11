package com.example.hellosecurity.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserJwtTokenResponse(String userName, @Schema(type = "password") String token) {
}
