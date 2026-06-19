package com.github.tabreubr.musiclass.dto.student;

import com.github.tabreubr.musiclass.dto.developmentGoal.DevelopmentGoalResponse;
import com.github.tabreubr.musiclass.dto.progressGoal.ProgressGoalResponse;

import java.util.List;

public record StudentGoalsResponse(
        List<DevelopmentGoalResponse> developmentGoals,
        List<ProgressGoalResponse> progressGoals
) {
}