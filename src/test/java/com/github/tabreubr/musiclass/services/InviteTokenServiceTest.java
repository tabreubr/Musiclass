package com.github.tabreubr.musiclass.services;

import com.github.tabreubr.musiclass.dto.student.StudentInviteRequest;
import com.github.tabreubr.musiclass.entities.InviteToken;
import com.github.tabreubr.musiclass.entities.Student;
import com.github.tabreubr.musiclass.repositories.InviteTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InviteTokenServiceTest {

    @Mock
    InviteTokenRepository inviteTokenRepository;
    @Mock
    StudentService studentService;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    InstructorService instructorService;
    @InjectMocks
    InviteTokenService inviteTokenService;

    private InviteToken inviteToken;

    @BeforeEach
    void setUp() {
        inviteToken = new InviteToken();
        inviteToken.setUsed(false);
        inviteToken.setExpiresAt(LocalDateTime.now().plusDays(7));
        inviteToken.setStudent(new Student());
    }

    @Test
    void registerSucessfully() {
        when(inviteTokenRepository.findByToken(any())).thenReturn(Optional.of(inviteToken));
        inviteTokenService.register("valid-token", new StudentInviteRequest("test@example.com", "password"));
        assertTrue(inviteToken.getUsed());
        verify(inviteTokenRepository).save(inviteToken);

    }

    @Test
    void registerAlreadyExists() {
        inviteToken.setUsed(true);
        when(inviteTokenRepository.findByToken(any())).thenReturn(Optional.of(inviteToken));
        assertThrows(ResponseStatusException.class, () -> {
            inviteTokenService.register("used-token", new StudentInviteRequest("test@example.com", "password"));
        });
    }

    @Test
    void registerAlreadyExpired() {
        inviteToken.setExpiresAt(LocalDateTime.now().minusDays(1));
        when(inviteTokenRepository.findByToken(any())).thenReturn(Optional.of(inviteToken));
        assertThrows(ResponseStatusException.class, () -> {
            inviteTokenService.register("expired-token", new StudentInviteRequest("test@example.com", "password"));
        });
    }
}