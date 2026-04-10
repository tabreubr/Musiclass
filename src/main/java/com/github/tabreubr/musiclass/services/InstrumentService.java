package com.github.tabreubr.musiclass.services;

import com.github.tabreubr.musiclass.entities.Instrument;
import com.github.tabreubr.musiclass.repositories.InstrumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstrumentService {

    private final InstrumentRepository instrumentRepository;

    public InstrumentService(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

    public Instrument save(Instrument instrument) {
        return instrumentRepository.save(instrument);
    }

    public Instrument findById(Long id) {
        return instrumentRepository.findById(id).orElseThrow(() -> new RuntimeException("Instrument not found with id: " + id));
    }

    public List<Instrument> findAllInstruments() {
        return instrumentRepository.findAll();
    }

    public Instrument updateInstrumentById(Long id, Instrument instrument) {
        findById(id);
        instrument.setId(id);
        return instrumentRepository.save(instrument);
    }

    public void deleteInstrumentById(Long id) {
        findById(id);
        instrumentRepository.deleteById(id);
    }

}
