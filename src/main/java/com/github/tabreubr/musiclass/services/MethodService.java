package com.github.tabreubr.musiclass.services;

import com.github.tabreubr.musiclass.entities.Instructor;
import com.github.tabreubr.musiclass.entities.Method;
import com.github.tabreubr.musiclass.repositories.MethodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MethodService {

    private final MethodRepository methodRepository;

    public MethodService(MethodRepository methodRepository) {
        this.methodRepository = methodRepository;
    }

    public Method save(Method method) {
        return methodRepository.save(method);
    }

    public Method findById(Long id) {
        return methodRepository.findById(id).orElseThrow(() -> new RuntimeException("Method not found with id: " + id));
    }

    public List<Method> findAllMethods() {
        return methodRepository.findAll();
    }

    public Method updateMethodById(Long id, Method method) {
        findById(id);
        method.setId(id);
        return methodRepository.save(method);
    }

    public void deleteMethodById(Long id) {
        findById(id);
        methodRepository.deleteById(id);
    }

}
