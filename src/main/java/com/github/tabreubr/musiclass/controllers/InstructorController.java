package com.github.tabreubr.musiclass.controllers;

import com.github.tabreubr.musiclass.entities.Instructor;
import com.github.tabreubr.musiclass.services.InstructorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/instructors")
@RestController
public class InstructorController {

	private final InstructorService instructorService;

	public InstructorController(InstructorService instructorService) {
		this.instructorService = instructorService;
	}

	@PostMapping
	public ResponseEntity<?> saveInstructor(@RequestBody @Valid Instructor instructor) {
		return ResponseEntity.status(HttpStatus.CREATED).body(instructorService.save(instructor));
	}

	@GetMapping
	public ResponseEntity<?> findAllInstructors() {
		return ResponseEntity.status(HttpStatus.OK).body(instructorService.findAllInstructors());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findInstructorById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(instructorService.findById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateInstructor(@PathVariable Long id, @RequestBody @Valid Instructor instructor) {
		return ResponseEntity.status(HttpStatus.OK).body(instructorService.updateInstructorById(id, instructor));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteInstructor(@PathVariable Long id) {
		instructorService.deleteInstructorById(id);
		return ResponseEntity.noContent().build();
	}
}
