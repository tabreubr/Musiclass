package com.github.tabreubr.musiclass.services;

import com.github.tabreubr.musiclass.entities.Instrument;
import com.github.tabreubr.musiclass.exceptions.ResourceAlreadyExistsException;
import com.github.tabreubr.musiclass.repositories.InstrumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstrumentServiceTest {

    @Mock
    InstrumentRepository instrumentRepository;

    @InjectMocks
    InstrumentService instrumentService;

    private final Instrument instrument = new Instrument(null, "Violino");

    @Test
    void shouldSaveInstrumentWhenNameDoesNotExist() {
        when(instrumentRepository.existsByName(instrument.getName())).thenReturn(false);
        instrumentService.save(instrument);
        verify(instrumentRepository, times(1)).save(instrument);
    }

    @Test
    void shouldThrowResourceAlreadyExistsExceptionWhenNameAlreadyExists() {
        when(instrumentRepository.existsByName(instrument.getName())).thenReturn(true);
        assertThrows(ResourceAlreadyExistsException.class, () -> {
            instrumentService.save(instrument);
        });
    }
}