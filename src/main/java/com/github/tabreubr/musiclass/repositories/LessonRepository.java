package com.github.tabreubr.musiclass.repositories;

import com.github.tabreubr.musiclass.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
