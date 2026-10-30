package com.github.tabreubr.musiclass.dto.student;

import jakarta.validation.constraints.NotBlank;

public record StudentInviteRequest(
        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Password is required")
        String password
) {
}
