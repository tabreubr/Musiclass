package com.github.tabreubr.musiclass.services;

import com.github.tabreubr.musiclass.entities.Classes;
import com.github.tabreubr.musiclass.entities.Lesson;
import com.github.tabreubr.musiclass.entities.Method;
import com.github.tabreubr.musiclass.exceptions.ResourceNotFoundException;
import com.github.tabreubr.musiclass.repositories.LessonRepository;
import com.github.tabreubr.musiclass.repositories.MethodRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final MethodRepository methodRepository;
    private final ClassesService classesService;

    public LessonService(LessonRepository lessonRepository,
                         MethodRepository methodRepository,
                         ClassesService classesService) {
        this.lessonRepository = lessonRepository;
        this.methodRepository = methodRepository;
        this.classesService = classesService;
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

    public Lesson addToClass(Long classId, Map<String, Object> body) {
        Classes classes = classesService.findEntityById(classId);

        String methodName = (String) body.get("methodName");
        Method method = methodRepository.findByName(methodName)
                .orElseGet(() -> methodRepository.save(new Method(null, methodName)));

        Lesson lesson = new Lesson();
        lesson.setClasses(classes);
        lesson.setMethodName(method);
        if (body.get("page") != null)
            lesson.setPage(((Number) body.get("page")).intValue());
        if (body.get("lessonNumber") != null)
            lesson.setLessonNumber(((Number) body.get("lessonNumber")).intValue());
        lesson.setCompleted(false);

        return lessonRepository.save(lesson);
    }

    public Lesson updateCompleted(Long id, Boolean completed) {
        Lesson lesson = findById(id);
        lesson.setCompleted(completed);
        return lessonRepository.save(lesson);
    }
}
