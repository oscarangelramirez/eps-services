package com.epsglobal.services.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epsglobal.services.application.DatabaseApplicationService;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.database.AddBackupResponse;
import com.epsglobal.services.datatransfer.database.GetBackupResponse;

@RestController
@RequestMapping("/api/v1/database")
public class DatabaseController {

	@Autowired
	private DatabaseApplicationService databaseService;

	@PostMapping
	public ResponseEntity<AddBackupResponse> add() throws Exception {
		try {
			AddBackupResponse response = databaseService.createBackup();
			return ResponseEntity.ok(response);
		} catch (InvalidException | IOException | InterruptedException exception) {
			throw exception;
		}
	}

	@GetMapping
	public ResponseEntity<List<GetBackupResponse>> findAll() {
		List<GetBackupResponse> response = databaseService.findAll();
		return ResponseEntity.ok(response);
	}

	@GetMapping("{id}")
	public ResponseEntity<GetBackupResponse> findById(@PathVariable Long id) {
		try {
			GetBackupResponse response = databaseService.findById(id);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}
}
