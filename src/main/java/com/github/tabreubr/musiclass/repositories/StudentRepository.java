package com.github.tabreubr.musiclass.repositories;

import com.github.tabreubr.musiclass.entities.Instructor;
import com.github.tabreubr.musiclass.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    List<Student> findAllByInstructor(Instructor instructor);
    Optional<Student> findByEmail(String email);
}
