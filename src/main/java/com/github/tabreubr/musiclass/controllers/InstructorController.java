package com.github.tabreubr.musiclass.controllers;

import com.github.tabreubr.musiclass.dto.instructor.InstructorRequest;
import com.github.tabreubr.musiclass.dto.instructor.InstructorResponse;
import com.github.tabreubr.musiclass.entities.Instructor;
import com.github.tabreubr.musiclass.services.InstructorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/instructors")
@RestController
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @PostMapping
    public ResponseEntity<InstructorResponse> saveInstructor(@RequestBody @Valid InstructorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(instructorService.save(request));
    }

    @GetMapping
    public ResponseEntity<List<InstructorResponse>> findAllInstructors() {
        return ResponseEntity.ok(instructorService.findAllInstructors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructorResponse> findInstructorById(@PathVariable Long id) {
        return ResponseEntity.ok(instructorService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstructorResponse> updateInstructor(@PathVariable Long id,
                                                               @RequestBody @Valid InstructorRequest request) {
        return ResponseEntity.ok(instructorService.updateInstructorById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
        instructorService.deleteInstructorById(id);
        return ResponseEntity.noContent().build();
    }
}
