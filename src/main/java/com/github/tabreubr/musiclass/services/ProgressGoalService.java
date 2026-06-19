package com.github.tabreubr.musiclass.services;

import com.github.tabreubr.musiclass.dto.progressGoal.ProgressGoalRequest;
import com.github.tabreubr.musiclass.dto.progressGoal.ProgressGoalResponse;
import com.github.tabreubr.musiclass.entities.Instructor;
import com.github.tabreubr.musiclass.entities.Method;
import com.github.tabreubr.musiclass.entities.ProgressGoal;
import com.github.tabreubr.musiclass.entities.Student;
import com.github.tabreubr.musiclass.exceptions.ResourceNotFoundException;
import com.github.tabreubr.musiclass.repositories.MethodRepository;
import com.github.tabreubr.musiclass.repositories.ProgressGoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProgressGoalService {

    private final ProgressGoalRepository progressGoalRepository;
    private final MethodRepository methodRepository;
    private final StudentService studentService;
    private final InstructorService instructorService;

    public ProgressGoalService(ProgressGoalRepository progressGoalRepository,
                               MethodRepository methodRepository,
                               StudentService studentService,
                               InstructorService instructorService) {
        this.progressGoalRepository = progressGoalRepository;
        this.methodRepository = methodRepository;
        this.studentService = studentService;
        this.instructorService = instructorService;
    }

    public ProgressGoalResponse save(ProgressGoalRequest request) {
        Instructor instructor = instructorService.getAuthenticatedInstructor();
        Student student = studentService.findEntityById(request.studentId());
        if (!student.getInstructor().getId().equals(instructor.getId())) {
            throw new ResourceNotFoundException("Student not found with id: " + request.studentId());
        }
        Method method = methodRepository.findByName(request.methodName())
                .orElseGet(() -> methodRepository.save(new Method(null, request.methodName())));
        ProgressGoal goal = new ProgressGoal();
        goal.setStudent(student);
        goal.setMethod(method);
        goal.setTargetLessonNumber(request.targetLessonNumber());
        goal.setDeadline(request.deadline());
        return ProgressGoalResponse.from(progressGoalRepository.save(goal));
    }

    public ProgressGoalResponse findByIdValidated(Long id) {
        return ProgressGoalResponse.from(findAndValidateOwnership(id));
    }

    public List<ProgressGoalResponse> findAllProgressGoals() {
        Instructor instructor = instructorService.getAuthenticatedInstructor();
        return progressGoalRepository.findAllByStudentInstructor(instructor)
                .stream().map(ProgressGoalResponse::from).toList();
    }

    public ProgressGoalResponse updateProgressGoalById(Long id, ProgressGoalRequest request) {
        ProgressGoal goal = findAndValidateOwnership(id);
        Method method = methodRepository.findByName(request.methodName())
                .orElseGet(() -> methodRepository.save(new Method(null, request.methodName())));
        goal.setMethod(method);
        goal.setTargetLessonNumber(request.targetLessonNumber());
        goal.setDeadline(request.deadline());
        return ProgressGoalResponse.from(progressGoalRepository.save(goal));
    }

    public void deleteProgressGoalById(Long id) {
        progressGoalRepository.deleteById(findAndValidateOwnership(id).getId());
    }

    public ProgressGoalResponse toggleCompleted(Long id) {
        ProgressGoal goal = findAndValidateOwnership(id);
        goal.setCompleted(!Boolean.TRUE.equals(goal.getCompleted()));
        return ProgressGoalResponse.from(progressGoalRepository.save(goal));
    }

    public List<ProgressGoalResponse> findAllByStudent(Student student) {
        return progressGoalRepository.findAllByStudent(student)
                .stream().map(ProgressGoalResponse::from).toList();
    }

    private ProgressGoal findAndValidateOwnership(Long id) {
        Instructor instructor = instructorService.getAuthenticatedInstructor();
        ProgressGoal goal = progressGoalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Progress Goal not found with id: " + id));
        if (!goal.getStudent().getInstructor().getId().equals(instructor.getId())) {
            throw new ResourceNotFoundException("Progress Goal not found with id: " + id);
        }
        return goal;
    }
}
