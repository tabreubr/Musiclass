package com.github.tabreubr.musiclass.repositories;

import com.github.tabreubr.musiclass.entities.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument,Long> {
}
