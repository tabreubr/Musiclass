package com.github.tabreubr.musiclass.services;

import com.github.tabreubr.musiclass.entities.Instructor;
import com.github.tabreubr.musiclass.entities.Method;
import com.github.tabreubr.musiclass.entities.ProgressGoal;
import com.github.tabreubr.musiclass.entities.Student;
import com.github.tabreubr.musiclass.repositories.MethodRepository;
import com.github.tabreubr.musiclass.repositories.ProgressGoalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


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

    public ProgressGoal save(ProgressGoal progressGoal) {
        return progressGoalRepository.save(progressGoal);
    }

    public ProgressGoal findById(Long id) {
        return progressGoalRepository.findById(id).orElse(null);
    }

    public List<ProgressGoal> findAllProgressGoals() {
        Instructor instructor = instructorService.getAuthenticatedInstructor();
        return progressGoalRepository.findAllByStudentInstructor(instructor);
    }

    public ProgressGoal updateProgressGoalById(Long id, ProgressGoal progressGoal) {
        findById(id);
        progressGoal.setId(id);
        return progressGoalRepository.save(progressGoal);
    }

    public void deleteProgressGoalById(Long id) {
        findById(id);
        progressGoalRepository.deleteById(id);
    }

    public ProgressGoal saveFromBody(Map<String, Object> body) {
        String methodName = (String) body.get("methodName");
        Method method = methodRepository.findByName(methodName)
                .orElseGet(() -> methodRepository.save(new Method(null, methodName)));

        Student student = studentService.findEntityById(((Number) body.get("studentId")).longValue());

        ProgressGoal goal = new ProgressGoal();
        goal.setMethod(method);
        goal.setStudent(student);
        goal.setTargetLessonNumber(((Number) body.get("targetLessonNumber")).intValue());
        if (body.get("deadline") != null)
            goal.setDeadline(LocalDate.parse((String) body.get("deadline")));

        return progressGoalRepository.save(goal);
    }
}
