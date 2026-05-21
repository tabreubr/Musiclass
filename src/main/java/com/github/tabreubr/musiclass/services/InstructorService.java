package com.github.tabreubr.musiclass.services;

import com.github.tabreubr.musiclass.controllers.AuthController;
import com.github.tabreubr.musiclass.dto.instructor.InstructorRequest;
import com.github.tabreubr.musiclass.dto.instructor.InstructorResponse;
import com.github.tabreubr.musiclass.entities.Instructor;
import com.github.tabreubr.musiclass.exceptions.ResourceNotFoundException;
import com.github.tabreubr.musiclass.repositories.InstructorRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;
    private final PasswordEncoder passwordEncoder;

    public InstructorService(InstructorRepository instructorRepository, PasswordEncoder passwordEncoder) {
        this.instructorRepository = instructorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public InstructorResponse save(InstructorRequest request) {
        Instructor instructor = new Instructor();
        instructor.setName(request.name());
        instructor.setEmail(request.email());
        instructor.setPassword(passwordEncoder.encode(request.password()));
        instructor.setRole(request.role());
        return InstructorResponse.from(instructorRepository.save(instructor));
    }

    public InstructorResponse findById(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + id));
        return InstructorResponse.from(instructor);
    }

    public Instructor findEntityById(Long id) {
        return instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + id));
    }

    // Mantido com return de entidade para o AuthController conseguir verificar a senha e gerar o token
    // uso interno, nunca exposto diretamente
    public Instructor findByEmail(String email) {
        return instructorRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with email: " + email));
    }

    public List<InstructorResponse> findAllInstructors() {
        return instructorRepository.findAll()
                .stream()
                .map(InstructorResponse::from)
                .toList();
    }

    public InstructorResponse updateInstructorById(Long id, InstructorRequest request) {
        findById(id);
        Instructor instructor = new Instructor();
        instructor.setId(id);
        instructor.setName(request.name());
        instructor.setEmail(request.email());
        instructor.setPassword(passwordEncoder.encode(request.password()));
        instructor.setRole(request.role());
        return InstructorResponse.from(instructorRepository.save(instructor));
    }

    public void deleteInstructorById(Long id) {
        findById(id);
        instructorRepository.deleteById(id);
    }

    public Instructor getAuthenticatedInstructor() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return findByEmail(email);
    }
}
