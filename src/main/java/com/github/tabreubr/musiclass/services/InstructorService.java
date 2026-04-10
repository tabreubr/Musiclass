package com.github.tabreubr.musiclass.services;

import com.github.tabreubr.musiclass.entities.Instructor;
import com.github.tabreubr.musiclass.exceptions.ResourceNotFoundException;
import com.github.tabreubr.musiclass.repositories.InstructorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public Instructor save(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    public Instructor findById(Long id) {
        return instructorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + id));
    }

    public List<Instructor> findAllInstructors() {
        return instructorRepository.findAll();
    }

    public Instructor updateInstructorById(Long id, Instructor instructor) {
        findById(id);
        instructor.setId(id);
        return instructorRepository.save(instructor);
    }

    public void deleteInstructorById(Long id) {
        findById(id);
        instructorRepository.deleteById(id);
    }
}
