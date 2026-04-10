package com.github.tabreubr.musiclass.controllers;

import com.github.tabreubr.musiclass.entities.Classes;
import com.github.tabreubr.musiclass.services.ClassesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/classes")
@RestController
public class ClassesController {

    private final ClassesService classesService;

    public ClassesController(ClassesService classesService) {
        this.classesService = classesService;
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
}
