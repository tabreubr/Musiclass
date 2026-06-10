package com.github.tabreubr.musiclass.controllers;

import com.github.tabreubr.musiclass.dto.classes.ClassesResponse;
import com.github.tabreubr.musiclass.entities.Student;
import com.github.tabreubr.musiclass.services.ClassesService;
import com.github.tabreubr.musiclass.services.DevelopmentGoalService;
import com.github.tabreubr.musiclass.services.ProgressGoalService;
import com.github.tabreubr.musiclass.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentAreaController {

    private final ClassesService classesService;
    private final DevelopmentGoalService developmentGoalService;
    private final ProgressGoalService progressGoalService;
    private final StudentService studentService;

    public StudentAreaController(ClassesService classesService,
                                 DevelopmentGoalService developmentGoalService,
                                 ProgressGoalService progressGoalService,
                                 StudentService studentService) {
        this.classesService = classesService;
        this.developmentGoalService = developmentGoalService;
        this.progressGoalService = progressGoalService;
        this.studentService = studentService;
    }

    @GetMapping("/classes")
    public ResponseEntity<List<ClassesResponse>> getMyClasses() {
        return ResponseEntity.ok(classesService.findAllByAuthenticatedStudent());
    }

    @GetMapping("/goals")
    public ResponseEntity<?> getMyGoals() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentService.findByEmail(email);
        return ResponseEntity.ok(Map.of(
                "developmentGoals", developmentGoalService.findAllByStudent(student),
                "progressGoals", progressGoalService.findAllByStudent(student)
        ));
    }

    @GetMapping("/classes/{id}")
    public ResponseEntity<ClassesResponse> getMyClassById(@PathVariable Long id) {
        return ResponseEntity.ok(classesService.findByIdForStudent(id));
    }
}
