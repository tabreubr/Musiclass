package com.github.tabreubr.musiclass.controllers;

import com.github.tabreubr.musiclass.dto.developmentGoal.DevelopmentGoalRequest;
import com.github.tabreubr.musiclass.dto.developmentGoal.DevelopmentGoalResponse;
import com.github.tabreubr.musiclass.services.DevelopmentGoalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/development-goals")
@RestController
public class DevelopmentGoalController {

    private final DevelopmentGoalService developmentGoalService;

    public DevelopmentGoalController(DevelopmentGoalService developmentGoalService) {
        this.developmentGoalService = developmentGoalService;
    }

    @PostMapping
    public ResponseEntity<DevelopmentGoalResponse> saveDevelopmentGoal(
            @RequestBody @Valid DevelopmentGoalRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(developmentGoalService.save(request));
    }

    @GetMapping
    public ResponseEntity<List<DevelopmentGoalResponse>> findAllDevelopmentGoals() {
        return ResponseEntity.ok(developmentGoalService.findAllDevelopmentGoals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DevelopmentGoalResponse> findDevelopmentGoalById(@PathVariable Long id) {
        return ResponseEntity.ok(developmentGoalService.findByIdValidated(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DevelopmentGoalResponse> updateDevelopmentGoal(
            @PathVariable Long id, @RequestBody @Valid DevelopmentGoalRequest request) {
        return ResponseEntity.ok(developmentGoalService.updateDevelopmentGoalById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevelopmentGoal(@PathVariable Long id) {
        developmentGoalService.deleteDevelopmentGoalById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<DevelopmentGoalResponse> toggleDevelopmentGoal(@PathVariable Long id) {
        return ResponseEntity.ok(developmentGoalService.toggleCompleted(id));
    }
}
