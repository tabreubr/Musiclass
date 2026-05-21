package com.github.tabreubr.musiclass.dto.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StudentRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Instrument is required")
        Long instrumentId
) {
}
