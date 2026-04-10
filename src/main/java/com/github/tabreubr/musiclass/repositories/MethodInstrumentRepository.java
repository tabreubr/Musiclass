package com.github.tabreubr.musiclass.repositories;

import com.github.tabreubr.musiclass.entities.MethodInstrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MethodInstrumentRepository extends JpaRepository<MethodInstrument,Long> {
}
