package com.github.tabreubr.musiclass.dto.developmentGoal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DevelopmentGoalRequest(
        @NotBlank String description,
        LocalDate deadline,
        @NotNull Long studentId
) {
}