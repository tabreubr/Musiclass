package com.github.tabreubr.musiclass.repositories;

import com.github.tabreubr.musiclass.entities.DevelopmentGoal;
import com.github.tabreubr.musiclass.entities.Instructor;
import com.github.tabreubr.musiclass.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevelopmentGoalRepository extends JpaRepository<DevelopmentGoal,Long> {

    List<DevelopmentGoal> findAllByStudentInstructor(Instructor instructor);
}
