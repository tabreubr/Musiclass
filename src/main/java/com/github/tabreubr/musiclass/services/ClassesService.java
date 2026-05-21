package com.github.tabreubr.musiclass.services;

import com.github.tabreubr.musiclass.dto.classes.ClassesRequest;
import com.github.tabreubr.musiclass.dto.classes.ClassesResponse;
import com.github.tabreubr.musiclass.dto.classes.ClassesUpdateRequest;
import com.github.tabreubr.musiclass.entities.Classes;
import com.github.tabreubr.musiclass.entities.Instructor;
import com.github.tabreubr.musiclass.entities.Student;
import com.github.tabreubr.musiclass.exceptions.ResourceNotFoundException;
import com.github.tabreubr.musiclass.repositories.ClassesRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassesService {

    private final ClassesRepository classRepository;
    private final StudentService studentService;
    private final InstructorService instructorService;

    public ClassesService(ClassesRepository classRepository,
                          StudentService studentService,
                          InstructorService instructorService) {
        this.classRepository = classRepository;
        this.studentService = studentService;
        this.instructorService = instructorService;
    }

    public ClassesResponse save(ClassesRequest request) {
        Student student = studentService.findEntityById(request.studentId());
        Instructor instructor = instructorService.getAuthenticatedInstructor();

        Classes classes = new Classes();
        classes.setDate(request.date());
        classes.setObservations(request.observations());
        classes.setPassed(request.passed());
        classes.setStudent(student);
        classes.setInstructor(instructor);

        return ClassesResponse.from(classRepository.save(classes));
    }

    public ClassesResponse findById(Long id) {
        return ClassesResponse.from(findEntityById(id));
    }

    public List<ClassesResponse> findAllClasses() {
        Instructor instructor = instructorService.getAuthenticatedInstructor();
        return classRepository.findAllByInstructorAndDeletedFalse(instructor)
                .stream()
                .map(ClassesResponse::from)
                .toList();
    }

    public List<ClassesResponse> findAllByAuthenticatedStudent() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentService.findByEmail(email);
        return classRepository.findAllByStudentAndDeletedFalse(student)
                .stream()
                .map(ClassesResponse::from)
                .toList();
    }

    public ClassesResponse updateById(Long id, ClassesUpdateRequest request) {
        Classes existing = findEntityById(id);

        if (request.passed() != null)
            existing.setPassed(request.passed());
        if(request.observations() != null)
            existing.setObservations(request.observations());

        return ClassesResponse.from(classRepository.save(existing));
    }

    public void deleteById(Long id) {
        Classes existing = findEntityById(id);
        existing.setDeleted(true);
        classRepository.save(existing);
    }

    // Método interno - retorna entidade para uso entre services
    public Classes findEntityById(Long id) {
        return classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class with id: " + id));
    }
}
