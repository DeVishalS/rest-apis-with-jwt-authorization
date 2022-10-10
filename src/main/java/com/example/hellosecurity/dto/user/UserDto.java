package com.example.hellosecurity.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record UserDto(
    String userName,
    String password,
    @Schema(accessMode = Schema.AccessMode.READ_ONLY) String token,
    @Schema(accessMode = Schema.AccessMode.READ_ONLY) @JsonIgnore String role
)
{ }