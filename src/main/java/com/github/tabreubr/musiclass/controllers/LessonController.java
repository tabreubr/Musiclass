package com.github.tabreubr.musiclass.controllers;

import com.github.tabreubr.musiclass.dto.lesson.LessonResponse;
import com.github.tabreubr.musiclass.services.LessonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/lessons")
@RestController
public class LessonController {

	private final LessonService lessonService;

	public LessonController(LessonService lessonService) {
		this.lessonService = lessonService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<LessonResponse> findLessonById(@PathVariable Long id) {
		return ResponseEntity.ok(lessonService.findByIdValidated(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
		lessonService.deleteLessonById(id);
		return ResponseEntity.noContent().build();
	}
}
