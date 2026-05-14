package com.github.tabreubr.musiclass.dto.classes;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ClassesRequest(

        @NotNull(message = "Date is required")
        LocalDateTime date,

        String observations,

        Boolean passed,

        @NotNull(message = "Student is required")
        Long studentId
) {
}