package com.github.tabreubr.musiclass.dto.lesson;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LessonRequest(
        @NotNull Long classesId,
        @NotBlank String methodName,
        Integer page,
        Integer lessonNumber
) {
}