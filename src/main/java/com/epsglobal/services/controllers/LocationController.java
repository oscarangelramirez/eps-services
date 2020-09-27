package com.epsglobal.services.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epsglobal.services.application.LocationApplicationService;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.location.AddLocationRequest;
import com.epsglobal.services.datatransfer.location.AddLocationResponse;
import com.epsglobal.services.datatransfer.location.GetLocationResponse;
import com.epsglobal.services.datatransfer.location.UpdateLocationRequest;
import com.epsglobal.services.datatransfer.location.UpdateLocationResponse;

@RestController
@RequestMapping("/api/v1/locations")
@CrossOrigin(origins = "*")
public class LocationController {
	@Autowired
	private LocationApplicationService locationApplicationService;

	@GetMapping
	public ResponseEntity<List<GetLocationResponse>> findAll() {
		List<GetLocationResponse> response = locationApplicationService.findAll();
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<GetLocationResponse> findById(@PathVariable Long id) {
		try {
			GetLocationResponse response = locationApplicationService.findById(id);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}

	@PostMapping
	public ResponseEntity<AddLocationResponse> add(@RequestBody AddLocationRequest request) {
		try {
			AddLocationResponse response = locationApplicationService.add(request);
			return ResponseEntity.ok(response);
		} catch (InvalidException exception) {
			throw exception;
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<UpdateLocationResponse> update(@PathVariable Long id, @RequestBody UpdateLocationRequest request) {
		try {
			UpdateLocationResponse response = locationApplicationService.update(id, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
}
