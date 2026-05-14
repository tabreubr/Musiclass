package com.github.tabreubr.musiclass.services;

import com.github.tabreubr.musiclass.dto.student.StudentRequest;
import com.github.tabreubr.musiclass.dto.student.StudentResponse;
import com.github.tabreubr.musiclass.entities.Instructor;
import com.github.tabreubr.musiclass.entities.Instrument;
import com.github.tabreubr.musiclass.entities.Student;
import com.github.tabreubr.musiclass.exceptions.ResourceNotFoundException;
import com.github.tabreubr.musiclass.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final InstructorService instructorService;
    private final InstrumentService instrumentService;

    public StudentService(StudentRepository studentRepository,
                          InstructorService instructorService,
                          InstrumentService instrumentService) {
        this.studentRepository = studentRepository;
        this.instructorService = instructorService;
        this.instrumentService = instrumentService;
    }

    public StudentResponse save(StudentRequest request) {
        Instructor instructor = instructorService.getAuthenticatedInstructor();
        Instrument instrument = instrumentService.findById(request.instrumentId());

        Student student = new Student();
        student.setName(request.name());
        student.setInstructor(instructor);
        student.setInstrument(instrument);

        return StudentResponse.from(studentRepository.save(student));
    }

    public StudentResponse findById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id: " + id));
        return StudentResponse.from(student);
    }

    public List<StudentResponse> findAllStudents() {
        Instructor instructor = instructorService.getAuthenticatedInstructor();
        return studentRepository.findAllByInstructor(instructor)
                .stream()
                .map(StudentResponse::from)
                .toList();
    }

    public StudentResponse updateById(Long id, StudentRequest request) {

        Student student = findEntityById(id);
        Instrument instrument = instrumentService.findById(request.instrumentId());

        student.setName(request.name());
        student.setInstrument(instrument);

        return StudentResponse.from(studentRepository.save(student));
    }

    public void deleteStudentById(Long id) {
        findById(id);
        studentRepository.deleteById(id);
    }

    public Student saveEntity(Student student) {
        return studentRepository.save(student);
    }

    public Student findEntityById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id: " + id));
    }


}
