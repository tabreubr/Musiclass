package com.github.tabreubr.musiclass.controllers;

import com.github.tabreubr.musiclass.dto.classes.ClassesRequest;
import com.github.tabreubr.musiclass.dto.classes.ClassesResponse;
import com.github.tabreubr.musiclass.dto.classes.ClassesUpdateRequest;
import com.github.tabreubr.musiclass.entities.Classes;
import com.github.tabreubr.musiclass.services.ClassesService;
import com.github.tabreubr.musiclass.services.LessonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/classes")
@RestController
public class ClassesController {

    private final ClassesService classesService;
    private final LessonService lessonService;

    public ClassesController(ClassesService classesService,
                             LessonService lessonService) {
        this.classesService = classesService;
        this.lessonService = lessonService;
    }

    @PostMapping
    public ResponseEntity<ClassesResponse> saveClass(@RequestBody @Valid ClassesRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(classesService.save(request));
    }

    @GetMapping
    public ResponseEntity<List<ClassesResponse>> findAllClasses() {
        return ResponseEntity.ok(classesService.findAllClasses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassesResponse> findClassById(@PathVariable Long id){
        return ResponseEntity.ok(classesService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassesResponse> updateClass(@PathVariable Long id,
                                                       @RequestBody ClassesUpdateRequest request) {
        return ResponseEntity.ok(classesService.updateById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id){
        classesService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/lessons")
    public ResponseEntity<?> addLesson(@PathVariable Long id,
                                       @RequestBody Map<String, Object> body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(lessonService.addToClass(id, body));
    }

    @PatchMapping("/{id}/lessons/{lessonId}")
    public ResponseEntity<?> updateLesson(@PathVariable Long id,
                                          @PathVariable Long lessonId,
                                          @RequestBody Map<String, Boolean> body) {
        return ResponseEntity.ok(lessonService.updateCompleted(lessonId, body.get("completed")));
    }

    @DeleteMapping("/{id}/lessons/{lessonId}")
    public ResponseEntity<?> deleteLesson(@PathVariable Long id,
                                          @PathVariable Long lessonId) {
        lessonService.deleteLessonById(lessonId);
        return ResponseEntity.noContent().build();
    }
}
