package com.github.tabreubr.musiclass.dto.instructor;

import com.github.tabreubr.musiclass.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InstructorRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is required")
        String password,

        @NotNull(message = "Role is required")
        UserRole role
) {
}
