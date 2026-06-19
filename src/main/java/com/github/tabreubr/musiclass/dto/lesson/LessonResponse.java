package com.github.tabreubr.musiclass.dto.lesson;

import com.github.tabreubr.musiclass.entities.Lesson;

public record LessonResponse(
        Long id,
        String methodName,
        Integer page,
        Integer lessonNumber,
        Boolean completed
) {
    public static LessonResponse from(Lesson lesson) {
        return new LessonResponse(
                lesson.getId(),
                lesson.getMethodName() != null ? lesson.getMethodName().getName() : null,
                lesson.getPage(),
                lesson.getLessonNumber(),
                lesson.getCompleted()
        );
    }
}
