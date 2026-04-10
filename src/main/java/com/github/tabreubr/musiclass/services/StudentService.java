package com.github.tabreubr.musiclass.services;

import com.github.tabreubr.musiclass.entities.Student;
import com.github.tabreubr.musiclass.exceptions.ResourceNotFoundException;
import com.github.tabreubr.musiclass.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public Student findById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public Student updateById(Long id, Student student) {
        findById(id);
        student.setId(id);
        return studentRepository.save(student);
    }

    public void deleteStudentById(Long id) {
        findById(id);
        studentRepository.deleteById(id);
    }


}
