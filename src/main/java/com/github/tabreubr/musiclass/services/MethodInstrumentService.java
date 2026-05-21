package com.github.tabreubr.musiclass.services;

import com.github.tabreubr.musiclass.entities.MethodInstrument;
import com.github.tabreubr.musiclass.exceptions.ResourceNotFoundException;
import com.github.tabreubr.musiclass.repositories.MethodInstrumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MethodInstrumentService {

    private final MethodInstrumentRepository methodInstrumentRepository;

    public MethodInstrumentService(MethodInstrumentRepository methodInstrumentRepository) {
        this.methodInstrumentRepository = methodInstrumentRepository;
    }

    public MethodInstrument save(MethodInstrument methodInstrument) {
        return methodInstrumentRepository.save(methodInstrument);
    }

    public MethodInstrument findById(Long id) {
        return methodInstrumentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Method Instrument not found with id: " + id));
    }

    public List<MethodInstrument> findAllMethodInstruments() {
        return methodInstrumentRepository.findAll();
    }

    public MethodInstrument updateMethodInstrumentById(Long id, MethodInstrument methodInstrument) {
        findById(id);
        methodInstrument.setId(id);
        return methodInstrumentRepository.save(methodInstrument);
    }

    public void deleteMethodInstrumentById(Long id) {
        findById(id);
        methodInstrumentRepository.deleteById(id);
    }
}

