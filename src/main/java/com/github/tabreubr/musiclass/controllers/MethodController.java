package com.github.tabreubr.musiclass.controllers;

import com.github.tabreubr.musiclass.entities.Method;
import com.github.tabreubr.musiclass.services.MethodService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/methods")
@RestController
public class MethodController {

	private final MethodService methodService;

	public MethodController(MethodService methodService) {
		this.methodService = methodService;
	}

	@PostMapping
	public ResponseEntity<?> saveMethod(@RequestBody @Valid Method method) {
		return ResponseEntity.status(HttpStatus.CREATED).body(methodService.save(method));
	}

	@GetMapping
	public ResponseEntity<?> findAllMethods() {
		return ResponseEntity.status(HttpStatus.OK).body(methodService.findAllMethods());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findMethodById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(methodService.findById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateMethod(@PathVariable Long id, @RequestBody @Valid Method method) {
		return ResponseEntity.status(HttpStatus.OK).body(methodService.updateMethodById(id, method));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteMethod(@PathVariable Long id) {
		methodService.deleteMethodById(id);
		return ResponseEntity.noContent().build();
	}
}
