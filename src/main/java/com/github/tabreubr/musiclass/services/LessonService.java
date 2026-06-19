package com.github.tabreubr.musiclass.services;

import com.github.tabreubr.musiclass.dto.lesson.LessonRequest;
import com.github.tabreubr.musiclass.dto.lesson.LessonResponse;
import com.github.tabreubr.musiclass.entities.Classes;
import com.github.tabreubr.musiclass.entities.Instructor;
import com.github.tabreubr.musiclass.entities.Lesson;
import com.github.tabreubr.musiclass.entities.Method;
import com.github.tabreubr.musiclass.exceptions.ResourceNotFoundException;
import com.github.tabreubr.musiclass.repositories.LessonRepository;
import com.github.tabreubr.musiclass.repositories.MethodRepository;
import org.springframework.stereotype.Service;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final MethodRepository methodRepository;
    private final ClassesService classesService;
    private final InstructorService instructorService;

    public LessonService(LessonRepository lessonRepository,
                         MethodRepository methodRepository,
                         ClassesService classesService,
                         InstructorService instructorService) {
        this.lessonRepository = lessonRepository;
        this.methodRepository = methodRepository;
        this.classesService = classesService;
        this.instructorService = instructorService;
    }

    public LessonResponse addToClass(Long classId, LessonRequest request) {
        Instructor instructor = instructorService.getAuthenticatedInstructor();
        Classes classes = classesService.findEntityById(classId);
        if (!classes.getInstructor().getId().equals(instructor.getId())) {
            throw new ResourceNotFoundException("Class not found with id: " + classId);
        }
        Method method = methodRepository.findByName(request.methodName())
                .orElseGet(() -> methodRepository.save(new Method(null, request.methodName())));
        Lesson lesson = new Lesson();
        lesson.setClasses(classes);
        lesson.setMethodName(method);
        lesson.setPage(request.page());
        lesson.setLessonNumber(request.lessonNumber());
        lesson.setCompleted(false);
        return LessonResponse.from(lessonRepository.save(lesson));
    }

    public LessonResponse findByIdValidated(Long id) {
        return LessonResponse.from(findAndValidateOwnership(id));
    }

    public LessonResponse updateCompleted(Long id, Boolean completed) {
        Lesson lesson = findAndValidateOwnership(id);
        lesson.setCompleted(completed);
        return LessonResponse.from(lessonRepository.save(lesson));
    }

    public void deleteLessonById(Long id) {
        lessonRepository.deleteById(findAndValidateOwnership(id).getId());
    }

    private Lesson findAndValidateOwnership(Long id) {
        Instructor instructor = instructorService.getAuthenticatedInstructor();
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + id));
        if (!lesson.getClasses().getInstructor().getId().equals(instructor.getId())) {
            throw new ResourceNotFoundException("Lesson not found with id: " + id);
        }
        return lesson;
    }
}