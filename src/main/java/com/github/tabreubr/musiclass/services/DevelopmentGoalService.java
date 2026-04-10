package com.github.tabreubr.musiclass.services;

import com.github.tabreubr.musiclass.entities.DevelopmentGoal;
import com.github.tabreubr.musiclass.exceptions.ResourceNotFoundException;
import com.github.tabreubr.musiclass.repositories.DevelopmentGoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevelopmentGoalService {

    private final DevelopmentGoalRepository developmentGoalRepository;

    public DevelopmentGoalService(DevelopmentGoalRepository developmentGoalRepository) {
        this.developmentGoalRepository = developmentGoalRepository;
    }

    public DevelopmentGoal save(DevelopmentGoal developmentGoal) {
        return developmentGoalRepository.save(developmentGoal);
    }

    public DevelopmentGoal findById(Long id) {
        return developmentGoalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Development Goal not found with id: " + id));
    }

    public List<DevelopmentGoal> findAllDevelopmentGoals() {
        return developmentGoalRepository.findAll();
    }

    public DevelopmentGoal updateDevelopmentGoalById(Long id, DevelopmentGoal developmentGoal) {
        findById(id);
        developmentGoal.setId(id);
        return developmentGoalRepository.save(developmentGoal);
    }

    public void deleteDevelopmentGoalById(Long id) {
        findById(id);
        developmentGoalRepository.deleteById(id);
    }
}
