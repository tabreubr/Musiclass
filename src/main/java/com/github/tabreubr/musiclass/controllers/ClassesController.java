package com.github.tabreubr.musiclass.controllers;

import com.github.tabreubr.musiclass.entities.Classes;
import com.github.tabreubr.musiclass.services.ClassesService;
import com.github.tabreubr.musiclass.services.LessonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> saveClass(@RequestBody @Valid Classes classes) {
        return ResponseEntity.status(HttpStatus.CREATED).body(classesService.save(classes));
    }

    @GetMapping
    public ResponseEntity<?> findAllClasses() {
        return ResponseEntity.status(HttpStatus.OK).body(classesService.findAllClasses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findClassById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(classesService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClass(@PathVariable Long id, @RequestBody @Valid Classes classes) {
        return ResponseEntity.status(HttpStatus.OK).body(classesService.updateById(id, classes));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClass(@PathVariable Long id){
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
