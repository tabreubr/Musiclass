package com.github.tabreubr.musiclass.controllers;

import com.github.tabreubr.musiclass.entities.Lesson;
import com.github.tabreubr.musiclass.services.LessonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/lessons")
@RestController
public class LessonController {

	private final LessonService lessonService;

	public LessonController(LessonService lessonService) {
		this.lessonService = lessonService;
	}

	@PostMapping
	public ResponseEntity<?> saveLesson(@RequestBody @Valid Lesson lesson) {
		return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.save(lesson));
	}

	@GetMapping
	public ResponseEntity<?> findAllLessons() {
		return ResponseEntity.status(HttpStatus.OK).body(lessonService.findAllLessons());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findLessonById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(lessonService.findById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateLesson(@PathVariable Long id, @RequestBody @Valid Lesson lesson) {
		return ResponseEntity.status(HttpStatus.OK).body(lessonService.updateLessonById(id, lesson));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteLesson(@PathVariable Long id) {
		lessonService.deleteLessonById(id);
		return ResponseEntity.noContent().build();
	}
}
