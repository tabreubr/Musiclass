package com.github.tabreubr.musiclass.dto.student;

import com.github.tabreubr.musiclass.entities.Student;

public record StudentSummary(
        Long id,
        String name,
        String email
) {
    public static StudentSummary from(Student student) {
        return new StudentSummary(
                student.getId(),
                student.getName(),
                student.getEmail()
        );
    }
}