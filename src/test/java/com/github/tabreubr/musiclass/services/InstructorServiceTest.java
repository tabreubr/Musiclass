package com.github.tabreubr.musiclass.services;

import com.github.tabreubr.musiclass.dto.instructor.InstructorRequest;
import com.github.tabreubr.musiclass.entities.Instructor;
import com.github.tabreubr.musiclass.enums.UserRole;
import com.github.tabreubr.musiclass.exceptions.ResourceAlreadyExistsException;
import com.github.tabreubr.musiclass.repositories.InstructorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InstructorServiceTest {

    @Mock
    InstructorRepository instructorRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    InstructorService instructorService;

    @Test
    void saveStoresNormalizedEmail() {
        when(instructorRepository.existsByEmail("ana@musiclass.test")).thenReturn(false);
        when(instructorRepository.save(any(Instructor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        instructorService.save(new InstructorRequest("Ana", "  Ana@Musiclass.TEST ", "senha", UserRole.INSTRUCTOR));

        ArgumentCaptor<Instructor> saved = ArgumentCaptor.forClass(Instructor.class);
        verify(instructorRepository).save(saved.capture());
        assertThat(saved.getValue().getEmail()).isEqualTo("ana@musiclass.test");
    }

    @Test
    void saveRejectsEmailAlreadyInUse() {
        when(instructorRepository.existsByEmail("ana@musiclass.test")).thenReturn(true);

        assertThatThrownBy(() -> instructorService.save(
                new InstructorRequest("Ana", "ana@musiclass.test", "senha", UserRole.INSTRUCTOR)))
                .isInstanceOf(ResourceAlreadyExistsException.class);
        verify(instructorRepository, never()).save(any());
    }

    @Test
    void adminUpdateRejectsEmailOfAnotherInstructor() {
        when(instructorRepository.findById(1L))
                .thenReturn(Optional.of(new Instructor(1L, "Ana", "ana@musiclass.test", "hash", UserRole.INSTRUCTOR)));
        when(instructorRepository.existsByEmailAndIdNot("bia@musiclass.test", 1L)).thenReturn(true);

        assertThatThrownBy(() -> instructorService.updateInstructorById(1L,
                new InstructorRequest("Ana", "bia@musiclass.test", "senha", UserRole.INSTRUCTOR)))
                .isInstanceOf(ResourceAlreadyExistsException.class);
        verify(instructorRepository, never()).save(any());
    }
}
