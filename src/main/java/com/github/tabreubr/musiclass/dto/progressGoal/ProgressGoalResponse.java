package com.github.tabreubr.musiclass.dto.progressGoal;

import com.github.tabreubr.musiclass.entities.ProgressGoal;

import java.time.LocalDate;

public record ProgressGoalResponse(
        Long id,
        LocalDate deadline,
        Integer targetLessonNumber,
        Boolean completed,
        Long studentId,
        String studentName,
        String methodName
) {
    public static ProgressGoalResponse from(ProgressGoal goal) {
        return new ProgressGoalResponse(
                goal.getId(),
                goal.getDeadline(),
                goal.getTargetLessonNumber(),
                goal.getCompleted(),
                goal.getStudent() != null ? goal.getStudent().getId() : null,
                goal.getStudent() != null ? goal.getStudent().getName() : null,
                goal.getMethod() != null ? goal.getMethod().getName() : null
        );
    }
}