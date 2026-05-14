package com.github.tabreubr.musiclass.services;

import com.github.tabreubr.musiclass.dto.invite.InviteResponse;
import com.github.tabreubr.musiclass.dto.student.StudentInviteRequest;
import com.github.tabreubr.musiclass.entities.InviteToken;
import com.github.tabreubr.musiclass.entities.Student;
import com.github.tabreubr.musiclass.exceptions.ResourceNotFoundException;
import com.github.tabreubr.musiclass.repositories.InviteTokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class InviteTokenService {

    private final InviteTokenRepository inviteTokenRepository;
    private final StudentService studentService;
    private final PasswordEncoder passwordEncoder;

    private static final String FRONTEND_URL = "http://localhost:3000";

    public InviteTokenService(InviteTokenRepository inviteTokenRepository,
                              StudentService studentService,
                              PasswordEncoder passwordEncoder) {
        this.inviteTokenRepository = inviteTokenRepository;
        this.studentService = studentService;
        this.passwordEncoder = passwordEncoder;
    }

    public InviteResponse generateInviteLink(Long studentId) {
        Student student = studentService.findEntityById(studentId);

        InviteToken inviteToken = new InviteToken();
        inviteToken.setStudent(student);
        inviteToken.setExpiresAt(LocalDateTime.now().plusDays(7));

        InviteToken saved = inviteTokenRepository.save(inviteToken);

        String link = FRONTEND_URL + "/invite/" + saved.getToken();

        return new InviteResponse(saved.getToken(), link, saved.getExpiresAt());
    }

    public void register(String token, StudentInviteRequest request) {
        InviteToken inviteToken = inviteTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid invite token"));

        if (Boolean.TRUE.equals(inviteToken.getUsed())) {
            throw new ResponseStatusException(HttpStatus.GONE, "Invite token already used");
        }

        if (LocalDateTime.now().isAfter(inviteToken.getExpiresAt())) {
            throw new ResponseStatusException(HttpStatus.GONE, "Invite token expired");
        }

        Student student = inviteToken.getStudent();

        student.setEmail(request.email());
        student.setPassword(passwordEncoder.encode(request.password()));

        inviteToken.setUsed(true);

        studentService.saveEntity(student);
        inviteTokenRepository.save(inviteToken);
    }

}
