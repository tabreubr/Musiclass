package com.github.tabreubr.musiclass.dto.instructor;

import com.github.tabreubr.musiclass.entities.Instructor;
import com.github.tabreubr.musiclass.enums.UserRole;

public record InstructorResponse(
        Long id,
        String name,
        String email,
        UserRole role
) {

    public static InstructorResponse from(Instructor instructor) {
        return new InstructorResponse(
                instructor.getId(),
                instructor.getName(),
                instructor.getEmail(),
                instructor.getRole()
        );
    }
}
