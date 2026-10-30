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
        return StudentResponse.from(findOwnedEntity(id));
    }

    public List<StudentResponse> findAllStudents() {
        Instructor instructor = instructorService.getAuthenticatedInstructor();
        return studentRepository.findAllByInstructor(instructor)
                .stream()
                .map(StudentResponse::from)
                .toList();
    }

    public StudentResponse updateById(Long id, StudentRequest request) {

        Student student = findOwnedEntity(id);
        Instrument instrument = instrumentService.findById(request.instrumentId());

        student.setName(request.name());
        student.setInstrument(instrument);

        return StudentResponse.from(studentRepository.save(student));
    }

    public void deleteStudentById(Long id) {
        studentRepository.delete(findOwnedEntity(id));
    }

    public Student saveEntity(Student student) {
        return studentRepository.save(student);
    }

    // Busca o aluno somente entre os alunos do instrutor autenticado.
    // Aluno de outro instrutor gera 404, sem revelar que o id existe.
    public Student findOwnedEntity(Long id) {
        Instructor instructor = instructorService.getAuthenticatedInstructor();
        return studentRepository.findByIdAndInstructor(id, instructor)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    public Student findEntityById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with email: " + email));
    }

}
