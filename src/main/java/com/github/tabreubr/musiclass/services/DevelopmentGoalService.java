package com.github.tabreubr.musiclass.services;

import com.github.tabreubr.musiclass.dto.developmentGoal.DevelopmentGoalRequest;
import com.github.tabreubr.musiclass.dto.developmentGoal.DevelopmentGoalResponse;
import com.github.tabreubr.musiclass.entities.DevelopmentGoal;
import com.github.tabreubr.musiclass.entities.Instructor;
import com.github.tabreubr.musiclass.entities.Student;
import com.github.tabreubr.musiclass.exceptions.ResourceNotFoundException;
import com.github.tabreubr.musiclass.repositories.DevelopmentGoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevelopmentGoalService {

    private final DevelopmentGoalRepository developmentGoalRepository;
    private final InstructorService instructorService;
    private final StudentService studentService;

    public DevelopmentGoalService(DevelopmentGoalRepository developmentGoalRepository,
                                  InstructorService instructorService,
                                  StudentService studentService) {
        this.developmentGoalRepository = developmentGoalRepository;
        this.instructorService = instructorService;
        this.studentService = studentService;
    }

    public DevelopmentGoalResponse save(DevelopmentGoalRequest request) {
        Instructor instructor = instructorService.getAuthenticatedInstructor();
        Student student = studentService.findEntityById(request.studentId());
        if (!student.getInstructor().getId().equals(instructor.getId())) {
            throw new ResourceNotFoundException("Student not found with id: " + request.studentId());
        }
        DevelopmentGoal goal = new DevelopmentGoal();
        goal.setDescription(request.description());
        goal.setDeadline(request.deadline());
        goal.setStudent(student);
        return DevelopmentGoalResponse.from(developmentGoalRepository.save(goal));
    }

    public DevelopmentGoalResponse findByIdValidated(Long id) {
        return DevelopmentGoalResponse.from(findAndValidateOwnership(id));
    }

    public List<DevelopmentGoalResponse> findAllDevelopmentGoals() {
        Instructor instructor = instructorService.getAuthenticatedInstructor();
        return developmentGoalRepository.findAllByStudentInstructor(instructor)
                .stream().map(DevelopmentGoalResponse::from).toList();
    }

    public DevelopmentGoalResponse updateDevelopmentGoalById(Long id, DevelopmentGoalRequest request) {
        DevelopmentGoal goal = findAndValidateOwnership(id);
        goal.setDescription(request.description());
        goal.setDeadline(request.deadline());
        return DevelopmentGoalResponse.from(developmentGoalRepository.save(goal));
    }

    public void deleteDevelopmentGoalById(Long id) {
        developmentGoalRepository.deleteById(findAndValidateOwnership(id).getId());
    }

    public DevelopmentGoalResponse toggleCompleted(Long id) {
        DevelopmentGoal goal = findAndValidateOwnership(id);
        goal.setCompleted(!Boolean.TRUE.equals(goal.getCompleted()));
        return DevelopmentGoalResponse.from(developmentGoalRepository.save(goal));
    }

    public List<DevelopmentGoalResponse> findAllByStudent(Student student) {
        return developmentGoalRepository.findAllByStudent(student)
                .stream().map(DevelopmentGoalResponse::from).toList();
    }

    private DevelopmentGoal findAndValidateOwnership(Long id) {
        Instructor instructor = instructorService.getAuthenticatedInstructor();
        DevelopmentGoal goal = developmentGoalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Development Goal not found with id: " + id));
        if (!goal.getStudent().getInstructor().getId().equals(instructor.getId())) {
            throw new ResourceNotFoundException("Development Goal not found with id: " + id);
        }
        return goal;
    }
}
