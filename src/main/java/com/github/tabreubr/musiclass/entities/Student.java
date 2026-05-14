package com.github.tabreubr.musiclass.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // campos que o instrutor preenche na criação
    @NotBlank
    private String name;

    @ManyToOne
    private Instrument instrument;

    @ManyToOne
    private Instructor instructor;

    // campos nulos até o aluno aceitar o convite
    @Column(nullable = true, unique = true)
    private String email;
    @Column(nullable = true)
    private String password;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("student")
    private List<Classes> classes;
}
