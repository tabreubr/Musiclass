package com.github.tabreubr.musiclass.repositories;

import com.github.tabreubr.musiclass.entities.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassesRepository extends JpaRepository<Classes,Long> {
    List<Classes> findAllByDeletedFalse();
}
