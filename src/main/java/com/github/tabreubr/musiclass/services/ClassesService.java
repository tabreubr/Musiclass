package com.github.tabreubr.musiclass.services;

import com.github.tabreubr.musiclass.entities.Classes;
import com.github.tabreubr.musiclass.exceptions.ResourceNotFoundException;
import com.github.tabreubr.musiclass.repositories.ClassesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassesService {

    private final ClassesRepository classRepository;

    public ClassesService(ClassesRepository classRepository) {
        this.classRepository = classRepository;
    }

    public Classes save(Classes classes) {
        return classRepository.save(classes);
    }

    public Classes findById(Long id) {
        return classRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + id));
    }

    public List<Classes> findAllClasses() {
        return classRepository.findAll();
    }

    public Classes updateById(Long id, Classes classes) {
        findById(id);
        classes.setId(id);
        return classRepository.save(classes);
    }

    public void deleteById(Long id) {
        findById(id);
        classRepository.deleteById(id);
    }
}
