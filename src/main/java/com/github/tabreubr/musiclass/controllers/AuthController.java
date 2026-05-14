package com.github.tabreubr.musiclass.controllers;

import com.github.tabreubr.musiclass.entities.Instructor;
import com.github.tabreubr.musiclass.infra.security.JwtUtil;
import com.github.tabreubr.musiclass.services.InstructorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final InstructorService instructorService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(InstructorService instructorService,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.instructorService = instructorService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Instructor instructor;
        try {
            instructor = instructorService.findByEmail(request.email());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        if (!passwordEncoder.matches(request.password(), instructor.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        String token = jwtUtil.generateToken(instructor);

        return ResponseEntity.ok(new LoginResponse(
                token,
                instructor.getName(),
                instructor.getRole().name(),
                instructor.getEmail(),
                instructor.getId()
        ));
    }

    record LoginRequest(String email, String password) {
    }

    record LoginResponse(String token, String name, String role, String email, Long id) {
    }

}
