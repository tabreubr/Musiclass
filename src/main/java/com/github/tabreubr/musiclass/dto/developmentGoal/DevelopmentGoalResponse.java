package com.github.tabreubr.musiclass.dto.developmentGoal;

import com.github.tabreubr.musiclass.entities.DevelopmentGoal;

import java.time.LocalDate;

public record DevelopmentGoalResponse(
        Long id,
        String description,
        LocalDate deadline,
        Boolean completed,
        Long studentId,
        String studentName
) {
    public static DevelopmentGoalResponse from(DevelopmentGoal goal) {
        return new DevelopmentGoalResponse(
                goal.getId(),
                goal.getDescription(),
                goal.getDeadline(),
                goal.getCompleted(),
                goal.getStudent() != null ? goal.getStudent().getId() : null,
                goal.getStudent() != null ? goal.getStudent().getName() : null
        );
    }
}
