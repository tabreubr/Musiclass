package com.github.tabreubr.musiclass.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Future
    private LocalDate date;
    private String observations;
    @NotBlank
    private Boolean passed;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Lesson lesson;
    @ManyToOne
    private Instructor instructor;
}
