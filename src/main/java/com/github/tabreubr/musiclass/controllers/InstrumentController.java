package com.github.tabreubr.musiclass.controllers;

import com.github.tabreubr.musiclass.entities.Instrument;
import com.github.tabreubr.musiclass.services.InstrumentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/instruments")
@RestController
public class InstrumentController {

	private final InstrumentService instrumentService;

	public InstrumentController(InstrumentService instrumentService) {
		this.instrumentService = instrumentService;
	}

	@PostMapping
	public ResponseEntity<?> saveInstrument(@RequestBody @Valid Instrument instrument) {
		return ResponseEntity.status(HttpStatus.CREATED).body(instrumentService.save(instrument));
	}

	@GetMapping
	public ResponseEntity<?> findAllInstruments() {
		return ResponseEntity.status(HttpStatus.OK).body(instrumentService.findAllInstruments());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findInstrumentById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(instrumentService.findById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateInstrument(@PathVariable Long id, @RequestBody @Valid Instrument instrument) {
		return ResponseEntity.status(HttpStatus.OK).body(instrumentService.updateInstrumentById(id, instrument));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteInstrument(@PathVariable Long id) {
		instrumentService.deleteInstrumentById(id);
		return ResponseEntity.noContent().build();
	}
}
