package com.github.tabreubr.musiclass.dto.student;

import com.github.tabreubr.musiclass.dto.instructor.InstructorResponse;
import com.github.tabreubr.musiclass.entities.Instrument;
import com.github.tabreubr.musiclass.entities.Student;

public record StudentResponse(
        Long id,
        String name,
        String email,
        InstructorResponse instructor,
        Instrument instrument
) {

    public static StudentResponse from(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getInstructor() != null ? InstructorResponse.from(student.getInstructor()) : null,
                student.getInstrument()
        );
    }
}
