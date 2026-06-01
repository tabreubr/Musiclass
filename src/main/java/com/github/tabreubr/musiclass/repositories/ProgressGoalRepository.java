package com.github.tabreubr.musiclass.repositories;

import com.github.tabreubr.musiclass.entities.Instructor;
import com.github.tabreubr.musiclass.entities.ProgressGoal;
import com.github.tabreubr.musiclass.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressGoalRepository extends JpaRepository<ProgressGoal,Long> {

    List<ProgressGoal> findAllByStudentInstructor(Instructor instructor);
    List<ProgressGoal> findAllByStudent(Student student);
}
