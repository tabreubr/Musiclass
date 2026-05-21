package com.github.tabreubr.musiclass.repositories;

import com.github.tabreubr.musiclass.entities.Classes;
import com.github.tabreubr.musiclass.entities.Instructor;
import com.github.tabreubr.musiclass.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassesRepository extends JpaRepository<Classes,Long> {

    List<Classes> findAllByDeletedFalse();

    List<Classes> findAllByInstructorAndDeletedFalse(Instructor instructor);

    List<Classes> findAllByStudentAndDeletedFalse(Student student);
}
