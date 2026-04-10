package com.github.tabreubr.musiclass.services;

import com.github.tabreubr.musiclass.entities.Lesson;
import com.github.tabreubr.musiclass.exceptions.ResourceNotFoundException;
import com.github.tabreubr.musiclass.repositories.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {

    private LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public Lesson save(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public Lesson findById(Long id) {
        return lessonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + id));
    }

    public List<Lesson> findAllLessons() {
        return lessonRepository.findAll();
    }

    public Lesson updateLessonById(Long id, Lesson lesson) {
        findById(id);
        lesson.setId(id);
        return lessonRepository.save(lesson);
    }

    public void deleteLessonById(Long id) {
        findById(id);
        lessonRepository.deleteById(id);
    }
}
