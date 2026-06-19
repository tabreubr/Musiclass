package com.github.tabreubr.musiclass.controllers;

import com.github.tabreubr.musiclass.dto.progressGoal.ProgressGoalRequest;
import com.github.tabreubr.musiclass.dto.progressGoal.ProgressGoalResponse;
import com.github.tabreubr.musiclass.services.ProgressGoalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/progress-goals")
@RestController
public class ProgressGoalController {

    private final ProgressGoalService progressGoalService;

    public ProgressGoalController(ProgressGoalService progressGoalService) {
        this.progressGoalService = progressGoalService;
    }

    @PostMapping
    public ResponseEntity<ProgressGoalResponse> saveProgressGoal(
            @RequestBody @Valid ProgressGoalRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(progressGoalService.save(request));
    }

    @GetMapping
    public ResponseEntity<List<ProgressGoalResponse>> findAllProgressGoals() {
        return ResponseEntity.ok(progressGoalService.findAllProgressGoals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgressGoalResponse> findProgressGoalById(@PathVariable Long id) {
        return ResponseEntity.ok(progressGoalService.findByIdValidated(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgressGoalResponse> updateProgressGoal(
            @PathVariable Long id, @RequestBody @Valid ProgressGoalRequest request) {
        return ResponseEntity.ok(progressGoalService.updateProgressGoalById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgressGoal(@PathVariable Long id) {
        progressGoalService.deleteProgressGoalById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<ProgressGoalResponse> toggleProgressGoal(@PathVariable Long id) {
        return ResponseEntity.ok(progressGoalService.toggleCompleted(id));
    }
}
