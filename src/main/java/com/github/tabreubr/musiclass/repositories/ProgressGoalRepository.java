package com.github.tabreubr.musiclass.repositories;

import com.github.tabreubr.musiclass.entities.ProgressGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressGoalRepository extends JpaRepository<ProgressGoal,Long> {
}
