package com.github.tabreubr.musiclass.controllers;

import com.github.tabreubr.musiclass.dto.student.StudentRequest;
import com.github.tabreubr.musiclass.dto.student.StudentResponse;
import com.github.tabreubr.musiclass.entities.Student;
import com.github.tabreubr.musiclass.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/students")
@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentResponse> saveStudent(@RequestBody @Valid StudentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.save(request));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> findAllStudents() {
        return ResponseEntity.ok(studentService.findAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> findStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable Long id, @RequestBody @Valid StudentRequest request) {
        return ResponseEntity.ok(studentService.updateById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }
}
