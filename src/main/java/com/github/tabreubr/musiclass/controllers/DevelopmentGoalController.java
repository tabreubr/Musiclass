package com.github.tabreubr.musiclass.controllers;

import com.github.tabreubr.musiclass.entities.DevelopmentGoal;
import com.github.tabreubr.musiclass.services.DevelopmentGoalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/development-goals")
@RestController
public class DevelopmentGoalController {

	private final DevelopmentGoalService developmentGoalService;

	public DevelopmentGoalController(DevelopmentGoalService developmentGoalService) {
		this.developmentGoalService = developmentGoalService;
	}

	@PostMapping
	public ResponseEntity<?> saveDevelopmentGoal(@RequestBody @Valid DevelopmentGoal developmentGoal) {
		return ResponseEntity.status(HttpStatus.CREATED).body(developmentGoalService.save(developmentGoal));
	}

	@GetMapping
	public ResponseEntity<?> findAllDevelopmentGoals() {
		return ResponseEntity.status(HttpStatus.OK).body(developmentGoalService.findAllDevelopmentGoals());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findDevelopmentGoalById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(developmentGoalService.findById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateDevelopmentGoal(@PathVariable Long id, @RequestBody @Valid DevelopmentGoal developmentGoal) {
		return ResponseEntity.status(HttpStatus.OK).body(developmentGoalService.updateDevelopmentGoalById(id, developmentGoal));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteDevelopmentGoal(@PathVariable Long id) {
		developmentGoalService.deleteDevelopmentGoalById(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}/complete")
	public ResponseEntity<?> toggleDevelopmentGoal(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(developmentGoalService.toggleCompleted(id));
	}
}
