package com.example.hellosecurity.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserDto(
        String userName,
        String password,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY) String token,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY) @JsonIgnore String role,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY) @JsonIgnore Boolean active,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY) @JsonIgnore Integer invalidAttempts,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY) @JsonIgnore LocalDateTime lockedUntil
        )
{ }