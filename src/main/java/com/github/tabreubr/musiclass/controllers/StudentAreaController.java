package com.github.tabreubr.musiclass.controllers;

import com.github.tabreubr.musiclass.dto.classes.ClassesResponse;
import com.github.tabreubr.musiclass.services.ClassesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentAreaController {

    private final ClassesService classesService;

    public StudentAreaController(ClassesService classesService) {
        this.classesService = classesService;
    }

    @GetMapping("/classes")
    public ResponseEntity<List<ClassesResponse>> getMyClasses(){
        return ResponseEntity.ok(classesService.findAllByAuthenticatedStudent());
    }
}
