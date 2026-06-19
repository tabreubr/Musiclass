package com.github.tabreubr.musiclass.dto.progressGoal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ProgressGoalRequest(
        @NotNull Long studentId,
        @NotBlank String methodName,
        @NotNull Integer targetLessonNumber,
        LocalDate deadline
) {
}