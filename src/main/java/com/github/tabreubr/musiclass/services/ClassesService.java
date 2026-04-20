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

    public Classes updateById(Long id, Classes updatedData) {
        Classes existing = findById(id);
        if (updatedData.getPassed() != null)
            existing.setPassed(updatedData.getPassed());
        if(updatedData.getObservations() != null)
            existing.setObservations(updatedData.getObservations());
        return classRepository.save(existing);
    }

    public void deleteById(Long id) {
        Classes existing = findById(id);
        existing.setDeleted(true);
        classRepository.save(existing);
    }
}
