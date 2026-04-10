package com.github.tabreubr.musiclass.services;

import com.github.tabreubr.musiclass.entities.ProgressGoal;
import com.github.tabreubr.musiclass.repositories.ProgressGoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressGoalService {

    private final ProgressGoalRepository progressGoalRepository;

    public ProgressGoalService(ProgressGoalRepository progressGoalRepository) {
        this.progressGoalRepository = progressGoalRepository;
    }

    public ProgressGoal save(ProgressGoal progressGoal) {
        return progressGoalRepository.save(progressGoal);
    }

    public ProgressGoal findById(Long id) {
        return progressGoalRepository.findById(id).orElse(null);
    }

    public List<ProgressGoal> findAllProgressGoals()
    {
        return progressGoalRepository.findAll();
    }

    public ProgressGoal updateProgressGoalById(Long id, ProgressGoal progressGoal) {
        findById(id);
        progressGoal.setId(id);
        return progressGoalRepository.save(progressGoal);
    }

    public void deleteProgressGoalById(Long id) {
        findById(id);
        progressGoalRepository.deleteById(id);
    }
}
