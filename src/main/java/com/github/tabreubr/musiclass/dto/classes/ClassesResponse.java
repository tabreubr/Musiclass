package com.github.tabreubr.musiclass.dto.classes;

import com.github.tabreubr.musiclass.dto.instructor.InstructorResponse;
import com.github.tabreubr.musiclass.dto.student.StudentResponse;
import com.github.tabreubr.musiclass.dto.student.StudentSummary;
import com.github.tabreubr.musiclass.entities.Classes;
import com.github.tabreubr.musiclass.entities.Lesson;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record ClassesResponse(
        Long id,
        LocalDateTime date,
        String observations,
        Boolean passed,
        StudentSummary student,
        InstructorResponse instructor,
        List<Lesson> lessons
) {
    public static ClassesResponse from(Classes classes) {
        return new ClassesResponse(
                classes.getId(),
                classes.getDate(),
                classes.getObservations(),
                classes.getPassed(),
                classes.getStudent() != null ? StudentSummary.from(classes.getStudent()) : null,
                classes.getInstructor() != null ? InstructorResponse.from(classes.getInstructor()) : null,
                classes.getLessons()
        );
    }
}
