package com.github.tabreubr.musiclass.repositories;

import com.github.tabreubr.musiclass.entities.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassesRepository extends JpaRepository<Classes,Long> {
}
