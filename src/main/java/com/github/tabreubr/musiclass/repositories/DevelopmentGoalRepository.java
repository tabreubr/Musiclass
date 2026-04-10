package com.github.tabreubr.musiclass.repositories;

import com.github.tabreubr.musiclass.entities.DevelopmentGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevelopmentGoalRepository extends JpaRepository<DevelopmentGoal,Long> {
}
