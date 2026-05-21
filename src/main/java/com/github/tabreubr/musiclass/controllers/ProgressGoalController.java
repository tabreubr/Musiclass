package com.github.tabreubr.musiclass.controllers;

import com.github.tabreubr.musiclass.entities.ProgressGoal;
import com.github.tabreubr.musiclass.services.ProgressGoalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/progress-goals")
@RestController
public class ProgressGoalController {

	private final ProgressGoalService progressGoalService;

	public ProgressGoalController(ProgressGoalService progressGoalService) {
		this.progressGoalService = progressGoalService;
	}

	@PostMapping
	public ResponseEntity<?> saveProgressGoal(@RequestBody Map<String, Object> body) {
		return ResponseEntity.status(HttpStatus.CREATED).body(progressGoalService.saveFromBody(body));
	}

	@GetMapping
	public ResponseEntity<?> findAllProgressGoals() {
		return ResponseEntity.status(HttpStatus.OK).body(progressGoalService.findAllProgressGoals());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findProgressGoalById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(progressGoalService.findById(id));
	}

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProgressGoal(@PathVariable Long id, @RequestBody ProgressGoal progressGoal) {
        return ResponseEntity.status(HttpStatus.OK).body(progressGoalService.updateProgressGoalById(id, progressGoal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProgressGoal(@PathVariable Long id) {
        progressGoalService.deleteProgressGoalById(id);
        return ResponseEntity.noContent().build();
    }

	@PatchMapping("/{id}/complete")
	public ResponseEntity<?> toggleProgressGoal(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(progressGoalService.toggleCompleted(id));
	}
}
